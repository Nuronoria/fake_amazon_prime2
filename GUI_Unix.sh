#!/bin/bash

cd databases

docker stop userlogin-db-container
docker rm userlogin-db-container

docker build -t userlogin-db -f Dockerfile-db .

docker run  --env-file .env -d --name userlogin-db-container -p 3308:3308 userlogin-db

cd ../login
mvn clean package

mvn javafx:run
