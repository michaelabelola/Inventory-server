package com.buyern.inventory.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
/**
 * <b>Examples: </b>Apparels, Heavy Machines, furniture, Electronics, Houses, Building Materials, Factory Equipments */
@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;
    private String entityUid;
    @Column(columnDefinition="boolean default false")
    private boolean isPrivate;
}