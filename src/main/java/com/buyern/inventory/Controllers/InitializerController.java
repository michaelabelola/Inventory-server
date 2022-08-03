package com.buyern.inventory.Controllers;

import com.buyern.inventory.Model.Category;
import com.buyern.inventory.Model.SubCategory;
import com.buyern.inventory.Model.SubCategoryGroup;
import com.buyern.inventory.Repositories.InventoryCategoryRepository;
import com.buyern.inventory.Repositories.InventorySubCategoryRepository;
import com.buyern.inventory.Repositories.SubCategoryGroupRepository;
import com.buyern.inventory.dtos.SubCategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("")
public class InitializerController {
    final InventoryCategoryRepository inventoryCategoryRepository;
    final InventorySubCategoryRepository inventorySubCategoryRepository;
    final SubCategoryGroupRepository inventorySubCategoryGroupRepository;

    @GetMapping("init1")
    private String init() {
        createInventoryCategories();
        return "Success";
    }

    @GetMapping("init2")
    private String init2() {
        createInventorySubCategoryGroups();
        return "Success";
    }

    @GetMapping("init3")
    private String init3() {
        createInventorySubCategories();
        return "Success";
    }

    private void createInventorySubCategoryGroups() {
        List<SubCategoryGroup> subCategoryGroups = new ArrayList<>();
        subCategoryGroups.add(new SubCategoryGroup(1L, "Footwear", null));
        inventorySubCategoryGroupRepository.saveAll(subCategoryGroups);
    }

    private void createInventoryCategories() {
        List<Category> inventoryCategories = new ArrayList<>();
        inventoryCategories.add(new Category(1L, "Apparels", null, false));
        inventoryCategories.add(new Category(2L, "Electronics", null, false));
        inventoryCategories.add(new Category(3L, "furniture", null, false));
        inventoryCategories.add(new Category(4L, "Cars", null, false));
        inventoryCategories.add(new Category(5L, "Houses", null, false));
        inventoryCategories.add(new Category(6L, "Heavy Machines", null, false));
        inventoryCategories.add(new Category(7L, "Factory Equipments", null, false));
        inventoryCategories.add(new Category(8L, "Building Materials", null, false));
        inventoryCategoryRepository.saveAll(inventoryCategories);
    }

    private void createInventorySubCategories() {
        List<SubCategory> inventorySubCategories = new ArrayList<>();
        inventorySubCategories.add(SubCategoryDto.builder().id(1L).name("Coats").categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(SubCategoryDto.builder().id(2L).name("Shirts").categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(SubCategoryDto.builder().id(3L).name("Belts").categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(SubCategoryDto.builder().id(4L).name("boots").groupingId(53L).categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(SubCategoryDto.builder().id(5L).name("Sandals").groupingId(53L).categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(SubCategoryDto.builder().id(6L).name("Trainers").groupingId(53L).categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(SubCategoryDto.builder().id(7L).name("Slides").groupingId(53L).categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        inventorySubCategories.add(SubCategoryDto.builder().id(8L).name("Rings").categoryId(25L).isPrivate(false).build().toInventorySubCategory());
        List<SubCategory> SubCategoryList = inventorySubCategoryRepository.saveAll(inventorySubCategories);
    }

}
