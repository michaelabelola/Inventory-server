package com.buyern.inventory.dtos;

import com.buyern.inventory.Model.Promo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class PromoDto implements Serializable {
    private Long id;
    private String name;
    private Long entityId;
    private Date startDate;
    private Date endDate;

    public static PromoDto create(Promo promo) {
        PromoDto promoDto = new PromoDto();
        promoDto.setId(promo.getId());
        promoDto.setName(promo.getName());
        promoDto.setEntityId(promo.getEntityId());
        promoDto.setStartDate(promo.getStartDate());
        promoDto.setEndDate(promo.getEndDate());
        return promoDto;
    }

    public Promo toPromo() {
        Promo promo = new Promo();
        promo.setName(getName());
        promo.setEntityId(getEntityId());
        promo.setStartDate(getStartDate());
        promo.setEndDate(getEndDate());
        return promo;
    }
}
