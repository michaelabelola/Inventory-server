package com.buyern.inventory.Repositories;

import com.buyern.inventory.Model.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, String> {
    Optional<InventoryItem> findByUidAndEntityId(String uid, String entityId);

    List<InventoryItem> findByParent_UidAndEntityIdOrderByTimeAddedDesc(String uid, String entityId);

    Optional<InventoryItem> findByUid(String uid);

    @Query("select i from InventoryItem i where i.uid = ?1 and i.entityId = ?2 and i.parent.uid = ?3")
    @NonNull
    Optional<InventoryItem> findByUidAndEntityIdAndParent_Uid(String uid, String entityId, String uid1);

    Page<InventoryItem> findByEntityIdOrderByParent_IdDesc(String entityId, Pageable pageable);


    List<InventoryItem> findAllByEntityId(String entityId);

}