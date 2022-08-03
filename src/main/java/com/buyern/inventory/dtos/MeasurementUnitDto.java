package com.buyern.inventory.dtos;

import com.buyern.inventory.Model.MeasurementUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeasurementUnitDto implements Serializable {
    private Long id;
    private String name;
    private String shortName;
    private String symbol;
    private String conversion;

    public static MeasurementUnitDto create(MeasurementUnit measurementUnit) {
        return MeasurementUnitDto.builder()
                .id(measurementUnit.getId())
                .name(measurementUnit.getName())
                .shortName(measurementUnit.getShortName())
                .symbol(measurementUnit.getSymbol())
                .conversion(measurementUnit.getConversion())
                .build();
    }

    public MeasurementUnit toModel() {
        MeasurementUnit measurementUnit = new MeasurementUnit();
        measurementUnit.setId(getId());
        measurementUnit.setName(getName());
        measurementUnit.setShortName(getShortName());
        measurementUnit.setSymbol(getSymbol());
        measurementUnit.setConversion(getConversion());
        return measurementUnit;
    }
}
