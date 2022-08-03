package com.buyern.inventory.dtos;

import com.buyern.inventory.Model.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryDto implements Serializable {
    private Long id;
    private String name;
    private Long categoryId;
    private Long groupingId;
    private String entityUid;
    private boolean isPrivate;

    public static SubCategoryDto create(SubCategory subCategory) {
        SubCategoryDto subCategoryDto = new SubCategoryDto();
        subCategoryDto.setId(subCategory.getId());
        subCategoryDto.setCategoryId(subCategory.getCategoryId());
        subCategoryDto.setEntityUid(subCategory.getEntityUid());
        subCategoryDto.setName(subCategory.getName());
        subCategoryDto.setPrivate(subCategory.isPrivate());
        subCategoryDto.setGroupingId(subCategory.getGroupingId());
        return subCategoryDto;
    }

    public SubCategory toInventorySubCategory() {
        SubCategory inventorySubCategory = new SubCategory();
        inventorySubCategory.setId(getId());
        inventorySubCategory.setName(getName());
        inventorySubCategory.setCategoryId(getCategoryId());
        inventorySubCategory.setEntityUid(getEntityUid());
        inventorySubCategory.setGroupingId(getGroupingId());
        inventorySubCategory.setPrivate(isPrivate());
        return inventorySubCategory;
    }
}
