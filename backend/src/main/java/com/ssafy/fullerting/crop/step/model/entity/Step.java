package com.ssafy.fullerting.crop.step.model.entity;

import com.ssafy.fullerting.crop.type.model.entity.Crop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="crop_step")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_step_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="crop_type_id")
    private Crop crop;

    @Column(name = "crop_step_growth", length = 3)
    @NotNull
    private String step; //단계

    @Column(name = "crop_step_harvest_day")
    @NotNull
    private int harvestDay; //수확일
}
