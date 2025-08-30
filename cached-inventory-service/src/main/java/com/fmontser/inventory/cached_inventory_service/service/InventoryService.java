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

	public Optional <Inventory> getInventoryByPlayerId(String playerId) {
		System.out.println("Log: Searching player's inventory on mongoDB");
		return (inventoryRepository.findByPlayerId(playerId));
	}

	public Optional<Inventory> updateInventory(String playerId, CreateInventoryRequest request) {
		Optional<Inventory> existingInventory =inventoryRepository.findByPlayerId(request.getPlayerId());

		if (existingInventory.isEmpty()) {
			System.out.println("Log: " + request.getPlayerId() + " inventory is empty, nothing to update");
			return (Optional.empty());
		} else {
			Inventory updateInventory = existingInventory.get();
			updateInventory.setItems(request.getItems());
			return (Optional.of(inventoryRepository.save(updateInventory)));
		}
	}


	public boolean deleteInventoryByPlayerId(String playerId) {
		Optional<Inventory> existingInventory = inventoryRepository.findByPlayerId(playerId);
		
		if (existingInventory.isPresent()) {
			inventoryRepository.delete(existingInventory.get());
			System.out.println("Log: " + playerId + " inventory has been deleted");
			return (true);
		} else {
			System.out.println("Log: " + playerId + " inventory to be deleted not found");
			return (false);
		}
	}
}
