package com.ssafy.fullerting.badge.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name="badge")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int badgeId;

    @Column(name = "badge_name", nullable = false, length = 20)
    private String name;

    @Column(name = "badge_img", nullable = false, length = 255)
    private String img;

}