package com.buyern.inventory.Services;

import com.buyern.inventory.Controllers.AdminInventoryItemController;
import com.buyern.inventory.Enums.SimpleMediaType;
import com.buyern.inventory.Helpers.ListMapper;
import com.buyern.inventory.Model.Inventory;
import com.buyern.inventory.Model.InventoryFeature;
import com.buyern.inventory.Model.InventoryItem;
import com.buyern.inventory.Model.InventoryPublicListingItem;
import com.buyern.inventory.Objects.FeatureValue;
import com.buyern.inventory.Objects.InventoryItemFeature;
import com.buyern.inventory.Objects.Media;
import com.buyern.inventory.Repositories.InventoryFeatureRepository;
import com.buyern.inventory.Repositories.InventoryItemRepository;
import com.buyern.inventory.Repositories.InventoryRepository;
import com.buyern.inventory.Repositories.Listings.PublicListingRepository;
import com.buyern.inventory.dtos.InventoryItemDto;
import com.buyern.inventory.dtos.PageableResponseDTO;
import com.buyern.inventory.dtos.ResponseDTO;
import com.buyern.inventory.exception.BadRequestException;
import com.buyern.inventory.exception.EntityAlreadyExistsException;
import com.buyern.inventory.exception.LimitReachedException;
import com.buyern.inventory.exception.RecordNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.api.async.RedisServerAsyncCommands;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerErrorException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InventoryItemService {
    final InventoryRepository inventoryRepository;
    final InventoryItemRepository inventoryItemRepository;
    final InventoryFeatureRepository inventoryFeatureRepository;
    final PublicListingRepository publicListingRepository;
    final MediaService mediaService;
    //    @Value("${inventory.media.max_count}")
    final int mediaCountLimit = 5;
    final Logger logger = LoggerFactory.getLogger(AdminInventoryItemController.class);

    public ResponseEntity<ResponseDTO> addInventoryItemToInventory(String entityId, String inventoryId, InventoryItemDto inventoryItemDto, boolean hasMedia) {
        Inventory inventory = getInventory(entityId, inventoryId);
        inventoryItemDto.setEntityId(entityId);
        if (inventoryItemDto.getName() == null)
            inventoryItemDto.setName(inventory.getName());
        if (inventoryItemDto.getAbout() == null)
            inventoryItemDto.setAbout(inventory.getAbout());
        if (inventoryItemDto.getPrice() == 0)
            inventoryItemDto.setPrice(inventory.getPrice());
        if (inventoryItemDto.getImage() == null || Objects.equals(inventoryItemDto.getImage(), ""))
            inventoryItemDto.setImage(inventory.getImage());

        if (inventoryItemDto.getFeatures() == null || inventoryItemDto.getFeatures().isEmpty())
            throw new BadRequestException("All Items (variations) must have a feature selected");
        List<Long> newFeatureIds = inventoryItemDto.getFeatures().stream().map(InventoryItemFeature::getId).collect(Collectors.toList());
        if (inventory.getFeatures() == null || inventory.getFeatures().isEmpty())
            throw new BadRequestException("parent inventory has no features selected");
        List<InventoryFeature> serverFeatures = inventoryFeatureRepository.findAllById(newFeatureIds);
        if (serverFeatures == null || serverFeatures.isEmpty())
            throw new RecordNotFoundException("one or more feature(s) not found");
        for (InventoryFeature serverFeature : serverFeatures) {
            try {
                ArrayList<FeatureValue> serverFeatureValues = new ObjectMapper().readValue(serverFeature.get_values(), new TypeReference<ArrayList<FeatureValue>>() {
                });
                for (FeatureValue serverFeatureValue : serverFeatureValues) {
                    for (InventoryItemFeature userSelectedFeature : inventoryItemDto.getFeatures()) {
                        userSelectedFeature.setParentFeatureId(serverFeature.getId());
//                        userSelectedFeature.setName(serverFeature.getName());
                        if (Objects.equals(serverFeatureValue.getId(), userSelectedFeature.getValue().getId())) {
                            userSelectedFeature.getValue().setId(serverFeatureValue.getId());
                            userSelectedFeature.getValue().setName(serverFeatureValue.getName());
                            userSelectedFeature.getValue().setRawValue(serverFeatureValue.getRawValue());
                        }
                    }
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new RecordNotFoundException("one or more saved feature(s) on the server has its values in a bad [Json format]");
            }
        }


        InventoryItem finalInventoryItem = inventoryItemDto.toModel();
        finalInventoryItem.setCategory(inventory.getCategory());
        finalInventoryItem.setSubCategory(inventory.getSubCategory());
        if (hasMedia)
            if (finalInventoryItem.getMedia() == null || finalInventoryItem.getMedia().isEmpty())
                finalInventoryItem.setMedia(inventory.getMedia());

        finalInventoryItem.setParent(inventory);
        try {
            finalInventoryItem.setListings(new ObjectMapper().writeValueAsString(new ArrayList<String>()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        inventory.getInventoryItems().add(finalInventoryItem);

        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(InventoryItemDto.CREATE(inventoryItemRepository.save(finalInventoryItem))).build());
    }

    public ResponseEntity<ResponseDTO> getInventoryItem(String entityId, String id) {
        InventoryItem inventory = inventoryItemRepository.findByUidAndEntityId(id, entityId).orElseThrow(() -> new EntityNotFoundException("Inventory Item not found or doesnt belong to this entity"));
        System.out.println(inventory);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(InventoryItemDto.CREATE(inventory)).build());
    }

    public ResponseEntity<ResponseDTO> getInventoryItems(String entityId, int page, int perPage) {
        Page<InventoryItem> inventories = inventoryItemRepository.findByEntityIdOrderByParent_IdDesc(entityId, PageRequest.of(page, perPage));

        inventories.getContent().get(0).setParent(new Inventory(inventories.getContent().get(0).getParent().getId()));
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(PageableResponseDTO.builder()
                        .totalRowCount(inventories.getTotalElements())
                        .totalPageCount(inventories.getTotalPages())
                        .data(new ListMapper<InventoryItem, InventoryItemDto>().map(inventories.getContent(), InventoryItemDto::CREATE))
                        .build())
                .build());
    }

    private Inventory getInventory(String entityId, String inventoryId) {
        return inventoryRepository.findByUidAndEntityId(inventoryId, entityId).orElseThrow(() -> new EntityNotFoundException("Inventory not found or doesnt belong to this entity"));
    }

    @Transactional
    public ResponseEntity<ResponseDTO> UploadItemImage(String entityId, String itemId, MultipartFile imageFile) {
        if (imageFile == null) throw new BadRequestException("No Image Uploaded");
        if (MediaService.verifyMediaType(imageFile.getContentType()) != SimpleMediaType.IMAGE)
            throw new BadRequestException("File is not an Image");

        InventoryItem inventoryItem = inventoryItemRepository.findByUidAndEntityId(itemId, entityId).orElseThrow(() -> new EntityNotFoundException("Item not found"));
        try {
            System.out.println(new ObjectMapper().writeValueAsString(inventoryItem.getParent()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        inventoryItem.setImage(mediaService.uploadInventoryItemMedia(entityId, imageFile, inventoryItem.getParent().getUid(), inventoryItem.getUid(), "main"));
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(inventoryItem).build());
    }

    public InventoryItem getInventoryItemByUid(String inventoryUid, String entityUid) {
        Optional<InventoryItem> inventoryItem = inventoryItemRepository.findByUidAndEntityId(inventoryUid, entityUid);
        if (inventoryItem.isEmpty())
            throw new RecordNotFoundException("Inventory Item with id does not exist or is not owned by this entity");
        return inventoryItem.get();
    }

    public ResponseEntity<ResponseDTO> uploadMedia(String entityId, String itemId, MultipartFile mediaFile, String name, String tag, SimpleMediaType type) {
        if (mediaFile == null)
            throw new RuntimeException("No Media file Uploaded");
        if (!MediaService.isImage(mediaFile.getContentType()) && !MediaService.isVideo(mediaFile.getContentType()))
            throw new RuntimeException("File is unsupported");
        InventoryItem savedInventoryItem = inventoryItemRepository.findByUidAndEntityId(itemId, entityId).orElseThrow(() -> new EntityNotFoundException("item not found"));
        ArrayList<Media> savedInventoryItemMediaList = new ArrayList<Media>();
        if (savedInventoryItem.getMedia() != null)
            try {
                savedInventoryItemMediaList = new ObjectMapper().readValue(savedInventoryItem.getMedia(), new TypeReference<ArrayList<Media>>() {
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (savedInventoryItemMediaList.size() >= mediaCountLimit)
            throw new LimitReachedException("Limit reached. Only maximum of %d media is allowed per inventory Item".formatted(mediaCountLimit));
        Media media = Media.builder()
                .id(UUID.randomUUID().toString())
                .name(name)
                .tag(tag)
                .type(type)
                .build();
        media.setLink(mediaService.uploadInventoryItemMedia(entityId, mediaFile, savedInventoryItem.getParent().getUid(), savedInventoryItem.getUid(), media.getId()));
        if (media.getLink() == null || Objects.equals(media.getLink(), ""))
            throw new RuntimeException("ERROR saving media");
        try {
            savedInventoryItemMediaList.add(media);
            savedInventoryItem.setMedia(new ObjectMapper().writeValueAsString(savedInventoryItemMediaList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("success").data(InventoryItemDto.CREATE(inventoryItemRepository.save(savedInventoryItem))).build());
    }

    public InventoryItem getItemByUid(String itemUid, String entityId) {
        return inventoryItemRepository.findByUidAndEntityId(itemUid, entityId).orElseThrow(() -> new EntityNotFoundException("Inventory Item not found or doesnt belong to this entity"));
    }

    public List<InventoryItem> getItemsByUidAndInventoryId(String inventoryUid, String entityId) {
        List<InventoryItem> inventoryItems = inventoryItemRepository.findByParent_UidAndEntityIdOrderByTimeAddedDesc(inventoryUid, entityId);
        if (inventoryItems == null || inventoryItems.isEmpty())
            throw new EntityNotFoundException("Inventory Item not found or doesnt belong to this entity");
        return inventoryItems;
    }

    public ResponseEntity<ResponseDTO> addInventoryToPublicListing(String itemId, String entityId, InventoryItem inventoryItem) {
        InventoryPublicListingItem savedItem = publicListingRepository.save(InventoryItemDto.CREATE(inventoryItem).toListingItem());
        System.out.println(savedItem);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(publicListingRepository.save(InventoryItemDto.CREATE(inventoryItemRepository.save(inventoryItem)).toListingItem())).build());
    }

    public ResponseEntity<ResponseDTO> addItemToListing(String entityId, String itemId, String listingUName) {
        InventoryItem inventoryItem = getItemByUid(itemId, entityId);
        ArrayList<String> listings = new ArrayList<>();
        if (inventoryItem.getListings() != null) {

            try {
                listings = new ObjectMapper().readValue(inventoryItem.getListings(), new TypeReference<ArrayList<String>>() {
                });
                //            check if already in listing
                if (listings.size() > 0)
                    for (String listing : listings) {
                        if (!Objects.equals(listing, listingUName)) {
                            throw new EntityAlreadyExistsException("Item already added to listing");
                        }
                    }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            listings.add(listingUName);
        }
        try {
            inventoryItem.setListings(new ObjectMapper().writeValueAsString(listings));
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
            throw new RuntimeException("Server Error: error writing listings to item");
        }
        switch (listingUName) {
            case "PUBLIC":
                return addInventoryToPublicListing(itemId, entityId, inventoryItem);
            default:
                throw new BadRequestException("no Listing specified or Listing In bad format");
        }
    }
}