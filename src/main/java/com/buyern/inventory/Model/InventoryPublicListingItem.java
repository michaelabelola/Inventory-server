package com.buyern.inventory.Model;

import com.buyern.inventory.Objects.InventoryItemFeature;
import com.buyern.inventory.Objects.Media;
import com.buyern.inventory.dtos.CategoryDto;
import com.buyern.inventory.dtos.InventoryDto;
import com.buyern.inventory.dtos.PromoDto;
import com.buyern.inventory.dtos.SubCategoryDto;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Document("inventories")
@Data
public class InventoryPublicListingItem {
    @MongoId
    private String id;
    private String name;
    private String entityId;
    private String about;
//    @CreationTimestamp
//    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
//    @Temporal(TemporalType.TIMESTAMP)
    private Date timeAdded;
    private Long addedBy;//user Id
    private int qty;
    private int unit;
    private double price;
//    @DocumentReference()
    private List<Promo> promos;
    private String image;
    private List<Media> media;
    private Category category;
    private SubCategory subCategory;
    private List<Category> customCategories;
    private List<InventoryItemFeature> features;
    private Inventory parent;
}
