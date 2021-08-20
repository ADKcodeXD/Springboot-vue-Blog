package com.adk.controller;


import com.adk.common.aop.LogAnnotation;
import com.adk.service.ArticleService;
import com.adk.vo.Result;
import com.adk.vo.params.ArticleParams;
import com.adk.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController//json数据交互需要使用该controller
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    /*
        首页文章列表的controller
     */
    @PostMapping
    @LogAnnotation(module="文章",operator="获取文章列表")
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

    //文章主体
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id")Long id){
        /**
         * 通过pathvariable来获取链接中的id值
         */
        return articleService.findArticleById(id);
    }

    /**
     * 文章发布
     */
    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParams articleParams){
        return articleService.publish(articleParams);
    }
}
