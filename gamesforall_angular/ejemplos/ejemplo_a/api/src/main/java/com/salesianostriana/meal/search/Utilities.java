package com.salesianostriana.meal.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Utilities {

    static List<Criteria> extractCriteria(String search) {
        List<Criteria> params = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([\\D\\S-_]+?),");
        if (!search.endsWith(",")) {
            search = search + ",";
        }
        Matcher matcher = pattern.matcher(search);
        while(matcher.find()) {
            params.add(new Criteria(matcher.group(1), matcher.group(2), matcher.group(3)));
        }
        return params;
    }

    static boolean checkParam(String nombre, Class clazz){
        return Arrays.stream(clazz.getDeclaredFields()).anyMatch(f -> f.getName().equalsIgnoreCase(nombre));
    }

    static List<String> extractList(String listCriteria){
        List<String> lista = new ArrayList<>();
        Pattern pattern = Pattern.compile("(\\w+?)+");
        Matcher matcher = pattern.matcher(listCriteria);
        while (matcher.find())
            lista.add(matcher.group());
        return lista;
    }

}
