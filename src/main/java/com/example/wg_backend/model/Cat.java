package com.example.wg_backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "cats")
public class Cat {

    @Id
    private String name;

    @Column(columnDefinition = "cat_color")
    private CatColor color;

    private long tailLength;
    private long whiskersLength;
}
