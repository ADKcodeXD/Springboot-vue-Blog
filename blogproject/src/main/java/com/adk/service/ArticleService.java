package com.adk.service;

import com.adk.pojo.Article;
import com.adk.vo.Result;
import com.adk.vo.params.PageParams;

import java.awt.print.Pageable;
import java.util.List;

public interface ArticleService {
    /**
     * 分页查询文章列表
     */
    Result listArticle(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticle(int limit);

    Result listArchives();

    Result findArticleById(Long id);
}
