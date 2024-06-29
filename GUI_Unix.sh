#!/bin/bash

cd databases

docker stop userlogin-db-container
docker rm userlogin-db-container
# Create secret files from the environment variables file
source db_secrets.env
echo -n $DB_USER > db_user.txt
echo -n $DB_PASSWORD > db_password.txt
# Create Docker secrets
docker secret create db_user db_user.txt
docker secret create db_password db_password.txt

docker build -t userlogin-db -f Dockerfile-db .

# Initialize Docker Swarm if not already initialized
docker swarm init 2>/dev/null || true
# Deploy the stack using Docker Compose
docker stack deploy -c ../docker-compose.yml mystack

# docker run  --env-file db_secrets.env -d --name userlogin-db-container -p 3308:3308 userlogin-db

# Wait for the database to initialize
sleep 10

cd ../login
mvn clean package

mvn javafx:run


# Cleanup secrets files
rm db_user.txt db_password.txt