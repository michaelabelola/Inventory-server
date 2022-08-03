package com.buyern.inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListingDto implements Serializable {
    private Long id;
    private String name;
    private String isPrivate;
    private String isActive;
    private Date dateCreated;
}
