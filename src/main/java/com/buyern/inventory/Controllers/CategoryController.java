package com.buyern.inventory.Controllers;

import com.buyern.inventory.Services.InventoryCategoryService;
import com.buyern.inventory.dtos.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("categories")
public class CategoryController {
    final InventoryCategoryService categoryService;

    @GetMapping
    private ResponseEntity<ResponseDTO> getCategories() {
        return categoryService.getCategories();
    }
}
