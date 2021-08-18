package com.adk.service;

import com.adk.dao.mapper.ArticleMapper;
import com.adk.pojo.Article;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 线程池类 使用该类去更新 能够在不妨碍主线程的情况下进行
 */
@Component
public class ThreadService {

    //添加异步注解 标注该服务可以多线程执行
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        //通过新建一个对象来进行update
        /**
         * 这里有个小bug
         * update会读取新的对象中 所有不为null的数据，并且将其用来更新数据库
         * 如果pojo类中有基本类型，基本类型会有默认值，因此就会一并更新
         * 所以我们要将pojo中的类型改为封装的类型
         */
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts+1);

        LambdaUpdateWrapper<Article> lambdaUpdateWrapper= new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(Article::getId,article.getId());
        //为了保证线程安全的操作
        lambdaUpdateWrapper.eq(Article::getViewCounts,viewCounts);
        //执行操作
        articleMapper.update(articleUpdate,lambdaUpdateWrapper);


    }
}
