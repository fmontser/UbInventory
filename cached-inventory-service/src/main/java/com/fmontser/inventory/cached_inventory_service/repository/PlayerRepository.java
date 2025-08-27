package com.fmontser.inventory.cached_inventory_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.fmontser.inventory.cached_inventory_service.model.Player;

public interface PlayerRepository extends MongoRepository<Player, String> {
	//TODO implement...
}
