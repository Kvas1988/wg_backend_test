package com.example.wg_backend.model;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum CatColor {
    BLACK("black"),
    WHITE("white"),
    BLACK_AND_WHITE("black & white"),
    RED("red"),
    RED_AND_WHITE("red & white"),
    RED_AND_BLACK_AND_WHITE("red & black & white");

    private String color;

    CatColor(String color) {
        this.color = color;
    }

    // public String getColor() {
    //     return this.color;
    // }

    @Override
    @JsonValue
    public String toString() {
        return this.color;
    }

    // @JsonCreator
    public static CatColor fromString(String s) {
        if (s == null) {
            return null;
        }

        return Stream.of(CatColor.values())
                .filter(en -> en.toString().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
