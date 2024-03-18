package com.ssafy.fullerting.record.steplog.model.entity;

import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="crop_step_log")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class StepLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_step_log_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="pack_diary_id")
    private PackDiary packDiary;

    @OneToOne
    @JoinColumn(name="pack_diary_id")
    private String step; //단계

    @Column(name = "crop_step_harvest_day")
    @NotNull
    private int harvestDay; //수확일
}
