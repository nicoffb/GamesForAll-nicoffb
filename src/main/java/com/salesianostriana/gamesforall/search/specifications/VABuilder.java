package com.salesianostriana.gamesforall.search.specifications;


import com.salesianostriana.gamesforall.search.util.SearchCriteria;
import com.salesianostriana.gamesforall.valoration.model.Valoration;

import java.util.List;

public class VABuilder extends GSBuilder<Valoration> {
    public VABuilder(List<SearchCriteria> params) {
        super(params, Valoration.class);
    }
}
