package com.ssafy.fullerting.crop.tip.service;

import com.ssafy.fullerting.crop.tip.model.dto.response.GetAllTipsForCropResponse;
import com.ssafy.fullerting.crop.tip.model.entity.Tip;
import com.ssafy.fullerting.crop.tip.repository.CropTipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class CropTipServiceImpl implements CropTipService{
    private final CropTipRepository cropTipRepository;

    @Override
    public List<GetAllTipsForCropResponse> getAllTipsForCrop(Long cropTypeId) {
        List<Tip> tipList = cropTipRepository.findAllByCropId(cropTypeId);
        return tipList.stream().map(GetAllTipsForCropResponse::fromResponse).collect(Collectors.toList());
    }
}
