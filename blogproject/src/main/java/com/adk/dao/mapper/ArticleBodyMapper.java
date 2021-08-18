package com.adk.dao.mapper;

import com.adk.dao.dos.Archives;
import com.adk.pojo.Article;
import com.adk.pojo.ArticleBody;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 通过继承BaseMapper
 * MybatisPlus可以直接封装一个Article的表进去
 * 并且不需要写接口，可以直接使用mybatisPlus封装的方法进行查询。
 */
@Repository
public interface ArticleBodyMapper extends BaseMapper<ArticleBody>  {

}
