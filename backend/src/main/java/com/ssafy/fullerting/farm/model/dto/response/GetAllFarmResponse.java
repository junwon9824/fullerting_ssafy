package com.ssafy.fullerting.farm.model.dto.response;

import com.ssafy.fullerting.farm.model.entity.Farm;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetAllFarmResponse {
    private Long farmId;
    private String farmType; //운영주체
    private String farmName; //텃밭명
    private String farmAreaLnm; //도
    private String farmAreaMnm; //시구
    private String farmAddress; //주소
    private String farmOffSite; //부대시설
    private float farmPosLat; //위도
    private float farmPosLng; //경도

    public static GetAllFarmResponse toResponse(Farm farm){
        return GetAllFarmResponse.builder()
                .farmId(farm.getId())
                .farmType(farm.getType())
                .farmName(farm.getName())
                .farmAreaLnm(farm.getAreaLnm())
                .farmAreaMnm(farm.getAreaMnm())
                .farmAddress(farm.getAddress())
                .farmOffSite(farm.getOffSite())
                .farmPosLat(farm.getPosLat())
                .farmPosLng(farm.getPosLng())
                .build();
    }
}
