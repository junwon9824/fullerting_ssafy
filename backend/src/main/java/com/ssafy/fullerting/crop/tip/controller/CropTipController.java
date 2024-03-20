package com.ssafy.fullerting.crop.tip.controller;

import com.ssafy.fullerting.crop.tip.service.CropTipService;
import com.ssafy.fullerting.global.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/crop-tips")
@RequiredArgsConstructor //생성자 주입
@Slf4j
public class CropTipController {
    private final CropTipService cropTipService;

    @GetMapping("{crop_type_id}")
    public ResponseEntity<MessageUtils> getAllTipsForCrop(@PathVariable("crop_type_id") Long cropTypeId){
        return ResponseEntity.ok().body(MessageUtils.success(cropTipService.getAllTipsForCrop(cropTypeId)));
    }
}
