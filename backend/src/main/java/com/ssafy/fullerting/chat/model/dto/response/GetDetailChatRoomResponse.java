package com.ssafy.fullerting.chat.model.dto.response;

import com.ssafy.fullerting.exArticle.model.entity.ExArticle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
public class GetDetailChatRoomResponse {
    private Long chatRoomExArticleId; //거래 게시글 ID
    private String chatRoomExArticleTitle; //거래 게시글 제목
    private Long chatRoomUserId; //거래 판매자
    private Long chatRoomBuyerId; //거래 구매자
    private boolean chatRoomExArticleIsDone; //거래 완료 여부

    public static GetDetailChatRoomResponse toResponse(ExArticle exArticle){
        return GetDetailChatRoomResponse.builder()
                .chatRoomExArticleId(exArticle.getId())
                .chatRoomExArticleTitle(exArticle.getTitle())
                .chatRoomUserId(exArticle.getUser().getId())
                .chatRoomExArticleIsDone(exArticle.isDone())
                .build();
    }
}
