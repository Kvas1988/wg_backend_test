rebuild-docker:
	docker-compose down
	./mvnw clean package -DskipTests
	docker-compose up --build

build:
	./mvnw clean package -DskipTests

.PHONY: build