package com.buyern.inventory.Model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "sub_categories")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private String name;
    private Long categoryId;
    private Long groupingId;
    private String entityUid;
    @Column(columnDefinition="boolean default false")
    private boolean isPrivate;
}