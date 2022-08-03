package com.buyern.inventory.Model;

import com.buyern.inventory.Enums.PromoStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "promos")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String about;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateCreated;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date startDate;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date endDate;
    private PromoStatus status;
    @OneToMany
    @JoinTable(name = "promo_inventories", joinColumns = @JoinColumn(name = "promo_id"), inverseJoinColumns = @JoinColumn(name = "inventory_id"))
    @ToString.Exclude
    private List<Inventory> inventories;
    private Long entityId;
    private String image;
    /**
     * promo code is unique to inventory or group.
     * @implNote format = entity*/
    private String promoCode;
    /**
     * run count is the amount of  timwe this promo nas been started (<b>run</b>)*/
    private long runCount = 0L;
    /**
     * <h3>Images Array</h3>
     * {id:1, link:"www.image.com/img.jpg", tag:"coverImg", title:"get  one buy blah blah blah 😉", text:"just random unneeded text users can add"}
     */
    private String images; //all promo images json array
}