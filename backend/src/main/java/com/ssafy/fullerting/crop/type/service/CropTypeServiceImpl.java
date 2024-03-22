package com.ssafy.fullerting.crop.type.service;

import com.ssafy.fullerting.crop.type.model.dto.response.GetAllCropTypeResponse;
import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.crop.type.repository.CropTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class CropTypeServiceImpl implements CropTypeService{
    private final CropTypeRepository cropTypeRepository;

    @Override
    public List<GetAllCropTypeResponse> getAllCropType() {
        List<Crop> cropList = cropTypeRepository.findAll();
        return cropList.stream().map(GetAllCropTypeResponse::toResponse).collect(Collectors.toList());
    }
}
