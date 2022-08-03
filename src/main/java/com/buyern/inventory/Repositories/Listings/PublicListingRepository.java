package com.buyern.inventory.Repositories.Listings;

import com.buyern.inventory.Model.InventoryPublicListingItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PublicListingRepository extends MongoRepository<InventoryPublicListingItem, String> {

}
