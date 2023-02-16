package com.salesianostriana.gamesforall.search.specifications;


import com.salesianostriana.gamesforall.product.model.Product;
import com.salesianostriana.gamesforall.search.util.SearchCriteria;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Log
@AllArgsConstructor
public class ProductSpecification implements Specification<Product> {

    private SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (searchCriteria.getOperator().equalsIgnoreCase(">")){
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString()
            );
        } else if (searchCriteria.getOperator().equalsIgnoreCase("<")) {
            if(Arrays.stream(root.get(searchCriteria.getKey()).getJavaType().getInterfaces()).anyMatch(
                    c -> c.getName() == "java.time.temporal.Temporal"
            )){
                Comparable val = null;

                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDate.class){
                    val = LocalDate.parse(searchCriteria.getValue().toString());
                } else if (root.get(searchCriteria.getKey()).getJavaType() == LocalDateTime.class) {
                    val = LocalDateTime.parse(searchCriteria.getValue().toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss"));
                    log.info(val.toString());
                }
                return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), val);
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        } else if (searchCriteria.getOperator().equalsIgnoreCase(":")) {
            if (root.get(searchCriteria.getKey()).getJavaType() == String.class){
                return criteriaBuilder.like(
                        root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue().toString() + "%"
                );
            } else if (root.get(searchCriteria.getKey()).getJavaType().toString().equalsIgnoreCase("boolean")) {
                boolean value = searchCriteria.getValue().toString().equalsIgnoreCase("true") ? true : false;
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), value);
            }else {
                return criteriaBuilder.equal(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue());
            }
        }

        return null;
    }
}
