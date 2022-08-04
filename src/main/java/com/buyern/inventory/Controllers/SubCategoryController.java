package com.buyern.inventory.Controllers;

import com.buyern.inventory.Model.UserAuth;
import com.buyern.inventory.Objects.EntityPermission;
import com.buyern.inventory.Objects.Permission;
import com.buyern.inventory.Objects.ToolPermission;
import com.buyern.inventory.Services.InventoryCategoryService;
import com.buyern.inventory.Services.InventorySubCategoryService;
import com.buyern.inventory.dtos.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("subCategories")
public class SubCategoryController {
    final InventorySubCategoryService subCategoryService;

    @GetMapping("byCategoryId")
    private ResponseEntity<ResponseDTO> getCategories(@RequestParam long categoryId) {
        return subCategoryService.getSubCategoriesByCategoryId(categoryId);
    }
}
