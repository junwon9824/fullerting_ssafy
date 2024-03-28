package com.ssafy.fullerting.community.comment.model.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResonse {

    private  Long id;

    private String commentcontent;

}
