@echo off
setlocal

cd databases

docker stop userlogin-db-container
docker rm userlogin-db-container

docker build -t userlogin-db -f Dockerfile-db .

docker run -d --name userlogin-db-container -p 3308:3308 userlogin-db

cd ../login
call mvn clean package

call mvn javafx:run

endlocal
pause
