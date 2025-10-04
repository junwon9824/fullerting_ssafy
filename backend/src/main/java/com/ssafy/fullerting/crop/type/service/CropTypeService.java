package com.ssafy.fullerting.crop.type.service;

import com.ssafy.fullerting.crop.type.model.dto.response.GetAllCropTypeResponse;

import java.util.List;

public interface CropTypeService {
    List<GetAllCropTypeResponse> getAllCropType();
}
