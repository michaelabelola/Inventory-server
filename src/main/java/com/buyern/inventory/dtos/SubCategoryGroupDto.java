package com.buyern.inventory.dtos;

import com.buyern.inventory.Model.SubCategoryGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SubCategoryGroupDto implements Serializable {
    private Long id;
    private String name;
    private CategoryDto category;
    private List<SubCategoryDto> subCategories;

    public static SubCategoryGroupDto inventorySubCategoryGroupDto(SubCategoryGroup inventorySubCategoryGroup) {
        SubCategoryGroupDto inventorySubCategoryGroupDto = new SubCategoryGroupDto();
        inventorySubCategoryGroupDto.setId(inventorySubCategoryGroup.getId());
        inventorySubCategoryGroupDto.setName(inventorySubCategoryGroup.getName());
        inventorySubCategoryGroupDto.setCategory(CategoryDto.create(inventorySubCategoryGroup.getCategory()));
        return inventorySubCategoryGroupDto;
    }

    public SubCategoryGroup inventorySubCategoryGroup() {
        SubCategoryGroup inventorySubCategoryGroup = new SubCategoryGroup();
        inventorySubCategoryGroup.setId(getId());
        inventorySubCategoryGroup.setName(getName());
        inventorySubCategoryGroup.setCategory(getCategory().toModel());
        return inventorySubCategoryGroup;
    }
}
