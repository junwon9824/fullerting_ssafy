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
    private String farmType;

    @JsonProperty("FARM_NM")
    private String farmName;

    @JsonProperty("AREA_LNM")
    private String areaLnm;

    @JsonProperty("AREA_MNM")
    private String areaMnm;

    @JsonProperty("ADDRESS1")
    private String address;

    @JsonProperty("OFF_SITE")
    private String offSite;

    @JsonProperty("POSLAT")
    private String posLat;

    @JsonProperty("POSLNG")
    private String posLng;

    @Override
    public String toString() {
        return "Row{" +
                "farmType='" + farmType + '\'' +
                ", farmName='" + farmName + '\'' +
                ", areaLnm='" + areaLnm + '\'' +
                ", areaMnm='" + areaMnm + '\'' +
                ", address='" + address + '\'' +
                ", offSite='" + offSite + '\'' +
                ", posLat='" + posLat + '\'' +
                ", posLng='" + posLng + '\'' +
                '}';
    }
}
