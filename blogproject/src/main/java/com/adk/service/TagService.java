package com.adk.service;

import com.adk.pojo.Tag;
import com.adk.vo.Result;
import com.adk.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagByArticleId(Long articleid);

    Result hot(int limit);
}
