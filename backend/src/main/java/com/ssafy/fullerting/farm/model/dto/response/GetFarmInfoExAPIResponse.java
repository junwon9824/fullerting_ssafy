package com.ssafy.fullerting.farm.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetFarmInfoExAPIResponse { //텃밭정보 API로부터 받은 정보
    @JsonProperty("Grid_20171122000000000552_1")
    private GridData gridData;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class GridData {
        private List<Row> row;
    }
}
