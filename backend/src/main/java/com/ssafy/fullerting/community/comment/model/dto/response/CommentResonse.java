package com.ssafy.fullerting.community.comment.model.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResonse {

    private  Long id;

    private String commentcontent;

    private String nickname;
    private String thumbnail;
    private String rank;

    private LocalDateTime localDateTime;

}
