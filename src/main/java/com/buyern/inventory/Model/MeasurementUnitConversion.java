package com.buyern.inventory.Model;

import com.buyern.inventory.Enums.UnitConversionMethod;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "unitConversion")
@Data
public class MeasurementUnitConversion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
//   conver from celsius to fahrenheit
    private String name;
    @OneToOne
    @JoinColumn(name = "from_unit_id")
    private MeasurementUnit fromUnit;
    @OneToOne
    @JoinColumn(name = "to_unit_id")
    private MeasurementUnit toUnit;
    @Enumerated(EnumType.STRING)
    private UnitConversionMethod conversionMethod;
    /**
     * <h3>Equation for convert from one mathematical unit to another mathematical unit</h3>
     * @implNote this is only for mathematical conversions
     */
    private String conversionFunction;

}
