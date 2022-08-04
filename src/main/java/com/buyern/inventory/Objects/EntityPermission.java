package com.buyern.inventory.Objects;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EntityPermission {
    private String entityId;
    private List<ToolPermission> tools;
}
