package com.adk.vo.params;

import lombok.Data;

@Data
public class CommentParams {

    private Long articleId;

    private String content;

    private Long parent;

    private Long toUserId;
}
