package com.company.recruit.common.core.api;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private Integer pageNo;
    private Integer pageSize;
    private Long total;
    private Integer pages;
    private List<T> records;

    public static <T> PageResponse<T> of(Integer pageNo, Integer pageSize, Long total, List<T> records) {
        long safeTotal = total == null ? 0L : total;
        int safePageSize = pageSize == null || pageSize <= 0 ? 10 : pageSize;
        int pages = (int) ((safeTotal + safePageSize - 1) / safePageSize);
        return PageResponse.<T>builder()
            .pageNo(pageNo)
            .pageSize(pageSize)
            .total(safeTotal)
            .pages(pages)
            .records(records)
            .build();
    }
}