package com.buyern.inventory.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "inventory_groups")
@Data
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Long entityId;
    @OneToMany
    @JoinTable(name = "inventory_groups_items", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "inventory_id"))
    private List<Inventory> inventories;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateCreated;
}