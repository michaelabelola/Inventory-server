package com.buyern.inventory.Model;

import com.buyern.inventory.Enums.OrderParty;
import com.buyern.inventory.Enums.OrderStatus;
import com.buyern.inventory.Objects.Customer;
import com.buyern.inventory.Objects.Delivery;
import com.buyern.inventory.Objects.InventoryItemFeature;
import com.buyern.inventory.Objects.Media;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Data
@Document("delivery")
public class Order {
    //orders can have child orders that share delivery but from different companies
//    each order or child order can have multiple inventories and multiple inventory items
    @MongoId
    private String id;
    private String[] inventoriesIds;

    private String name;
    private String entityId;
    private String about;
    // private int unit;
    private double price;
    // inventory or inventories and deliveries price
    private double totalPrice;

    private List<Promo> promos;
    private String image;
    private List<Media> media;
    private Category category;
    private SubCategory subCategory;
    private List<InventoryItemFeature> features;

    private boolean paymentOnDelivery;
    private OrderStatus status;
    /**
     * On Completion
     */
    private String feedback;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCompleted;
    /**
     * On Cancellation
     */
    private OrderParty cancelledBy;
    private String reasonForCancellation;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeCancelled;

    private Delivery delivery;

    private Customer customer;
    private List<Order> subOrders;
}