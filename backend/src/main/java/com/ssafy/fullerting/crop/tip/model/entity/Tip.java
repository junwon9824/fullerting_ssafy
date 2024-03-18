package com.ssafy.fullerting.crop.tip.model.entity;

import com.ssafy.fullerting.crop.step.model.entity.Step;
import com.ssafy.fullerting.crop.type.model.entity.Crop;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="crop_tip")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class Tip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_tip_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="crop_type_id")
    private Crop crop;

    @ManyToOne
    @JoinColumn(name="crop_step_id")
    private Step step; //단계

    @Column(name = "crop_tip_content", length = 100)
    @NotNull
    private String content; //내용
}
