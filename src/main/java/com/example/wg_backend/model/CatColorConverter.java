package com.example.wg_backend.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CatColorConverter implements AttributeConverter<CatColor, String> {
    @Override
    public String convertToDatabaseColumn(CatColor catColor) {
        if (catColor == null) {
            return null;
        }

        return catColor.getColor();
    }

    @Override
    public CatColor convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }

        return Stream.of(CatColor.values())
                .filter(en -> en.getColor().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
