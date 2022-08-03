package com.buyern.inventory.Services;

import com.buyern.inventory.Helpers.ListMapper;
import com.buyern.inventory.dtos.InventoryFeatureDto;
import com.buyern.inventory.Model.InventoryFeature;
import com.buyern.inventory.Repositories.InventoryFeatureRepository;
import com.buyern.inventory.dtos.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FeatureService {
    final InventoryFeatureRepository inventoryFeatureRepository;

    public ResponseEntity<ResponseDTO> createFeature(InventoryFeature inventoryFeature) {
        InventoryFeature feature = inventoryFeatureRepository.save(inventoryFeature);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(InventoryFeatureDto.create(feature)).build());
    }

    public ResponseEntity<ResponseDTO> getEntityInventoryFeatures(String entityId) {
        List<InventoryFeature> features = inventoryFeatureRepository.findAllFeaturesByEntityId(entityId);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(new ListMapper<InventoryFeature, InventoryFeatureDto>().map(features, InventoryFeatureDto::create)).build());
    }
    public List<InventoryFeature> getFeaturesByFeatureIds(List<Long> featureIds) {
        return inventoryFeatureRepository.findAllById(featureIds);
    }

    public ResponseEntity<ResponseDTO> getInventoryFeatureById(String entityId, Long id) {
        Optional<InventoryFeature> feature = inventoryFeatureRepository.findByEntityIdAndId(entityId, id);
        if (feature.isEmpty())
            throw new EntityNotFoundException("feature does not exist");
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(InventoryFeatureDto.create(feature.get())).build());
    }
}
