package com.company.recruit.common.core.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PageQuery {

    @Min(1)
    private Integer pageNo = 1;

    @Min(1)
    @Max(100)
    private Integer pageSize = 10;
}