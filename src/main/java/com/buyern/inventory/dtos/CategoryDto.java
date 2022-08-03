package com.buyern.inventory.dtos;

import com.buyern.inventory.Model.Category;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class CategoryDto implements Serializable {
    private Long id;
    private String name;
    private String entityUid;
    private boolean isPrivate;

    public Category toModel() {
        Category category = new Category();
        category.setId(getId());
        category.setName(getName());
        category.setEntityUid(getEntityUid());
        category.setPrivate(isPrivate());
        return category;
    }

    public static CategoryDto create(Category category) {
        return new CategoryDtoBuilder()
                .id(category.getId())
                .name(category.getName())
                .entityUid(category.getEntityUid())
                .isPrivate(category.isPrivate())
                .build();
    }
}
