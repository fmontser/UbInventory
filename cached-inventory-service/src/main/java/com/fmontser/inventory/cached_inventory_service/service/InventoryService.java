package com.fmontser.inventory.cached_inventory_service.service;

import java.util.Optional;
import org.springframework.stereotype.Service;

import com.fmontser.inventory.cached_inventory_service.model.Inventory;
import com.fmontser.inventory.cached_inventory_service.repository.InventoryRepository;
import com.fmontser.inventory.cached_inventory_service.dto.CreateInventoryRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {
	private final InventoryRepository inventoryRepository;

	//TODO logic...
	public Optional <Inventory> getInventoryByPlayerId(String playerId) {
		System.out.println("Log: Searching player's inventory on mongoDB");
		return (inventoryRepository.findByPlayerId(playerId));
	}

	public Inventory createInventory(CreateInventoryRequest request) throws Exception {
		System.out.println("Log: Creating " + request.getPlayerId() +" inventory on mongoDB");

		if (inventoryRepository.findByPlayerId(request.getPlayerId()).isPresent()) {
			System.err.println("Error: " + request.getPlayerId() + " already exists");
			throw new Exception(request.getPlayerId() + " already exists");
		} else {
			Inventory newInventory = new Inventory();
			newInventory.setPlayerId(request.getPlayerId());
			newInventory.setItems(request.getItems());
			return (inventoryRepository.save((newInventory)));
		}
	}
}
