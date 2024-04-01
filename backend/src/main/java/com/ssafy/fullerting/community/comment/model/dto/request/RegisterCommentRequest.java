package com.ssafy.fullerting.community.comment.model.dto.request;

import com.ssafy.fullerting.community.article.model.entity.Article;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
public class RegisterCommentRequest {
    private String commentcontent;
    private String redirectURL;
}
