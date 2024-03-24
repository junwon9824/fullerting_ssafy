package com.ssafy.fullerting.crop.step.model.entity;

import com.ssafy.fullerting.crop.type.model.entity.Crop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "crop_step")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class Step { //작물단계
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_step_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "crop_type_id")
    private Crop crop; //작물

    @Column(name = "crop_step_growth")
    @NotNull
    private int step; //단계

    @Column(name = "crop_step_harvest_day")
    @NotNull
    private int harvestDay; //수확일
}
