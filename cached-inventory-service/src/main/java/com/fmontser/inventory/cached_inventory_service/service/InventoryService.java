package com.fmontser.inventory.cached_inventory_service.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.fmontser.inventory.cached_inventory_service.model.Inventory;
import com.fmontser.inventory.cached_inventory_service.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
	private final InventoryRepository inventoryRepository;

	//TODO logic...
	public Optional <Inventory> getInventoryByPlayerId(String playerId){
		System.out.println("Log: Searching player's inventory on mongoDB");
		return inventoryRepository.findByPlayerId(playerId);
	}
}
