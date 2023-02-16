package com.salesianostriana.gamesforall.search.specifications;


import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.search.util.SearchCriteria;

import java.util.List;

public class PSBuilder extends GSBuilder<Product>{
    public PSBuilder(List<SearchCriteria> params) {
        super(params, Product.class);
    }
}
