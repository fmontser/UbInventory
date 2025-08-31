MAKEFLAGS		+= --silent
COMPOSE_FILE	:= docker-compose.yml
APP_DIR			:= cached-inventory-service

all: up
	@echo "Launching inventory service..."
	@cd $(APP_DIR) && ./mvnw spring-boot:run

up: down
	@echo "Setting docker services online..."
	@docker compose -f $(COMPOSE_FILE) up -d

down:
	@echo "Setting docker services offline..."
	@docker compose -f $(COMPOSE_FILE) down

logs:
	@docker compose logs -f $(word 2, $(MAKECMDGOALS))

bash:
	@docker exec -it $(word 2, $(MAKECMDGOALS)) bash

clean:
	@echo "Cleaning docker..."
	@docker compose -f $(COMPOSE_FILE) down --volumes --remove-orphans

re: build up

.PHONY: all up down bash clean re