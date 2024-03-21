package com.ssafy.fullerting.record.packdiary.service;

import com.ssafy.fullerting.record.packdiary.model.dto.response.AICropStepResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {
    private final WebClient webClient;

    public WebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:8000").build();
//        this.webClient = webClientBuilder.baseUrl("https://j10c102.p.ssafy.io").build();
    }

    public Mono<AICropStepResponse> callAIApi(MultipartFile imageFile) {

        return webClient.post()
                .uri("/v1/calc")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("image", imageFile))
                .retrieve()
                .bodyToMono(AICropStepResponse.class);
    }
}
