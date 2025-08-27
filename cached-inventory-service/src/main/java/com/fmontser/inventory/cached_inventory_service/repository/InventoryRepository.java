package com.fmontser.inventory.cached_inventory_service.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.fmontser.inventory.cached_inventory_service.model.Inventory;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
	Optional<Inventory> findByPlayerId(String playerId);
	//TODO add more query methods...
}
