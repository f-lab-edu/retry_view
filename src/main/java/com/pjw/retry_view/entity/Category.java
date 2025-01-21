package com.pjw.retry_view.entity;

import com.pjw.retry_view.converter.CategoryTypeEnumConverter;
import com.pjw.retry_view.dto.CategoryView;
import com.pjw.retry_view.enums.CategoryType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "category")
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name ="type")
    @Convert(converter = CategoryTypeEnumConverter.class)
    private CategoryType type;
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "name")
    private String name;

    @Builder
    public Category(Long id, CategoryType type, Long groupId, String name) {
        this.id = id;
        this.type = type;
        this.groupId = groupId;
        this.name = name;
    }

    public CategoryView toDTO(){
        return CategoryView.builder()
                .id(id)
                .type(type)
                .groupId(groupId)
                .name(name)
                .build();
    }
}
