package com.fmontser.inventory.cached_inventory_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.fmontser.inventory.cached_inventory_service.model.Item;

public interface ItemRepository extends MongoRepository<Item, String> {
	//TODO add more query methods...
}
