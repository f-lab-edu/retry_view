package com.pjw.retry_view.entity;

import java.time.ZonedDateTime;
import java.util.List;

public class Review {
    private Long id;
    private Long productId;
    private Integer rank;//star?
    private String comment;
    private List<Long> images;
    private Long createdBy;
    private ZonedDateTime createdAt;
    private Long updatedBy;
    private  ZonedDateTime updatedAt;
}
