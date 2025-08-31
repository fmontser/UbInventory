package com.fmontser.inventory.cached_inventory_service.controller;


import java.util.Optional;

import com.fmontser.inventory.cached_inventory_service.model.Inventory;
import com.fmontser.inventory.cached_inventory_service.service.InventoryService;
import com.fmontser.inventory.cached_inventory_service.dto.CreateInventoryRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping(("/api/v1/inventories"))
@RequiredArgsConstructor

public class InventoryController {
	private final InventoryService inventoryService;

	//TODO mover las comprobaciones a la capa de servicio (hay redundancia...)

	@PostMapping()
	public ResponseEntity<Inventory> createInventory(@RequestBody CreateInventoryRequest request) {
		try {
			Inventory newInventory = inventoryService.createInventory(request);
			return (new ResponseEntity<>(newInventory, HttpStatus.CREATED));
		} catch (Exception e) {
			System.err.println("Error: Data conflict while creating inventory: " + e.getMessage());
			return (ResponseEntity.status(HttpStatus.CONFLICT).build());
		}
	}

	@GetMapping("/{playerId}")
	public ResponseEntity<Inventory> getInventoryByPlayerId(@PathVariable String playerId) {
		Optional<Inventory> inventory = inventoryService.getInventoryByPlayerId(playerId);
		if (inventory.isPresent())
			return (ResponseEntity.ok(inventory.get()));
		else
			return (ResponseEntity.notFound().build());
	}

	@PutMapping("/{playerId}")
	public ResponseEntity<Inventory> updateInventory(@PathVariable String playerId, @RequestBody CreateInventoryRequest request) {
		Optional<Inventory> inventory = inventoryService.updateInventory(playerId, request);
		if (inventory.isPresent())
			return (ResponseEntity.ok(inventory.get()));
		else
			return (ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{playerId}")
	public ResponseEntity<Void> deleteInventory(@PathVariable String playerId) {
		boolean isDeleted = inventoryService.deleteInventoryByPlayerId(playerId);
		if (isDeleted)
			return (ResponseEntity.noContent().build());
		else
			return (ResponseEntity.notFound().build());
	}

}
