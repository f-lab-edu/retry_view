package com.pjw.retry_view.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private Long id;
    @NotNull(message = "카테고리는 필수 입력값입니다.")
    private Long mainCategoryId;
    private Long subCategoryId;
    @NotNull(message = "제품명은 필수 입력값입니다.")
    private String name;
    private Integer price;
    @NotNull(message = "브랜드명은 필수 입력값입니다.")
    private String brand;
    private String detail;
    private String imageUrl;
}
