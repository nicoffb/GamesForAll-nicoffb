package com.salesianostriana.meal.search;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@AllArgsConstructor
public class SpecBuilder<T> {

    private List<Criteria> params;
    private Class type;

    public Specification<T> build(){
        if(params.isEmpty()){ return null; }

        Specification<T> result = new GenericSpecification<T>(params.get(0));
        for(int i = 1; i < params.size(); i++) {
            result = result.and(new GenericSpecification<T>(params.get(i)));
        }

        return result;
    }

}
