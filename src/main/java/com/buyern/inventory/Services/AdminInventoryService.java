package com.buyern.inventory.Services;

import com.buyern.inventory.Enums.SimpleMediaType;
import com.buyern.inventory.Helpers.ListMapper;
import com.buyern.inventory.dtos.InventoryFeatureDto;
import com.buyern.inventory.Model.Inventory;
import com.buyern.inventory.Model.InventoryFeature;
import com.buyern.inventory.Objects.Media;
import com.buyern.inventory.Repositories.InventoryRepository;
import com.buyern.inventory.dtos.InventoryDto;
import com.buyern.inventory.dtos.ResponseDTO;
import com.buyern.inventory.exception.EntityAlreadyExistsException;
import com.buyern.inventory.exception.LimitReachedException;
import com.buyern.inventory.exception.RecordNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminInventoryService {

    final InventoryRepository inventoryRepository;
    final PromoService promoService;
    final MediaService mediaService;
    final FeatureService featureService;

    /**
     * <h3>The maximum amount of media an inventory is allowed to have</h3>
     */
    final Integer mediaCountLimit = 5;

    public ResponseEntity<ResponseDTO> getInventoryMain(String inventoryId, String entityId) {
//        Inventory inventory = getInventoryByUid(inventoryId, entityId);
//        if (inventory.getParent() == null)
//            return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(InventoryDto.create(inventory)).build());
//
//        List<Inventory> variations = getInventoryVariations(inventory.getId());
//        ObjectNode mainInventory = new ObjectMapper().valueToTree(inventory.getParent());
//        mainInventory.putPOJO("variations", variations);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(InventoryDto.create(inventoryRepository.findByUidAndEntityId(inventoryId, entityId).orElseThrow(()-> new EntityNotFoundException("Inventory with id does not exist or is not owned by this entity")))).build());
    }

    public ResponseEntity<ResponseDTO> getInventories(String entityId) {
        List<Inventory> inventories = inventoryRepository.findAllByEntityId(entityId);
        if (inventories.isEmpty())
            throw new RecordNotFoundException("no inventory");
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(new ListMapper<Inventory, InventoryDto>().map(inventories, InventoryDto::create)).build());
    }

    public ResponseEntity<ResponseDTO> getInventories(String entityId, int page, int pageSize) {
        Page<Inventory> inventoriesPage = inventoryRepository.findByEntityId(entityId, PageRequest.of(page, pageSize));
        ObjectNode jsonNode = new ObjectMapper().createObjectNode();
        jsonNode.put("totalRowCount", inventoriesPage.getTotalElements());
        jsonNode.put("totalPageCount", inventoriesPage.getTotalPages());
        try {
            jsonNode.putPOJO("data", new ListMapper<Inventory, InventoryDto>().map(inventoriesPage.get().toList(), InventoryDto::create));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        "new ListMapper<Inventory, InventoryDto>().map(inventories, InventoryDto::create)"
//        if (inventories.isEmpty())
//            throw new RecordNotFoundException("no inventory");
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(jsonNode).build());
    }

    public ResponseEntity<ResponseDTO> getInventoriesMain(List<Long> inventoryIds, Long entityId) {
        List<Inventory> inventories = inventoryRepository.findAllById(inventoryIds);
        if (inventories.isEmpty())
            throw new RecordNotFoundException("no inventory with specified ids exist");

        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(new ListMapper<Inventory, InventoryDto>().map(inventories, InventoryDto::create)).build());
    }

    public ResponseEntity<ResponseDTO> createInventory(InventoryDto inventoryDto, String entityUid) {
        inventoryDto.setEntityId(entityUid);
        inventoryDto.setUid(UUID.randomUUID().toString());
        if (inventoryDto.getFeatures() != null && !inventoryDto.getFeatures().isEmpty()) {
            List<InventoryFeature> features;
            //get features
            try {
                features = featureService.getFeaturesByFeatureIds(inventoryDto.getFeatures().stream().map(InventoryFeatureDto::getId).collect(Collectors.toList()));
                inventoryDto.setFeatures(new ListMapper<InventoryFeature, InventoryFeatureDto>().map(features, InventoryFeatureDto::create));
            } catch (Exception ex) {
                throw new EntityNotFoundException("Feature id Error. One ore more of the selected features not found");
            }
        }
        Inventory inventory = inventoryDto.toInventory();
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(InventoryDto.create(inventoryRepository.save(inventory))).build());
    }

    public ResponseEntity<ResponseDTO> createInventories(List<InventoryDto> inventoriesDto, Long entityId) {
        List<Inventory> inventories = new ListMapper<InventoryDto, Inventory>().map(inventoriesDto, InventoryDto::toInventory);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(inventoryRepository.saveAll(inventories)).build());
    }

    public ResponseEntity<ResponseDTO> deleteInventory(Long id, Long inventoryId) {
        try {
            inventoryRepository.deleteById(inventoryId);
            return ResponseEntity.ok(ResponseDTO.builder().code("00").message("Deleted Successfully").build());
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting inventory");
        }
    }

    //    TODO: don't just delete inventories
