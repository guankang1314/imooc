package com.imooc.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.IMarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.MobileEmailUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.controller
 * @date 2021/9/12 19:07
 */
@Api(value = "地址相关",tags = {"地址相关的接口"})
@RestController
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;


    @ApiOperation(value = "根据用户id查询用户地址",notes = "根据用户id查询用户地址",httpMethod = "POST")
    @PostMapping("/list")
    public IMOOCJSONResult list(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("");
        }

        List<UserAddress> list = addressService.queryAll(userId);
        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "用户新增地址",notes = "用户新增地址",httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(@RequestBody AddressBO addressBO) {

        IMOOCJSONResult result = checkAddress(addressBO);
        if (result.getStatus() != 200) {
            return result;
        }

        addressService.addNewUserAddress(addressBO);

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户更新地址",notes = "用户更新地址",httpMethod = "POST")
    @PostMapping("/update")
    public IMOOCJSONResult update(@RequestBody AddressBO addressBO) {

        if (StringUtils.isBlank(addressBO.getUserId())) {
            return IMOOCJSONResult.errorMsg("用户id为空");
        }

        IMOOCJSONResult result = checkAddress(addressBO);
        if (result.getStatus() != 200) {
            return result;
        }

        addressService.updateUserAddress(addressBO);

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户删除地址",notes = "用户删除地址",httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(@RequestParam String userId,@RequestParam String addressId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return IMOOCJSONResult.errorMsg("参数不能为空");
        }

        addressService.deleteUserAddress(userId,addressId);
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户设置默认地址",notes = "用户设置默认地址",httpMethod = "POST")
    @PostMapping("/setDefalut")
    public IMOOCJSONResult setDefault(@RequestParam String userId,@RequestParam String addressId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return IMOOCJSONResult.errorMsg("参数不能为空");
        }

        addressService.updateUserAddressToBeDefault(userId,addressId);
        return IMOOCJSONResult.ok();
    }

    private IMOOCJSONResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return IMOOCJSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return IMOOCJSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return IMOOCJSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return IMOOCJSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return IMOOCJSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return IMOOCJSONResult.errorMsg("收货地址信息不能为空");
        }

        return IMOOCJSONResult.ok();
    }
}
