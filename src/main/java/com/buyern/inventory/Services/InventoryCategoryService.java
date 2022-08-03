package com.buyern.inventory.Services;

import com.buyern.inventory.Helpers.ListMapper;
import com.buyern.inventory.Model.Category;
import com.buyern.inventory.Repositories.InventoryCategoryRepository;
import com.buyern.inventory.dtos.CategoryDto;
import com.buyern.inventory.dtos.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InventoryCategoryService {
final InventoryCategoryRepository categoryRepository;
    public ResponseEntity<ResponseDTO> getCategories() {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(new ListMapper< Category, CategoryDto >().map(categoryRepository.findAll(), CategoryDto::create)).build());
    }
}
