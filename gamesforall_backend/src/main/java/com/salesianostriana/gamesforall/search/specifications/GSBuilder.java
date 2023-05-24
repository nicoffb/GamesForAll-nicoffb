package com.salesianostriana.gamesforall.search.specifications;


import com.salesianostriana.gamesforall.search.util.QueryableEntity;
import com.salesianostriana.gamesforall.search.util.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

@Log
@AllArgsConstructor
public class GSBuilder<T> {

    private List<SearchCriteria> params;
    private Class type;

    public Specification<T> build(){
        List<SearchCriteria> checkedParams = params.stream()
                .filter(p -> QueryableEntity.checkQueryParams(type, p.getKey()))
                .collect(Collectors.toList());

        if (checkedParams.isEmpty())
            return null;

        Specification<T> res = new GenericSpecification<T>(params.get(0));

        for (int i = 0; i < params.size(); i++){
            res = res.and(new GenericSpecification<T>(params.get(i)));
        }

        return res;
    }

}
