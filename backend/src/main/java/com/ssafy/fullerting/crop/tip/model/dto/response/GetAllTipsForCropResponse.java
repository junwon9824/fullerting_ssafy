package com.ssafy.fullerting.crop.tip.model.dto.response;

import com.ssafy.fullerting.crop.tip.model.entity.Tip;
import com.ssafy.fullerting.crop.type.model.dto.response.GetAllCropTypeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetAllTipsForCropResponse {
    private Long cropTipId;
    private int cropTipGrowthStep;
    private String cropTipContent;

    public static GetAllTipsForCropResponse fromResponse(Tip tip){
        return GetAllTipsForCropResponse.builder()
                .cropTipId(tip.getId())
                .cropTipGrowthStep(tip.getStep())
                .cropTipContent(tip.getContent())
                .build();
    }
}
