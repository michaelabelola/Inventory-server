package com.buyern.inventory.Objects;

import com.buyern.inventory.Enums.FeatureType;
import com.buyern.inventory.Model.InventoryFeature;
import com.buyern.inventory.Model.MeasurementUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * <b>EXAMPLE: </b>
 * {id:123, name:"color", value:"red", "visible":"true" }
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class InventoryItemFeature {
    private Long id;
    /**
     * only parent and standalone inventory can have this
     */
    private String name;
    /**
     * only non parent (standalone) and child inventory can have this
     */
    private FeatureValue value;
    private Double MinValue;
    private Double MaxValue;
    @Enumerated(EnumType.STRING)
    private FeatureType type;
    /**
     * only parent and standalone inventory can have this
     */
    private boolean visible;
    /**
     * <h3>unit of measurement if any</h3>
     */
    private MeasurementUnit measurementUnit;
    private Long parentFeatureId;
}
