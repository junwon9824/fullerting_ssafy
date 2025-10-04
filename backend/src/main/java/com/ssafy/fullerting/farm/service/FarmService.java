package com.ssafy.fullerting.farm.service;

import com.ssafy.fullerting.farm.model.dto.response.GetAllFarmResponse;

import java.util.List;

public interface FarmService {
    void getFarmInfoExAPI();
    List<GetAllFarmResponse> searchFarm(Integer region);
}
