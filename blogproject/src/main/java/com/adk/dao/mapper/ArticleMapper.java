package com.adk.dao.mapper;

import com.adk.dao.dos.Archives;
import com.adk.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通过继承BaseMapper
 * MybatisPlus可以直接封装一个Article的表进去
 * 并且不需要写接口，可以直接使用mybatisPlus封装的方法进行查询。
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article>  {

    List<Archives> listArchives();

    IPage<Article> listArticle(Page<Article> page,Long categoryId,Long tagId,String year,String month);
}
