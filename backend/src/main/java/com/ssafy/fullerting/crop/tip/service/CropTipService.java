package com.ssafy.fullerting.crop.tip.service;

import com.ssafy.fullerting.crop.tip.model.dto.response.GetAllTipsForCropResponse;

import java.util.List;

public interface CropTipService {
    List<GetAllTipsForCropResponse> getAllTipsForCrop(Long cropTypeId);
}
