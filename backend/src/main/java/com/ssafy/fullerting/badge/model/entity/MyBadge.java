package com.ssafy.fullerting.badge.model.entity;

import com.ssafy.fullerting.user.model.entity.CustomUser;
import jakarta.persistence.*;

@Entity
@Table(name = "my_badge")
public class MyBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int myBadgeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser customUser;


    @OneToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;

}
