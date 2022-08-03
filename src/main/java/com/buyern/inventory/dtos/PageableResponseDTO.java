package com.buyern.inventory.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageableResponseDTO {
    private long totalRowCount;
    private int totalPageCount;
    private Object data;
}
