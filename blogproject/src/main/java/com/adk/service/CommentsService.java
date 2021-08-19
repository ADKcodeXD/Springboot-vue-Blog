package com.adk.service;

import com.adk.vo.Result;

public interface CommentsService {
    Result commentsByArticleId(Long id);
}
