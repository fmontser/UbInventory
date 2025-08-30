package com.fmontser.inventory.cached_inventory_service.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import com.fmontser.inventory.cached_inventory_service.model.Inventory;
import com.fmontser.inventory.cached_inventory_service.repository.InventoryRepository;
import com.fmontser.inventory.cached_inventory_service.dto.CreateInventoryRequest;

import lombok.RequiredArgsConstructor;

//TODO mover operaciones de cache a su propia clase...

@Service
@RequiredArgsConstructor
public class InventoryService {
	private final InventoryRepository inventoryRepository;
	private final RedisTemplate<String, Object> redisTemplate;

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

	public Optional <Inventory>  getInventoryByPlayerId(String playerId) {
		System.out.println("Log: Searching player's inventory");

		final String cacheKey = "inventory::" + playerId;

		try {
			Inventory cachedInventory = (Inventory) redisTemplate.opsForValue().get(cacheKey);

			if (cachedInventory != null) {
				System.out.println("Log: found cached inventory");
				return (Optional.of(cachedInventory));
			}
		} catch (Exception e) {
			System.err.println("Error: cache error: " + e.getMessage());
		}

		Optional<Inventory> nonCachedInventory = inventoryRepository.findByPlayerId(playerId);
		try {
			if (nonCachedInventory.isPresent()) {
				System.out.println("Log: found inventory, copying to cache");
				redisTemplate.opsForValue().set(cacheKey, nonCachedInventory.get());
			}
		} catch (Exception e) {
			System.err.println("Error: cache error: " + e.getMessage());
		}
		return (nonCachedInventory);
	}

	public Optional<Inventory> updateInventory(String playerId, CreateInventoryRequest request) {
		Optional<Inventory> optInventory =inventoryRepository.findByPlayerId(request.getPlayerId());

		if (optInventory.isEmpty()) {
			System.out.println("Log: " + request.getPlayerId() + " inventory is empty, nothing to update");
			return (Optional.empty());
		} else {
			Inventory inventory = optInventory.get();
			inventory.setItems(request.getItems());
			Inventory updatedInventory = inventoryRepository.save(inventory);
			
			try {
				final String cacheKey = "inventory::" + playerId;
				redisTemplate.opsForValue().set(cacheKey, updatedInventory);
			} catch (Exception e) {
				System.err.println("Error: cache error: " + e.getMessage());
			}
			return (Optional.of(updatedInventory));
		}
	}

	public boolean deleteInventoryByPlayerId(String playerId) {
		Optional<Inventory> optInventory = inventoryRepository.findByPlayerId(playerId);
		
		if (optInventory.isPresent()) {
			inventoryRepository.delete(optInventory.get());
			try {
				final String cacheKey = "inventory::" + playerId;
				redisTemplate.delete(cacheKey);
			} catch (Exception e) {
				System.err.println("Error: cache error: " + e.getMessage());
			}
			System.out.println("Log: " + playerId + " inventory has been deleted");
			return (true);
		} else {
			System.out.println("Log: " + playerId + " inventory to be deleted not found");
			return (false);
		}
	}
}
