package com.pjw.retry_view.spec;

import com.pjw.retry_view.entity.Board;
import org.springframework.data.jpa.domain.Specification;

public class BoardSpecification {

    public static Specification<Board> likeTitle(String title){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%"+title+"%");
    }

    public static Specification<Board> gtId(Long id){
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"), id);
    }

    public static Specification<Board> eqType(String boardType){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), boardType);
    }

    private BoardSpecification(){}
}
