package com.ssafy.fullerting.farm.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Row {
    @JsonProperty("FARM_TYPE")
    private String farmType; //운영주체

    @JsonProperty("FARM_NM")
    private String farmName; //텃밭명
    
    @JsonProperty("AREA_LCD")
    private String areaLcd; //시도코드

    @JsonProperty("AREA_LNM")
    private String areaLnm; //시도명

    @JsonProperty("AREA_MCD")
    private String areaMcd; //시군구코드

    @JsonProperty("AREA_MNM")
    private String areaMnm; //시군구명

    @JsonProperty("ADDRESS1")
    private String address; //주소

    @JsonProperty("OFF_SITE")
    private String offSite; //부대시설

    @JsonProperty("POSLAT")
    private String posLat; //위도좌표

    @JsonProperty("POSLNG")
    private String posLng; //경도좌표
}
