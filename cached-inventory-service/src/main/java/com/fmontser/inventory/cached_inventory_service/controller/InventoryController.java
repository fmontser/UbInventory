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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(("/api/v1/inventories"))
@RequiredArgsConstructor

public class InventoryController {
	private final InventoryService inventoryService;

	@GetMapping("/{playerId}")
	public ResponseEntity<Inventory> getInventoryByPlayerId(@PathVariable String playerId) {
		Optional<Inventory> response = inventoryService.getInventoryByPlayerId(playerId);
		if (response.isPresent())
			return (ResponseEntity.ok(response.get()));
		else
			return (ResponseEntity.notFound().build());
	}

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

}
