package com.example.wg_backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Cat {
    public enum CatColor {
        BLACK,
        WHITE,
        BLACK_AND_WHITE,
        RED,
        RED_AND_WHITE,
        RED_AND_BLACK_AND_WHITE
    }

    @Id
    private String name;
    private CatColor color;
    private long tailLength;
    private long whiskersLength;

}
