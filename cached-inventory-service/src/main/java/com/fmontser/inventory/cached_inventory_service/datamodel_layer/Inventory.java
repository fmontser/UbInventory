package com.fmontser.inventory.cached_inventory_service.datamodel_layer;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "inventories")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Inventory {
	@Id
	private String id;
	private String playerId;
	private List<InventorySlot> items;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class InventorySlot {
		private String itemId;
		private int quantity;
	}
}