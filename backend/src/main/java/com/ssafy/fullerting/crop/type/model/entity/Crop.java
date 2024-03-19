package com.ssafy.fullerting.crop.type.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="crop_type")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_type_id")
    private Long id;

    @Column(name = "crop_type_name", length = 30)
    @NotNull
    private String name; //작물 이름

    @Column(name = "crop_type_img_url")
    @NotNull
    private String imgUrl; //작물 이미지
}
