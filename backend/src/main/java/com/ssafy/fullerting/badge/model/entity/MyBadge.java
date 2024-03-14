package com.ssafy.fullerting.badge.model.entity;

import com.ssafy.fullerting.user.model.entity.User;
import jakarta.persistence.*;

@Entity
@Table(name = "my_badge")
public class MyBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int myBadgeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;

}
