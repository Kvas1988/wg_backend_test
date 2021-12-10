package com.example.wg_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity(name = "Cat")
@Table(name = "cats")
// @TypeDef(
//         name = "pgsql_enum",
//         typeClass = PostgreSQLEnumType.class
// )
public class Cat {

    @Id
    @Column(unique = true, nullable = false)
    @NotNull
    private String name;

    // @Enumerated(EnumType.STRING)
    // @Column(columnDefinition = "cat_color")
    // // @Type( type = "pgsql_enum" )
    // private CatColor color;

    @Min(0)
    @JsonProperty("tail_length")
    private long tailLength;

    @NotNull
    @Min(0)
    @JsonProperty("whiskers_length")
    private long whiskersLength;

    // ========= WORKING =========
    @Basic
    @Column(columnDefinition = "cat_color")
    private String color; // persistent

    @JsonIgnore
    @Transient
    // @Valid
    private CatColor colorTransient; // transient

    @PostLoad
    void fillTransient() {
        if (color != null) {
            this.colorTransient = CatColor.fromString(color);
        }
    }

    @PrePersist
    void fillPersistent() {
        if (colorTransient != null) {
            this.color = colorTransient.toString();
        }
    }
    // ========= WORKING =========
}
