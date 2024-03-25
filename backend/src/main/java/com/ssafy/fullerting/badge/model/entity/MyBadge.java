package com.ssafy.fullerting.badge.model.entity;

import com.ssafy.fullerting.badge.model.dto.response.MyBadgeResponse;
import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "my_badge")
public class MyBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long myBadgeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser customUser;


    @OneToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;


}
