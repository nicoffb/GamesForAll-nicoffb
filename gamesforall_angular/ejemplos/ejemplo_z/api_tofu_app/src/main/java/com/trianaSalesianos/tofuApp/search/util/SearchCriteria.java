package com.trianaSalesianos.tofuApp.search.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchCriteria {
    private String key;
    private String operator;
    private Object value;
}
