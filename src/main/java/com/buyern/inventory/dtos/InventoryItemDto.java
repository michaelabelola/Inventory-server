package com.buyern.inventory.dtos;

import com.buyern.inventory.Helpers.ListMapper;
import com.buyern.inventory.Model.*;
import com.buyern.inventory.Objects.InventoryItemFeature;
import com.buyern.inventory.Objects.Media;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryItemDto implements Serializable {
    private String uid = UUID.randomUUID().toString();
    private String name;
    private String entityId;
    private String about;
    private Date timeAdded;
    private Long addedBy;
    private int qty;
    private int unit;
    private double price;
    private List<PromoDto> promos;
    private String image;
    private List<Media> media;
    private CategoryDto category;
    private SubCategoryDto subCategory;
    private List<InventoryItemFeature> features;
    private List<String> listings;

    public static InventoryItemDto CREATE(InventoryItem inventory) {
        InventoryItemDto inventoryItemDto = new InventoryItemDto();
        inventoryItemDto.setUid(inventory.getUid());
        inventoryItemDto.setEntityId(inventoryItemDto.getEntityId());
        inventoryItemDto.setName(inventory.getName());
        inventoryItemDto.setAbout(inventory.getAbout());
        inventoryItemDto.setTimeAdded(inventory.getTimeAdded());
        inventoryItemDto.setAddedBy(inventory.getAddedBy());
        inventoryItemDto.setQty(inventory.getQty());
        inventoryItemDto.setPrice(inventory.getPrice());
        inventoryItemDto.setPromos(new ListMapper<Promo, PromoDto>().map(inventory.getPromos(), PromoDto::create));
        inventoryItemDto.setImage(inventory.getImage());
        if (inventory.getMedia() == null)
            inventoryItemDto.setMedia(new ArrayList<>());
        else
            try {
                inventoryItemDto.setMedia(new ObjectMapper().readValue(inventory.getMedia(), List.class));
            } catch (JsonProcessingException ex) {
                inventoryItemDto.setMedia(null);
            }
        inventoryItemDto.setCategory(CategoryDto.create(inventory.getCategory()));
        inventoryItemDto.setSubCategory(SubCategoryDto.create(inventory.getSubCategory()));

        if (inventory.getFeatures() == null || Objects.equals(inventory.getFeatures(), ""))
            inventoryItemDto.setFeatures(new ArrayList<>());
        else
            try {
                inventoryItemDto.setFeatures(
                        new ObjectMapper().readValue(inventory.getFeatures(), new TypeReference<List<InventoryItemFeature>>() {
                        })
                );

            } catch (JsonProcessingException ex) {
                inventoryItemDto.setFeatures(new ArrayList<>());
            }
        try {
            inventoryItemDto.setListings(new ObjectMapper().readValue(inventory.getListings(), new TypeReference<List<String>>() {}));
        } catch (JsonProcessingException e) {
            inventoryItemDto.setListings(new ArrayList<>());
            e.printStackTrace();
        }
//            inventoryItemDto.setParent(InventoryDto.create(inventory.getParent()));
        return inventoryItemDto;
    }

    public InventoryItem toModel() {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setUid(getUid());
        inventoryItem.setEntityId(getEntityId());
        inventoryItem.setName(getName());
        inventoryItem.setAbout(getAbout());
        inventoryItem.setTimeAdded(getTimeAdded());
        inventoryItem.setAddedBy(getAddedBy());
        inventoryItem.setQty(getQty());
        inventoryItem.setPrice(getPrice());
        inventoryItem.setPromos(new ListMapper<PromoDto, Promo>().map(getPromos(), PromoDto::toPromo));
        inventoryItem.setImage(getImage());
        if (getMedia() == null || getMedia().isEmpty())
            inventoryItem.setMedia(null);
        else
            try {
                inventoryItem.setMedia(new ObjectMapper().writeValueAsString(getMedia()));
            } catch (JsonProcessingException ex) {
                inventoryItem.setMedia(null);
            }
        inventoryItem.setCategory(getCategory() != null ? getCategory().toModel() : null);
        inventoryItem.setSubCategory(getSubCategory() != null ? getSubCategory().toInventorySubCategory() : null);
        if (getFeatures() == null || getFeatures().isEmpty())
            inventoryItem.setFeatures(null);
        else
            try {
                inventoryItem.setFeatures(new ObjectMapper().writeValueAsString(getFeatures()));
            } catch (JsonProcessingException ex) {
                inventoryItem.setFeatures(null);
            }
        try {
            inventoryItem.setListings(new ObjectMapper().writeValueAsString(getListings()));
        } catch (JsonProcessingException e) {
            inventoryItem.setListings(null);
            e.printStackTrace();
        }

//            inventoryItem.setParent(getParent().toInventory());
        return inventoryItem;
    }


    public InventoryPublicListingItem toListingItem() {
        InventoryPublicListingItem inventoryPublicListingItem = new InventoryPublicListingItem();
        inventoryPublicListingItem.setId(getUid());
        inventoryPublicListingItem.setEntityId(inventoryPublicListingItem.getEntityId());
        inventoryPublicListingItem.setName(getName());
        inventoryPublicListingItem.setAbout(getAbout());
        inventoryPublicListingItem.setTimeAdded(getTimeAdded());
        inventoryPublicListingItem.setAddedBy(getAddedBy());
        inventoryPublicListingItem.setQty(getQty());
        inventoryPublicListingItem.setPrice(getPrice());
        inventoryPublicListingItem.setPromos(new ListMapper<PromoDto, Promo>().map(getPromos(), PromoDto::toPromo));
        inventoryPublicListingItem.setImage(getImage());
        inventoryPublicListingItem.setMedia(getMedia());
        inventoryPublicListingItem.setCategory(getCategory().toModel());
        inventoryPublicListingItem.setSubCategory(getSubCategory().toInventorySubCategory());
        inventoryPublicListingItem.setFeatures(getFeatures());
//            inventoryPublicListingItem.setParent(InventoryDto.create(getParent()));
        return inventoryPublicListingItem;
    }
}
