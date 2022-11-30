package com.example.todoapp_trainning.search;

import com.example.todoapp_trainning.entity.Todo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class TodoSpecification implements Specification<Todo> {
    private SearchCriteria searchCriteria;

    public TodoSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Todo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        switch (searchCriteria.getOperator()) {
            case EQUALS:
                return criteriaBuilder.equal(
                        root.get(searchCriteria.getKey()),
                        searchCriteria.getValue());
            case LIKE:
                return criteriaBuilder.like(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case GREATER_THAN_OR_EQUALS:
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDateTime.class){
                    return criteriaBuilder.greaterThanOrEqualTo(
                            root.get(searchCriteria.getKey()), (LocalDateTime) searchCriteria.getValue());
                } else {
                    return criteriaBuilder.greaterThanOrEqualTo(
                            root.get(searchCriteria.getKey()),
                            String.valueOf(searchCriteria.getValue()));
                }
            case LESS_THAN:
                return criteriaBuilder.lessThan(
                        root.get(searchCriteria.getKey()),
                        String.valueOf(searchCriteria.getValue()));
            case LESS_THAN_OR_EQUALS:
                if (root.get(searchCriteria.getKey()).getJavaType() == LocalDateTime.class){
                    return criteriaBuilder.lessThanOrEqualTo(
                            root.get(searchCriteria.getKey()), (LocalDateTime) searchCriteria.getValue());
                } else {
                    return criteriaBuilder.lessThanOrEqualTo(
                            root.get(searchCriteria.getKey()),
                            String.valueOf(searchCriteria.getValue()));
                }
        }
        return null;
    }
}
