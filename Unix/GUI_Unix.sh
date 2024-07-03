#!/bin/bash

# Navigate to the databases directory
cd ../databases

# Prompt the user to enter the database password for USER Root
read -s -p "Enter DB Password for USER Root: " DB_PASSWORD
echo


# Save the credentials in db_secrets.env
echo "DB_PASSWORD=$DB_PASSWORD" > db_secrets.env


# Generate a random 256-bit AES key
# Generate 32 bytes of raw data and encode to base64
aes_key=$(openssl rand 32 | openssl base64)

# Print the key 
echo "Generated AES Key: $aes_key"

# Export the key as an environment variable (optional)
export AES_KEY=$aes_key

# Alternatively, save the key to a file
# echo "MySuperSecretKey" > ../secrets/AES_KEY.txt

echo "AES key generated and saved"

# Load environment variables from db_secrets.env
source db_secrets.env

# Navigate to the Secrets directory
cd ../Secrets

# Write the database password to a file
echo -n $DB_PASSWORD > db_password.txt

# Stop and remove any existing container if it exists
docker swarm leave --force 2>/dev/null || true
docker stop userlogin-db-container 2>/dev/null || true
docker rm userlogin-db-container 2>/dev/null || true

# Remove the stack if it already exists to avoid conflicts
# docker stack rm DB-stack  2>/dev/null || true

# Initialize Docker Swarm if not already initialized
docker swarm init 

# Create a Docker secret for the database password if it doesn't already exist
if ! docker secret ls | grep -q db_password; then
    docker secret create db_password db_password.txt
else
    echo "Secret db_password already exists"
fi

# Navigate back to the databases directory
cd ../databases

# Build the Docker image
docker build -t userlogin-db -f Dockerfile-db .


# Wait for a moment to ensure stack is removed
sleep 10

# Remove any existing network to avoid network not found error
docker network rm DB-stack_default 2>/dev/null || true

# Deploy the stack using Docker Compose
docker stack deploy -c ../Containers/docker-compose.yml DB-stack

# Wait for the database to initialize
sleep 10

# Navigate to the login directory
cd ../login

# Build and package the Maven project
mvn clean package

# Run the JavaFX application
mvn javafx:run


read -p "Do you want to stop and remove the Docker container? (yes/no): " USER_RESPONSE

if [ "$USER_RESPONSE" == "yes" ]; then
    # Stop the Docker stack
    docker stack rm DB-stack
else
    echo "Docker container will keep running."
fi

# Stop the Docker stack
docker stack rm DB-stack


# Clean up secret files
rm ../Secrets/db_password.txt
rm ../Secrets/AES_KEY.txt
rm ../databases/db_secrets.env