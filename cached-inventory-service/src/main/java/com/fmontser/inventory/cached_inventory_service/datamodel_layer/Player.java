package com.fmontser.inventory.cached_inventory_service.datamodel_layer;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "players")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Player {
	@Id
	private String id;
	private String name;
}