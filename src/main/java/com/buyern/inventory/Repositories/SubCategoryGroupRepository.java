package com.buyern.inventory.Repositories;

import com.buyern.inventory.Model.SubCategoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryGroupRepository extends JpaRepository<SubCategoryGroup, Long> {
}