package com.salesianostriana.meal.search;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Criteria {

    private String key;
    private String operator;
    private Object value;

}
