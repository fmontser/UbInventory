MAKEFLAGS 		+= --silent
COMPOSE_FILE	:= docker-compose.yml

up: down
	@echo "Setting services online..."
	@docker compose -f $(COMPOSE_FILE) up -d

down:
	@echo "Setting services offline..."
	@docker compose -f $(COMPOSE_FILE) down

logs:
	@docker compose logs -f $(word 2, $(MAKECMDGOALS))

bash:
	@docker exec -it $(word 2, $(MAKECMDGOALS)) bash

clean:
	@echo "Cleaning docker..."
	@docker compose -f $(COMPOSE_FILE) down --volumes --remove-orphans

re: build up

.PHONY: up down bash clean re