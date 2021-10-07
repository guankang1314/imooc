package com.imooc.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.imooc.utils.IMOOCJSONResult;

/**
 * @author qingtian
 * @description:
 * @Package com.imooc.exception
 * @date 2021/10/3 17:57
 */
@RestControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public IMOOCJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException ex) {
        return IMOOCJSONResult.errorMsg("文件上传大小不能超过500k，请压缩后再上传");
    }
}
