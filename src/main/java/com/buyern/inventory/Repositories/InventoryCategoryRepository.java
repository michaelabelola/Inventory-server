package com.buyern.inventory.Repositories;

import com.buyern.inventory.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryCategoryRepository extends JpaRepository<Category, Long> {
}