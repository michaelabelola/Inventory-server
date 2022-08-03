package com.buyern.inventory.Objects;

import java.util.Map;

public class Permission {
    /**
     * e.g finance, inventory, permission, Order
     * or
     * e.g read, write, create, delete*/
    private String tool;
    private Map<String, Permission> main;
}
