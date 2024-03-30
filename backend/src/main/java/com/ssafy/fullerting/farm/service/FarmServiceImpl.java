package com.ssafy.fullerting.farm.service;

import com.ssafy.fullerting.farm.exception.FarmException;
import com.ssafy.fullerting.farm.model.dto.response.GetFarmInfoExAPIResponse;
import com.ssafy.fullerting.farm.model.dto.response.GetAllFarmResponse;
import com.ssafy.fullerting.farm.model.dto.response.Row;
import com.ssafy.fullerting.farm.model.entity.Farm;
import com.ssafy.fullerting.farm.repository.FarmRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.fullerting.farm.exception.FarmErrorCode.TRANSACTION_FAIL;

@RequiredArgsConstructor
@Service
@Slf4j
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepository;
    private final WebClient.Builder webClientBuilder;

    @Value("${api.key}")
    private String apiKey;

    @PostConstruct //어플리케이션 실행 시 자동으로 호출
    @Override
    public void getFarmInfoExAPI() { //텃밭정보 open API 호출
        WebClient webClient = webClientBuilder.baseUrl("http://211.237.50.150:7080/").build();
        webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("openapi/")
                                .path(apiKey)
                                .path("/json")
                                .path("/Grid_20171122000000000552_1")
                                .path("/1")
                                .path("/780")
                                .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(GetFarmInfoExAPIResponse.class)
                .subscribe(response -> {
                    List<Row> rows = response.getGridData().getRow();
                    for (Row row : rows) {
                        try {
                            //필요없는 데이터 필터링
                            if(!row.getFarmName().contains("[보안점검]") && !row.getFarmName().isEmpty()
                                    && !row.getAreaLcd().isEmpty() && !row.getAreaLcd().isBlank()
                                    && !row.getFarmName().isBlank() && !row.getPosLat().isEmpty()){
                                farmRepository.save(Farm.toEntity(row));
                            }
                        } catch(Exception e){
                            e.printStackTrace();
                            throw new FarmException(TRANSACTION_FAIL);
                        }
                    }
                });



    }

    @Override
    public List<GetAllFarmResponse> searchFarm(Integer region) {
        List<Farm> farmList = null;

        //전체조회
        if(region == 0) {
            farmList = farmRepository.findAll();
        }
        //지역별 검색
        else {
            farmList = farmRepository.findByAreaLcd(region);
        }

        return farmList.stream().map(GetAllFarmResponse::toResponse).collect(Collectors.toList());
    }
}
