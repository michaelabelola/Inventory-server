package com.buyern.inventory.Objects;

import com.buyern.inventory.Enums.CustomerType;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Enumerated;

@Data
public class Customer {
    private String id;
    private String name;
    @Enumerated
    private CustomerType customerType;

}
