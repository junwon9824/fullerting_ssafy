package com.ssafy.fullerting.record.packdiary.model.entity;

import com.ssafy.fullerting.crop.type.model.entity.Crop;
import com.ssafy.fullerting.user.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name="pack_diary")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class PackDiary { //작물일지
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pack_diary_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne
    @JoinColumn(name="crop_type_id")
    private Crop crop; //작물

    @Column(name = "pack_diary_title", length = 30)
    @NotNull
    private String title; //제목

    @Column(name = "pack_diary_cul_start_at")
    @NotNull
    private Date culStartAt; //재배시작일

    @Column(name = "pack_diary_cul_end_at")
    private Date culEndAt; //재배종료일

    @Column(name = "pack_diary_growth_step", columnDefinition = "INT DEFAULT 0")
    @NotNull
    private int growthStep; //작물단계

    @Column(name = "pack_diary_created_at")
    @NotNull
    private Timestamp createdAt; //생성일

}
