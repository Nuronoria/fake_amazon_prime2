#!/bin/bash

cd databases

# Stop and remove any existing container if it exists
docker stop userlogin-db-container 2>/dev/null || true
docker rm userlogin-db-container 2>/dev/null || true

# Create secret files from the environment variables file
source db_secrets.env

# Write secrets to files
echo -n $DB_USER > db_user.txt
echo -n $DB_PASSWORD > db_password.txt

# Remove old secrets if they exist
docker secret rm db_user 2>/dev/null || true
docker secret rm db_password 2>/dev/null || true

# Create Docker secrets
docker secret create db_user db_user.txt
docker secret create db_password db_password.txt

# Build the Docker image
docker build -t userlogin-db -f Dockerfile-db .

# Initialize Docker Swarm if not already initialized
docker swarm init 2>/dev/null || true

# Remove the stack if it already exists to avoid conflicts
docker stack rm DB-stack

# Deploy the stack using Docker Compose
docker stack deploy -c ../docker-compose.yml DB-stack

mysql -h 127.0.0.1 -P 3308 -u root -p password123