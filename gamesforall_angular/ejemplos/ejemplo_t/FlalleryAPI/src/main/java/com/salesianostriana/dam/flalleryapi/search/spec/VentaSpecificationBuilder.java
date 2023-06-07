package com.salesianostriana.dam.flalleryapi.search.spec;

import com.salesianostriana.dam.flalleryapi.models.Venta;
import com.salesianostriana.dam.flalleryapi.search.util.SearchCriteria;

import java.util.List;

public class VentaSpecificationBuilder extends GenericSpecificationBuilder<Venta> {
    public VentaSpecificationBuilder(List<SearchCriteria> params) {
        super(params, Venta.class);
    }
}
