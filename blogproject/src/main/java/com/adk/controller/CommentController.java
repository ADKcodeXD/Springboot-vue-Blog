package com.adk.controller;


import com.adk.service.CommentsService;
import com.adk.vo.Result;
import com.adk.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comments")
public class CommentController {

    @Autowired
    private  CommentsService commentsService;
    @GetMapping("article/{id}")
    public Result comments(@PathVariable("id") Long id){
        return commentsService.commentsByArticleId(id);
    }

    @PostMapping("create/change")
    public Result commentAdd(@RequestBody CommentParams commentParams){
        return commentsService.comment(commentParams);
    }
}
