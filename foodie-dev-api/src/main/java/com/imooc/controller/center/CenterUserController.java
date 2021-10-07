package com.imooc.controller.center;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.imooc.controller.BaseController;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBO;
import com.imooc.resource.FileUpload;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.DateUtil;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.controller.center
 * @date 2021/10/2 13:47
 */
@Api(value = "用户信息接口",tags = "用户信息相关接口")
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "修改用户信息",notes = "修改用户信息",httpMethod = "POST")
    @PostMapping("update")
    public IMOOCJSONResult update(
                            @ApiParam(name = "userId",value = "用户id",required = true)
                            @RequestParam String userId,
                            @RequestBody @Valid CenterUserBO centerUserBO,
                            BindingResult result,
                            HttpServletRequest request,
                            HttpServletResponse response) {

        if (result.hasErrors()) {
            Map<String, String> errorsMap = getErrors(result);
            return IMOOCJSONResult.errorMap(errorsMap);
        }
        Users updateResult = centerUserService.updateUserInfo(userId, centerUserBO);
        setNullProperty(updateResult);

        CookieUtils.setCookie(request,response,"user",
                                JsonUtils.objectToJson(updateResult),true);
        //TODO 后续要增加令牌token,会整合redis
        return IMOOCJSONResult.ok();
    }


    @ApiOperation(value = "修改用户头像",notes = "修改用户头像",httpMethod = "POST")
    @PostMapping("uploadFace")
    public IMOOCJSONResult uploadFace(
                            @ApiParam(name = "userId",value = "用户id",required = true)
                            @RequestParam String userId,
                            MultipartFile file,
                            HttpServletRequest request,
                            HttpServletResponse response) {

        //定义头像保存的地址
        // String fileSpace = IMAGE_USER_FACE_LOCATION;
        String fileSpace = fileUpload.getImageUserFaceLocation();
        //在路径上为每个用户增加一个userId，用于区分不同的用户上传
        String uploadPathPrefix = File.separator + userId;

        //开始上传
        if (file != null) {
            OutputStream outputStream = null;
            //获取上传文件的名称
            String fileName = file.getOriginalFilename();
            if (StringUtils.isNoneBlank(fileName)) {
                //文件重命名
                try {
                    String fileNameArr[] = fileName.split("\\.");
                    //获取文件的后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];

                    if (!suffix.equalsIgnoreCase("png") &&
                            !suffix.equalsIgnoreCase("jpg") &&
                            !suffix.equalsIgnoreCase("jpeg") &&
                            !suffix.equalsIgnoreCase("gif")) {
                        return IMOOCJSONResult.errorMsg("图片格式不正确");
                    }

                    //文件名称重组
                    String newFileName = "face-" + userId + "." + suffix;

                    String finalFacePath = fileSpace + uploadPathPrefix + File.separator + newFileName;

                    uploadPathPrefix += ("/" + newFileName);

                    File outFile = new File(finalFacePath);

                    if (!outFile.getParentFile().exists()) {
                        outFile.getParentFile().mkdirs();
                    }

                    //开始保存
                    outputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream,outputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                 }finally {
                    IOUtils.closeQuietly(outputStream);
                }
            }
        }else {
            return IMOOCJSONResult.errorMsg("文件不能为空！");
        }

        //获取图片服务地址
        String imageServerUrl = fileUpload.getImageServerUrl();
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix
                + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        Users updateResult = centerUserService.updateUserFace(userId, finalUserFaceUrl);
        setNullProperty(updateResult);
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(updateResult),true);

        return IMOOCJSONResult.ok(updateResult);
    }

    private Map<String,String> getErrors(BindingResult result) {

        Map<String,String> map = new HashMap<>();

        List<FieldError> errorList = result.getFieldErrors();
        errorList.forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();
            map.put(field,message);
        });
        return map;
    }

    private Users setNullProperty(Users users) {
        users.setPassword(null);
        users.setMobile(null);
        users.setCreatedTime(null);
        users.setEmail(null);
        users.setBirthday(null);
        users.setUpdatedTime(null);
        return users;
    }
}
