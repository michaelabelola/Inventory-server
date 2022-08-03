package com.buyern.inventory.Model;

import com.buyern.inventory.Enums.FeatureType;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "features")
public class InventoryFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    /**
     * only parent and standalone inventory can have this
     */
    private String name;
    /**
     * only non parent (standalone) and child inventory can have this
     */
    @Column(columnDefinition = "LONGTEXT")
    private String _values;
    private String value;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FeatureType type;
    /**
     * if type is number
     */
    private double minValue;
    /**
     * if type is number
     */
    private double maxValue;
    /**
     * owner entity id.
     * null if its is default
     */
    @OneToOne
    @JoinColumn(name = "measurement_unit_id")
    private MeasurementUnit measurementUnit;
    private String entityId;

    private Long categoryId;

    private Long subCategoryId;
    /**
     * only parent and standalone inventory can have this
     */
    private boolean visible = true;
    /**
     * <h3>id of parent feature</h3>
     * only child feature or features with values can have this
     */
    @OneToOne
    @JoinColumn(name = "parent_id")
    private InventoryFeature parentFeature;

}