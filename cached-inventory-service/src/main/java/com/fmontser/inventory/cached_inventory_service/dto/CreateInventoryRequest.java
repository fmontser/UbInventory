package com.fmontser.inventory.cached_inventory_service.dto;

import java.util.List;
import com.fmontser.inventory.cached_inventory_service.model.Inventory.InventorySlot;
import lombok.Data;

@Data

public class CreateInventoryRequest {
	private String playerId;
	private List<InventorySlot> items;
}
