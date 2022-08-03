package com.buyern.inventory.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "inventories_items")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class InventoryItem {
    @Id
    @Column(unique = true, nullable = false)
    private String uid = UUID.randomUUID().toString();
    private String name;
    private String entityId;
    private String about;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeAdded;
    private Long addedBy;//user Id
    private int qty;
    private int unit;
    private double price;
    /**
     * <h3>Promo Json Array</h3>
     * {promoId:123, promoName:"Summer Discount", }
     */
    @OneToMany
    @JoinTable(name = "promo_inventory_ids", joinColumns = @JoinColumn(name = "inventory_id"), inverseJoinColumns = @JoinColumn(name = "promo_id"))
    private List<Promo> promos; // promo json array
    private String image;
    @Column(columnDefinition = "LONGTEXT")
    private String media; // image link json array
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToOne
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;
    /**
     * this is an array of custom categories created by the entities
     */
    @OneToMany
    @JoinTable(name = "custom_categories_id", joinColumns = @JoinColumn(name = "inventory_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> customCategories;
    /**
     * <h3>features array available to all variations</h3>
     * <p>
     * if(is parent){
     * //value is default value, visible hides all variation values,
     * {id:123, name:"color", value:"red", "visible":"true" }
     * } else
     * {id:123, name:"color", value:"red", "visible":"true" }
     */
    @Column(columnDefinition = "LONGTEXT")
    private String features;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Inventory parent;
    /**
     * <h3>listings array</h3>
     * <p>
     *     when an item is added to a public listing, it is added to this array;
     *     {id:123, name:"PublicListings", "visible":"true" }
     */
    @Column(columnDefinition = "LONGTEXT")
    private String listings;

    @Override
    public String toString() {
        return "InventoryItem{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", entityId='" + entityId + '\'' +
                ", about='" + about + '\'' +
                ", timeAdded=" + timeAdded +
                ", addedBy=" + addedBy +
                ", qty=" + qty +
                ", unit=" + unit +
                ", price=" + price +
                ", promos=" + promos +
                ", image='" + image + '\'' +
                ", media='" + media + '\'' +
                ", category=" + category +
                ", subCategory=" + subCategory +
                ", customCategories=" + customCategories +
                ", features='" + features + '\'' +
                ", parent=" + parent +
                ", listings='" + listings + '\'' +
                '}';
    }
}