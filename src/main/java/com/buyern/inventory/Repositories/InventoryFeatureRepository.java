package com.buyern.inventory.Repositories;

import com.buyern.inventory.Model.InventoryFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryFeatureRepository extends JpaRepository<InventoryFeature, Long> {
    @Override
    List<InventoryFeature> findAllById(Iterable<Long> longs);

    @Query("select i from InventoryFeature i where i.entityId = ?1 OR i.entityId IS null")
    List<InventoryFeature> findAllFeaturesByEntityId(@Nullable String entityId);

    List<InventoryFeature> findAllByEntityId(String entityId);
    Optional<InventoryFeature> findByEntityIdAndId(String entityId, Long id);
}