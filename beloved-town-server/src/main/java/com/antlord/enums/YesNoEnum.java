package com.antlord.enums;

import lombok.Getter;

@Getter
public enum YesNoEnum {

    /**
     * 是
     */
    YES("1", 1, "是"),

    /**
     * 否
     */
    NO("0", 0, "否");

    private final String code;
    private final Integer value;
    private final String name;

    YesNoEnum(String code, Integer value, String name) {
        this.code = code;
        this.value = value;
        this.name = name;
    }
}
