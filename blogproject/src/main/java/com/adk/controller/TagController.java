package com.adk.controller;

import com.adk.service.TagService;
import com.adk.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    public Result hot(){
        //展示标签的数量
        int limit=6;
        return tagService.hot(limit);
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }

    @GetMapping("detail")
    public Result findAllDetail(){
        return tagService.findAllDetail();
    }
}
