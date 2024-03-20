package com.ssafy.fullerting.crop.type.controller;

import com.ssafy.fullerting.crop.type.service.CropTypeService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/crop_types")
@RequiredArgsConstructor //생성자 주입
@Slf4j
public class CropTypeController {
    private final CropTypeService cropTypeService;

    @GetMapping
    public ResponseEntity<MessageUtils> getAllCropType() {
        return ResponseEntity.ok().body(MessageUtils.success(cropTypeService.getAllCropType()));
    }
}
