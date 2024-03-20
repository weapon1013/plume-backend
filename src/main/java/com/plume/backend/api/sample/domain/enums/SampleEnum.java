package com.plume.backend.api.sample.domain.enums;

import lombok.Getter;

@Getter
public enum SampleEnum {

    BOARD("게시판"),
    NOTICE("공지사항");

    SampleEnum(String value) {this.value = value;}

    private final String value;
}
