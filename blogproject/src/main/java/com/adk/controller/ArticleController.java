package com.adk.controller;


import com.adk.service.ArticleService;
import com.adk.vo.Result;
import com.adk.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//json数据交互需要使用该controller
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    /*
        首页文章列表的controller
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams){
        return articleService.listArticle(pageParams);
    }

    //首页最热文章
    @PostMapping("hot")
    public Result hotArticle(){
        int limit=5;
        return articleService.hotArticle(limit);
    }

    //首页最新文章
    @PostMapping("new")
    public Result newArticle(){
        int limit =5;
        return articleService.newArticle(limit);
    }

    //首页文章归档
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }
}