//    add to inventory archive instead for a while
    public ResponseEntity<ResponseDTO> deleteInventories(List<Long> inventoryIds, Long entityId) {
        try {
            inventoryRepository.deleteAllById(inventoryIds);
            return ResponseEntity.ok(ResponseDTO.builder().code("00").message("Deleted Successfully").build());
        } catch (Exception ex) {
            throw new RuntimeException("Error deleting inventory");
        }
    }

    public Inventory getInventoryByUid(Long inventoryId, @Nullable Boolean isParent) {
        Optional<Inventory> inventory = inventoryRepository.findById(inventoryId);
        if (inventory.isEmpty())
            if (isParent != null && isParent)
                throw new RecordNotFoundException("Parent inventory not found");
            else
                throw new RecordNotFoundException("Inventory with id does not exist");
        return inventory.get();
    }

    public Inventory getInventoryByUid(String inventoryUid, String entityUid) {
        Optional<Inventory> inventory = inventoryRepository.findByUidAndEntityId(inventoryUid, entityUid);
        if (inventory.isEmpty())
                throw new RecordNotFoundException("Inventory with id does not exist or is not owned by this entity");
        return inventory.get();
    }

//    public List<Inventory> getInventoryVariations(Long inventoryId) {
//        Inventory inventory = new Inventory();
//        inventory.setId(inventoryId);
//        return inventoryRepository.findAllByParent(inventory);
//    }

    public ResponseEntity<ResponseDTO> createInventoryUploadImage(String entityId, String inventoryId, MultipartFile imageFile) {
        Inventory inventory = getInventoryByUid(inventoryId, entityId);
        if (imageFile == null)
            throw new RuntimeException("No Image Uploaded");
        if (MediaService.verifyMediaType(imageFile.getContentType()) != SimpleMediaType.IMAGE)
            throw new RuntimeException("File is not an Image");
        String uploadedImageLink = mediaService.uploadInventoryMedia(entityId, imageFile, inventory.getUid(), "main");
        inventory.setImage(uploadedImageLink);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(InventoryDto.create(inventoryRepository.save(inventory))).build());
    }

    /*TODO:
    *  for all file upload, uuid cannot be a container name, azure storage will throw an exception. use entity id not entityuid instead*/
    public ResponseEntity<ResponseDTO> createInventoryUploadMedia(String entityId, String inventoryId, MultipartFile mediaFile, Media media) throws JsonProcessingException {
        if (mediaFile == null)
            throw new RuntimeException("No Media file Uploaded");
        if (!MediaService.isImage(mediaFile.getContentType()) && !MediaService.isVideo(mediaFile.getContentType()))
            throw new RuntimeException("File is unsupported");
        if (MediaService.isImage(mediaFile.getContentType()))
            media.setType(SimpleMediaType.IMAGE);
        if (MediaService.isVideo(mediaFile.getContentType()))
            media.setType(SimpleMediaType.VIDEO);
        Inventory inventory = getInventoryByUid(inventoryId, entityId);
        List<Media> savedMedia;
        if (inventory.getMedia() == null || inventory.getMedia().isEmpty())
            savedMedia = new ArrayList<>();
        else
            try {
                savedMedia = new ObjectMapper().readValue(inventory.getMedia(), List.class);
            } catch (JsonProcessingException ex) {
                savedMedia = new ArrayList<>();
            }
        if (savedMedia.size() >= mediaCountLimit)
            throw new LimitReachedException("Limit reached. Only maximum of %d media is allowed per inventory".formatted(mediaCountLimit));
        media.setId(UUID.randomUUID().toString());
        media.setLink(mediaService.uploadInventoryMedia(entityId, mediaFile, inventory.getUid(), media.getId()));
        if (media.getLink() == null || Objects.equals(media.getLink(), ""))
            throw new RuntimeException("ERROR saving media");
        savedMedia.add(media);
        inventory.setMedia(new ObjectMapper().writeValueAsString(savedMedia));

        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(InventoryDto.create(inventoryRepository.save(inventory))).build());
    }

    /**
     * <h3>Add Feature To Inventory</h3>
     *
     * @param parentInventory inventory to receive the new newFeature
     * @param newFeature      newFeature to be added to inventory
     * @param forceAdd        delete newFeature with same name from inventory and add the supplied to inventory
     */
    private void addFeatureToInventory(Inventory parentInventory, InventoryFeatureDto newFeature, boolean forceAdd) {
        newFeature.setId(Math.round(Math.random()));
        List<InventoryFeatureDto> calculatedFeatures = new ArrayList<>();
        try {
            if (parentInventory.getFeatures() != null && !Objects.equals(parentInventory.getFeatures(), "")) {
                JsonNode savedFeatures = new ObjectMapper().readTree(parentInventory.getFeatures());
                for (JsonNode jsonNode : savedFeatures) {
                    InventoryFeatureDto savedFeature = new ObjectMapper().convertValue(jsonNode, InventoryFeatureDto.class);
                    if (Objects.equals(newFeature.getName(), savedFeature.getName()))
                        if (forceAdd)
                            calculatedFeatures.add(newFeature);
                        else
                            throw new EntityAlreadyExistsException("Features of the same product can't have same name");
                    else
                        calculatedFeatures.add(savedFeature);
                }
                calculatedFeatures.add(newFeature);
            } else {
                calculatedFeatures.add(newFeature);
            }
            parentInventory.setFeatures(new ObjectMapper().writeValueAsString(calculatedFeatures));
        } catch (
                JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public ResponseEntity<ResponseDTO> addFeatureToInventory(String inventoryUid, String entityUid, InventoryFeatureDto feature) {
        Inventory inventory = getInventoryByUid(inventoryUid, entityUid);
        //TODO: check if it's parent
//        if (inventory.getParent() == null) {
//            //is parent
//            addFeatureToInventory(inventory, feature, false);
//        } else {
////            addFeatureToInventory(inventory.getParent(), feature, false);
//            addFeatureToInventory(inventory, feature, false);
//            //TODO: set Parent feature, set child feature
//            //TODO: then set child feature
//        }
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(InventoryDto.create(inventoryRepository.save(inventory))).build());
    }

    public ResponseEntity<ResponseDTO> getInventoryFeatures(String inventoryUid, String entityUid, InventoryFeatureDto featureDto) {
        Inventory inventory = getInventoryByUid(inventoryUid, entityUid);
        if (inventory.getFeatures() == null)
            throw new EntityNotFoundException("No features specified for this entity");
        try {
            return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(new ObjectMapper().readValue(inventory.getFeatures(), List.class)).build());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("bad inventory list format");
        }
    }


}
