#!/bin/bash

cd databases

docker stop userlogin-db-container
docker rm userlogin-db-container

docker build -t userlogin-db -f Dockerfile-db .

 

cd ../login
mvn clean package

mvn javafx:run
