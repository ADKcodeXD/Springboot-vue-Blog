package com.adk.controller;


import com.adk.utils.QiniuUtils;
import com.adk.vo.ErrorCode;
import com.adk.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("upload")
public class UploadController {


    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile file){
        //获得文件原始名
        String originalFilename = file.getOriginalFilename();
        //使用uuid new文件名
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename, ".");

        boolean upload = qiniuUtils.upload(file, fileName);
        if(upload){
            return Result.success(qiniuUtils.url+"/"+fileName);
        }else{
            return Result.fail(ErrorCode.UPLOAD_ERROR.getCode(),ErrorCode.UPLOAD_ERROR.getMsg());
        }
    }
}
