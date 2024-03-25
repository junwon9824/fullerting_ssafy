package com.ssafy.fullerting.badge.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="badge")
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long badgeId;

    @Column(name = "badge_name", nullable = false, length = 20)
    private String name;

    @Column(name = "badge_img", nullable = false, length = 255)
    private String img;

    @Column(name = "badge_trigger", length = 30)
    private String trigger;

}
