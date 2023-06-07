package com.salesianostriana.meal.model.converter;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        if (stringList.isEmpty()) {
            return null;
        }
        return stringList.stream().collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if(s.isEmpty()){
            return new ArrayList<>();
        }
        return List.of(s.split(SEPARATOR));
    }
}
