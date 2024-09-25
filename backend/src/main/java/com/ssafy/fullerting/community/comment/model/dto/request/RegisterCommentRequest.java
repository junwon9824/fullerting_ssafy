package com.ssafy.fullerting.community.comment.model.dto.request;

import lombok.Getter;

@Getter
public class RegisterCommentRequest {
    private String commentcontent;
    private String redirectURL;
}
