package com.buyern.inventory.Services;

import com.buyern.inventory.Model.Promo;
import com.buyern.inventory.Repositories.PromoRepository;
import com.buyern.inventory.exception.RecordNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PromoService {
    final PromoRepository promoRepository;

    public Promo getPromo(Long promoId) {
        Optional<Promo> promo = promoRepository.findById(promoId);
        if (promo.isEmpty())
            throw new RecordNotFoundException("Promo with id %d does not exist".formatted(promoId));
        return promo.get();
    }

    public List<Promo> getPromos(List<Long> promoIds) {
        List<Promo> promos = promoRepository.findAllById(promoIds);
        if (promos.isEmpty())
            throw new RecordNotFoundException("No Promos found");
        return promos;
    }
}
