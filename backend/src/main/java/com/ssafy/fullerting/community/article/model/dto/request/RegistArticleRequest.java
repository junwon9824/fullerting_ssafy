package com.ssafy.fullerting.community.article.model.dto.request;

import com.ssafy.fullerting.community.article.model.enums.ArticleType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RegistArticleRequest {


    private String title;

    private String content;

    private ArticleType type;

//    private List<MultipartFile> selectedFiles=new ArrayList<>();


}
