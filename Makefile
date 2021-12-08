rebuild-docker:
	docker-compose down
	./mvnw clean package -DskipTests
	docker-compose up --build

build:
	./mvnw clean package -DskipTests

rebuild-run:
	./mvnw clean install spring-boot:run

.PHONY: build