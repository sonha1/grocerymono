package com.tks.grocerymono.utils;

import lombok.Getter;

@Getter
public enum DateTimeFormat {

    dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm_COLON_ss("dd/MM/yyyy HH:mm:ss"),
    dd_SLASH_MM_SLASH_yyyy("dd/MM/yyyy"),
    MM_SLASH_yyyy("MM/yyyy"),
    yyyyMMdd_SPACE_HHmmss("yyyyMMdd HHmmss"),
    dd_DASH_MM_DASH_yyyy_SPACE_HH_COLON_mm_COLON_ss("yyyy-MM-dd HH:mm:ss"),
    yyyyMMddHHmmss("yyyyMMddHHmmss"),
    dd_SLASH_MM_SLASH_yyyy_SPACE_HH_COLON_mm("dd/MM/yyyy HH:mm"),
    dd_SLASH_MM_SLASH_yyyy_SPACE_HH_mm_ss("dd/MM/yyyy HHmmss"),
    dd_DASH_MM_DASH_yyyy_UNDERSCORE_HH_COLON_mm_COLON_ss("yyyy-MM-dd_HH:mm:ss"),

    yy_MM_dd_HH_mm_ss_S("yyMMddHHmmssS"),
    yyyy_DASH_MM_DASH_dd("yyyy-MM-dd"),
    ;

    private final String format;

    DateTimeFormat(String format) {
        this.format = format;
    }

}
