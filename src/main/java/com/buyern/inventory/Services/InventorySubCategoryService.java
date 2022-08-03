package com.buyern.inventory.Services;

import com.buyern.inventory.Model.SubCategory;
import com.buyern.inventory.Repositories.InventoryCategoryRepository;
import com.buyern.inventory.Repositories.InventorySubCategoryRepository;
import com.buyern.inventory.dtos.SubCategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.buyern.inventory.Helpers.ListMapper;
import com.buyern.inventory.Model.Category;
import com.buyern.inventory.Repositories.InventoryCategoryRepository;
import com.buyern.inventory.dtos.CategoryDto;
import com.buyern.inventory.dtos.ResponseDTO;
import org.springframework.http.ResponseEntity;

@Service
@AllArgsConstructor
public class InventorySubCategoryService {
    final InventorySubCategoryRepository subCategoryRepository;
    public ResponseEntity<ResponseDTO> getSubCategoriesByCategoryId(long categoryId) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(new ListMapper<SubCategory, SubCategoryDto>().map(subCategoryRepository.findAllByCategoryId(categoryId), SubCategoryDto::create)).build());
    }
}
