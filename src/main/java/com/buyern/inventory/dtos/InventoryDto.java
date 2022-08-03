package com.buyern.inventory.dtos;

import com.buyern.inventory.Helpers.ListMapper;
import com.buyern.inventory.Model.*;
import com.buyern.inventory.Objects.Media;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.*;

@Data
public class InventoryDto {
    private Long id;
    private String uid;
    private String name;
    private String entityId;
    private String about;
    private Date timeAdded;
    private Long addedBy;
    private int qty;
    private double price;
    private List<PromoDto> promos;
    private String image;
    private List<Media> media;
    private CategoryDto category;
    private SubCategoryDto subCategory;
    private List<InventoryFeatureDto> features;
    private List<InventoryItemDto> inventoryItems = new ArrayList<>();

    public static InventoryDto create(Inventory inventory) {
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setId(inventory.getId());
        inventoryDto.setUid(inventory.getUid());
        inventoryDto.setName(inventory.getName());
        inventoryDto.setEntityId(inventory.getEntityId());
        inventoryDto.setAbout(inventory.getAbout());
        inventoryDto.setTimeAdded(inventory.getTimeAdded());
        inventoryDto.setAddedBy(inventory.getAddedBy());
        inventoryDto.setQty(inventory.getQty());
        inventoryDto.setPrice(inventory.getPrice());
        inventoryDto.setPromos(new ListMapper<Promo, PromoDto>().map(inventory.getPromos(), PromoDto::create));
        inventoryDto.setImage(inventory.getImage());
        if (inventory.getMedia() == null)
            inventoryDto.setMedia(new ArrayList<>());
        else
            try {
                inventoryDto.setMedia(new ObjectMapper().readValue(inventory.getMedia(), List.class));
            } catch (JsonProcessingException ex) {
                inventoryDto.setMedia(null);
            }
        inventoryDto.setCategory(CategoryDto.create(inventory.getCategory()));
        inventoryDto.setSubCategory(SubCategoryDto.create(inventory.getSubCategory()));
        if (inventory.getFeatures() == null || Objects.equals(inventory.getFeatures(), ""))
            inventoryDto.setFeatures(new ArrayList<>());
        else
            try {
                List<InventoryFeature> featureZ = new ObjectMapper().readValue(inventory.getFeatures(), new TypeReference<List<InventoryFeature>>() {});
                inventoryDto.setFeatures(new ListMapper<InventoryFeature, InventoryFeatureDto>().map(featureZ, InventoryFeatureDto::create));

            } catch (JsonProcessingException ex) {
                inventoryDto.setFeatures(new ArrayList<>());
            }
            inventoryDto.setInventoryItems(new ListMapper<InventoryItem, InventoryItemDto>().map(inventory.getInventoryItems(), InventoryItemDto::CREATE));
        return inventoryDto;
    }

    public Inventory toInventory() {
        Inventory inventory = new Inventory();
        inventory.setId(getId());
        inventory.setUid(getUid());
        inventory.setEntityId(getEntityId());
        inventory.setName(getName());
        inventory.setAbout(getAbout());
        inventory.setTimeAdded(getTimeAdded());
        inventory.setAddedBy(getAddedBy());
        inventory.setQty(getQty());
        inventory.setPrice(getPrice());
        inventory.setPromos(new ListMapper<PromoDto, Promo>().map(getPromos(), PromoDto::toPromo));
        inventory.setImage(getImage());
        if (getMedia() == null || getMedia().isEmpty())
            inventory.setMedia(null);
        else
            try {
                inventory.setMedia(new ObjectMapper().writeValueAsString(getMedia()));
            } catch (JsonProcessingException ex) {
                inventory.setMedia(null);
            }
        inventory.setCategory(getCategory().toModel());
        inventory.setSubCategory(getSubCategory().toInventorySubCategory());
        if (getFeatures() == null || getFeatures().isEmpty())
            inventory.setFeatures(null);
        else
            try {
                inventory.setFeatures(new ObjectMapper().writeValueAsString(new ListMapper<InventoryFeatureDto, InventoryFeature>().map(getFeatures(), InventoryFeatureDto::toModel)));
            } catch (JsonProcessingException ex) {
                inventory.setFeatures(null);
            }
        inventory.setInventoryItems(new ListMapper<InventoryItemDto, InventoryItem>().map(getInventoryItems(), InventoryItemDto::toModel));
        return inventory;
    }
}
