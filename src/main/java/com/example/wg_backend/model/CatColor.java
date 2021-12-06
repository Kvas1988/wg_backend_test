package com.example.wg_backend.model;

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

    public String getColor() {
        return this.color;
    }

    @Override
    public String toString() {
        return this.color;
    }
}
