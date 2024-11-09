package com.pjw.retry_view.dto;

import com.pjw.retry_view.entity.Category;
import com.pjw.retry_view.enums.CategoryType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private CategoryType type;
    private Long groupId;
    private String name;

    @Builder
    public CategoryDTO(Long id, CategoryType type, Long groupId, String name) {
        this.id = id;
        this.type = type;
        this.groupId = groupId;
        this.name = name;
    }

    public Category toEntity(){
        return Category.builder()
                .id(id)
                .type(type)
                .groupId(groupId)
                .name(name)
                .build();
    }
}
