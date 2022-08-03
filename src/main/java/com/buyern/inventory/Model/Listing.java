package com.buyern.inventory.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "listings")
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    // public Listing
    @Column(nullable = false, unique = true)
    private String uName;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, columnDefinition="boolean default false")
    private String isPrivate;
    @Column(nullable = false, columnDefinition="boolean default false")
    private String isActive;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
}
