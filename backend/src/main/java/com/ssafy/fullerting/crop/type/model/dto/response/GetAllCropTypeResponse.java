package com.ssafy.fullerting.crop.type.model.dto.response;

import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.crop.type.model.entity.enums.CropType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GetAllCropTypeResponse {
    private Long cropTypeId;
    private String cropTypeName;
    private String cropTypeImgUrl;

    public static GetAllCropTypeResponse fromResponse(Crop crop){
        return GetAllCropTypeResponse.builder()
                .cropTypeId(crop.getId())
                .cropTypeName(crop.getName())
                .cropTypeImgUrl(crop.getImgUrl())
                .build();
    }
}
