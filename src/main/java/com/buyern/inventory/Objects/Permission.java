package com.buyern.inventory.Objects;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
@Data
@Builder
public class Permission {
    /**
     * e.g finance, inventory, permission, Order
     * or
     * e.g read, write, create, delete*/
    private String name;
    private Map<String, String> main;
}

