package com.adk.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import com.adk.dao.mapper.TagMapper;
import com.adk.pojo.Tag;
import com.adk.service.TagService;
import com.adk.vo.Result;
import com.adk.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagByArticleId(Long articleid) {
        //mybatisplus无法进行多表查询 因此需要自己写
        List<Tag> tagList = tagMapper.findTagByArticleId(articleid);
        return copyList(tagList);
    }

    /**
     * limit 作用于最大取多少个标签  用于获取最热门的几个标签
     * @param limit
     * @return
     */
    @Override
    public Result hot(int limit) {
        /**
         * 最热门，就是获取标签和文章关联表中数量最多的几个标签
         * 也就是获取tag_id 在表中的count的数量并且对其进行排序
         * 也就是以下的sql语句所实现的事情
         * SELECT tag_id as tagId
         * FROM `ms_article_tag`
         * GROUP BY tag_id
         * ORDER BY count(*) DESC
         * LIMIT ?
         */
        List<Long> hotsTagIds = tagMapper.findHotsTagIds(limit);

        /**
         * 然后根据获取到的ids来获取tag
         * 判断获取到的tagids列表是否为空 若为空直接返回空列表值
         */
        if(CollectionUtils.isEmpty(hotsTagIds)){
            return Result.success(Collections.emptyList());
        }

        List<Tag> tagList= tagMapper.findTagByTagIds(hotsTagIds);
        //获取完list后 转换为tagvo
        List<TagVo> tagVos = copyList(tagList);

        return Result.success(tagVos);
    }

    /**
     * copy类
     * @param tag
     * @return
     */
    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }
}
