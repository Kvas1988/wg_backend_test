# Implementation for Wargaming Backend test assignment (WIP)

This repo has a sourcecode for my implementation of [wg_forge_backend](https://github.com/wgnet/wg_forge_backend) test
assignment.

## Work in Progress...
### // TODO:
1. Configure hibernate-validator to validate input user's data. There are annotations from java.validation but they don't work at this moment.
2. Lowercase ASC/DESC order types in query params.
3. [6th task](https://github.com/wgnet/wg_forge_backend#6-%D0%B5-%D0%B7%D0%B0%D0%B4%D0%B0%D0%BD%D0%B8%D0%B5)

## Run the app
The fastest way to run the entire solution is via docker-compose. There's a [Makefile](Makefile), so you can run
```bash
make rebuild-docker
```
It will stop current docker-compose containers if exist, build java app and run `docker-compose up --build`

This [docker-compose.yaml](docker-compose.yaml) has 3 services:
- db: Postgresql container [configured by WG_Forge](https://github.com/wgnet/wg_forge_backend/blob/master/docker_instructions.md) to complete course
- python_to_postgres: python script to complete two first tasks
- wg_backend_spring_postgres: Spring app for the rest tasks

## Some info for my solutions
### Python

For the first two tasks I decided to write simple [python script](python/script.py) which will run several sql queries

### Spring Boot app
For the backend server task I took Spring, so most of the repo contains setup an src files for it

