#!/bin/bash

cd ../databases
# Prompt for username and password
read -p "Enter DB Username: " DB_USER
read -s -p "Enter DB Password: " DB_PASSWORD
echo

# Save the credentials in db_secrets.env
echo "DB_USER=$DB_USER" > db_secrets.env
echo "DB_PASSWORD=$DB_PASSWORD" >> db_secrets.env

# Stop and remove any existing container if it exists
docker stop userlogin-db-container 2>/dev/null || true
docker rm userlogin-db-container 2>/dev/null || true

# Create secret files from the environment variables file
source db_secrets.env

# Write secrets to files
echo -n $DB_USER > ../Secrets/db_user.txt
echo -n $DB_PASSWORD > ../Secrets/db_password.txt

# Check if secrets already exist, create them if they do not
if ! docker secret ls | grep -q db_user; then
    docker secret create db_user db_user.txt
else
    echo "Secret db_user already exists"
fi

if ! docker secret ls | grep -q db_password; then
    docker secret create db_password db_password.txt
else
    echo "Secret db_password already exists"
fi

cd ../Containers

# Build the Docker image
docker build -t userlogin-db -f Dockerfile-db .

# Initialize Docker Swarm if not already initialized
docker swarm init 2>/dev/null || true

# Remove the stack if it already exists to avoid conflicts
docker stack rm DB-stack

sleep 10

# Remove any existing network to avoid network not found error
docker network rm DB-stack_default 2>/dev/null || true

# Deploy the stack using Docker Compose
docker stack deploy -c ../Containers/docker-compose.yml DB-stack

# Wait for the database to initialize
sleep 10 # Don't remove because its really important.



cd ../login
mvn clean package

mvn javafx:run

# Cleanup secrets files
rm ../Secrets/db_user.txt ../Secrets/db_password.txt
