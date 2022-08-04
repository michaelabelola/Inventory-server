package com.buyern.inventory.Objects;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ToolPermission {
private String tool;
    private Map<String, String> main;
}
