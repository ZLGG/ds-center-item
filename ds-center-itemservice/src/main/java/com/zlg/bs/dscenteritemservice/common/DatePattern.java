package com.zlg.bs.dscenteritemservice.common;

public enum DatePattern {
    MONTH_PATTERN("yyyy-MM"),
    DATE_PATTERN("yyyy-MM-dd"),
    DATETIME_PATTERN("yyyy-MM-dd HH:mm:ss"),
    DATETIME_PATTERN_HHMM("yyyy-MM-dd HH:mm");

    private String pattern;

    private DatePattern(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return this.pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}
