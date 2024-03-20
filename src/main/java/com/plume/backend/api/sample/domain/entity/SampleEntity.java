package com.plume.backend.api.sample.domain.entity;

import com.plume.backend.api.sample.domain.enums.SampleEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "SAMPLE")
public class SampleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private long seq;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "SAMPLE_COLUMN1")
    private String sampleColumn1;

    @Column(name = "SAMPLE_COLUMN2")
    private String sampleColumn2;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private SampleEnum type;

    @Column(name = "REG_DT")
    private LocalDateTime regDt;

    @Column(name = "DEL_YN")
    private String delYn;

}
