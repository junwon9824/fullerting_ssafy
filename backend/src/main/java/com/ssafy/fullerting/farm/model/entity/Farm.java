package com.ssafy.fullerting.farm.model.entity;

import com.ssafy.fullerting.farm.model.dto.response.GetFarmInfoExAPIResponse;
import com.ssafy.fullerting.farm.model.dto.response.Row;
import com.ssafy.fullerting.record.packdiary.model.entity.PackDiary;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name="farm")
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Getter
@ToString
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "farm_id")
    private Long id;

    @Column(name = "farm_type", length = 30)
    private String type; //운영주체

    @Column(name = "farm_nm")
    private String name; //텃밭명

    @Column(name = "farm_area_lnm", length = 15)
    private String areaLnm; //도

    @Column(name = "farm_area_mnm", length = 30)
    private String areaMnm; //시구

    @Column(name = "farm_address")
    private String address; //주소

    @Column(name = "farm_off_site")
    private String offSite; //부대시설

    @Column(name = "farm_poslat")
    private float posLat; //위도

    @Column(name = "farm_poslng")
    private float posLng; //경도

    public static Farm toEntity(Row row){
        return Farm.builder()
                .type(row.getFarmType())
                .name(row.getFarmName())
                .areaLnm(row.getAreaLnm())
                .areaMnm(row.getAreaMnm())
                .address(row.getAddress())
                .offSite(row.getOffSite())
                .posLat(Float.parseFloat(row.getPosLat()))
                .posLng(Float.parseFloat(row.getPosLng()))
                .build();
    }

}
