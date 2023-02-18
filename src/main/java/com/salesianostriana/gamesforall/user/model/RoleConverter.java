package com.salesianostriana.gamesforall.user.model;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleConverter implements AttributeConverter<Set<UserRole>,String> {


    @Override
    public String convertToDatabaseColumn(Set<UserRole> attribute) {
        return attribute.stream().map(u->u.name()).collect(Collectors.joining(","));
    }

    @Override
    public Set<UserRole> convertToEntityAttribute(String dbData){
        Set<UserRole> result = new HashSet<>();
        Arrays.stream(dbData.split(",")).forEach(u->result.add(UserRole.valueOf(u)));
        return result;
        //TRANSFORMA (USER,ADMIN,ANOTHER) en un set de user roles pero como un enum;
    }
}
