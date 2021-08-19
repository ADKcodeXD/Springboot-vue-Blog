package com.adk.service;

import com.adk.vo.Result;
import com.adk.vo.params.CommentParams;

public interface CommentsService {
    Result commentsByArticleId(Long id);

    Result comment(CommentParams commentParams);
}
