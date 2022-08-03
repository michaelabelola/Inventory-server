package com.buyern.inventory.Model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "unit")
@Data
public class MeasurementUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    /* Kilogram, Grams, Fahrenheit, Celsius */
    private String name;
    /* KG, G, F°, C°*/
    private String shortName;
    private String symbol;
    private String conversion;

}
