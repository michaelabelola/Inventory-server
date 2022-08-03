package com.buyern.inventory.Controllers;

import com.buyern.inventory.dtos.InventoryFeatureDto;
import com.buyern.inventory.Objects.FeatureValue;
import com.buyern.inventory.Services.FeatureService;
import com.buyern.inventory.dtos.ResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("admin/{entityId}/feature")
public class AdminFeatureController {
    final FeatureService featureService;

    @PostMapping()
    private ResponseEntity<ResponseDTO> createFeature(@PathVariable String entityId, @RequestBody InventoryFeatureDto inventoryFeatureDto) {
        inventoryFeatureDto.setEntityId(entityId);
        inventoryFeatureDto.setValues((ArrayList<FeatureValue>) inventoryFeatureDto.getValues().stream().peek(featureValue -> featureValue.setId(UUID.randomUUID().toString())).collect(Collectors.toList()));
        return featureService.createFeature(inventoryFeatureDto.toModel());
    }
    @GetMapping("/all")
    private ResponseEntity<ResponseDTO> createFeature(@PathVariable String entityId) {
        return featureService.getEntityInventoryFeatures(entityId);
    }
    @GetMapping("")
    private ResponseEntity<ResponseDTO> createFeature(@PathVariable String entityId, @RequestParam Long id) {
        return featureService.getInventoryFeatureById(entityId, id);
    }

}
