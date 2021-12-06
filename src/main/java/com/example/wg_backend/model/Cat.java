package com.example.wg_backend.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "cats")
@TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)
public class Cat {



    @Id
    private String name;

    // @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "cat_color")
    // @Type(type = "pgsql_enum")
    private CatColor color;

    private long tailLength;
    private long whiskersLength;
}
