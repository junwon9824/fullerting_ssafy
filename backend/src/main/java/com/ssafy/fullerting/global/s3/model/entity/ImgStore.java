package com.ssafy.fullerting.global.s3.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "img_store")
public class ImgStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imgStoreId;

    private String url;
}
