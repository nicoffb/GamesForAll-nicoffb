package com.salesianostriana.gamesforall.search.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Extractor {

    static List<SearchCriteria> extractSearchCriteriaList(String search){
        List<SearchCriteria> params = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([\\D\\S-_]+?),");
        if (!search.endsWith(",")){
            search = search+",";
        }
        Matcher matcher = pattern.matcher(search);

        while (matcher.find()){
            String key = matcher.group(1);
            String operator = matcher.group(2);
            Object value = matcher.group(3);

            params.add(new SearchCriteria(key, operator, value));
        }

        return params;
    }

}
