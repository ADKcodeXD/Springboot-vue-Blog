package com.adk.dao.mapper;

import com.adk.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagMapper extends BaseMapper<Tag> {
    //根据文章id查询标签列表
    List<Tag> findTagByArticleId(Long articleid);

    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagByTagIds(List<Long> hotsTagIds);
}
