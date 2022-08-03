package com.buyern.inventory.Repositories;

import com.buyern.inventory.Model.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findAllByEntityId(String entityId);

    @Query("select i from Inventory i where i.entityId = ?1")
    Page<Inventory> findByEntityId(String entityUid, Pageable pageable);
    Optional<Inventory> findByIdAndEntityId(Long id, String entityId);
    Optional<Inventory> findByUidAndEntityId(String uid, String entityId);
}