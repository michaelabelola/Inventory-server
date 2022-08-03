package com.buyern.inventory.dtos;

import com.buyern.inventory.Enums.FeatureType;
import com.buyern.inventory.Model.InventoryFeature;
import com.buyern.inventory.Objects.FeatureValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class InventoryFeatureDto implements Serializable {
    private Long id;
    private String name;
    private ArrayList<FeatureValue> values;
    @Enumerated(EnumType.STRING)
    private FeatureType type;
    private double minValue;
    private double maxValue;
    private MeasurementUnitDto measurementUnit;
    private String entityId;
    private Long categoryId;
    private Long subCategoryId;
    private boolean visible = true;
    private InventoryFeatureDto parentFeature;

    public static InventoryFeatureDto create(InventoryFeature inventoryFeature) {
        ArrayList<FeatureValue> values = null;
        try {
            if (inventoryFeature.get_values() != null && !inventoryFeature.get_values().equals("")) {
                values = new ObjectMapper().readValue(inventoryFeature.get_values(), new TypeReference<ArrayList<FeatureValue>>() {
                });
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return InventoryFeatureDto.builder()
                .id(inventoryFeature.getId())
                .name(inventoryFeature.getName())
                .type(inventoryFeature.getType())
                .values(values)
                .minValue(inventoryFeature.getMinValue())
                .maxValue(inventoryFeature.getMaxValue())
                .measurementUnit(inventoryFeature.getMeasurementUnit() != null ? MeasurementUnitDto.create(inventoryFeature.getMeasurementUnit()) : null)
                .entityId(inventoryFeature.getEntityId())
                .categoryId(inventoryFeature.getCategoryId())
                .subCategoryId(inventoryFeature.getSubCategoryId())
                .visible(inventoryFeature.isVisible())
                .parentFeature(inventoryFeature.getParentFeature() != null ? InventoryFeatureDto.create(inventoryFeature.getParentFeature()) : null)
                .build();
    }

    public InventoryFeature toModel() {

        InventoryFeature inventoryFeature = new InventoryFeature();
        inventoryFeature.setId(getId());
        inventoryFeature.setName(getName());
        inventoryFeature.setType(getType());
        inventoryFeature.setMinValue(getMinValue());
        inventoryFeature.setMaxValue(getMaxValue());
        inventoryFeature.setEntityId(getEntityId());
        inventoryFeature.setMeasurementUnit(getMeasurementUnit() != null ? getMeasurementUnit().toModel() : null);
        inventoryFeature.setCategoryId(getCategoryId());
        inventoryFeature.setSubCategoryId(getSubCategoryId());
        inventoryFeature.setVisible(isVisible());
        inventoryFeature.setParentFeature(getParentFeature() != null ? getParentFeature().toModel() : null);
        try {
            if (getValues() != null && getValues().size() > 0) {
                inventoryFeature.set_values(new ObjectMapper().writeValueAsString(getValues()));
            }
        } catch (JsonProcessingException e) {
            inventoryFeature.set_values(null);
        }
        return inventoryFeature;
    }
}
