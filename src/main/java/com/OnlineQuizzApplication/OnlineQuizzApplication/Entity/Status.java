package com.OnlineQuizzApplication.OnlineQuizzApplication.Entity;

import lombok.Getter;

@Getter
public enum Status {
    PASS("Pass"),
    FAIL("Fail");

    private final String value;

    Status(String value) {
        this.value = value;
    }
}
