package com.buyern.inventory.Services;

import com.buyern.inventory.Helpers.ListMapper;
import com.buyern.inventory.Model.Inventory;
import com.buyern.inventory.Repositories.InventoryRepository;
import com.buyern.inventory.dtos.InventoryDto;
import com.buyern.inventory.dtos.ResponseDTO;
import com.buyern.inventory.exception.RecordNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {
    final InventoryRepository inventoryRepository;
    final PromoService promoService;

    public ResponseEntity<ResponseDTO> getEntityInventory(String entityUid, String inventoryUid) {
        Optional<Inventory> inventory = inventoryRepository.findByUidAndEntityId(inventoryUid, entityUid);
        if (inventory.isEmpty())
            throw new RecordNotFoundException("Inventory with id: " + inventoryUid + " belonging to entity: " + entityUid + " does not exist");
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(InventoryDto.create(inventory.get())).build());
    }

    public ResponseEntity<ResponseDTO> getEntityInventories(String entityUid) {
        List<Inventory> inventories = inventoryRepository.findAllByEntityId(entityUid);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(new ListMapper<Inventory, InventoryDto>().map(inventories, InventoryDto::create)).build());
    }

    public ResponseEntity<ResponseDTO> getInventory(Long inventoryId) {
        Inventory inventory = getInventory(inventoryId, null);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(InventoryDto.create(inventory)).build());
    }

    public Inventory getInventory(Long inventoryId, @Nullable Boolean isParent) {
        Optional<Inventory> inventory = inventoryRepository.findById(inventoryId);
        if (inventory.isEmpty())
            if (isParent != null && isParent)
                throw new RecordNotFoundException("Parent inventory not found");
            else
                throw new RecordNotFoundException("Inventory with id does not exist");
        return inventory.get();
    }

}
