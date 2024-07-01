@echo off

cd ..\databases

set /p "DB_PASSWORD=Enter DB Password for USER Root: "

:: Save the credentials in db_secrets.env
echo DB_PASSWORD=%DB_PASSWORD%> db_secrets.env

:: Stop and remove any existing container if it exists
docker stop userlogin-db-container 2>nul
docker rm userlogin-db-container 2>nul

:: Create secret files from the environment variables file
for /F "tokens=2 delims==" %%i in (db_secrets.env) do set DB_PASSWORD=%%i

cd ..\Secrets
:: Write secrets to files
echo %DB_PASSWORD%> db_password.txt

:: Initialize Docker Swarm if not already initialized
docker swarm init 2>nul

:: Create Docker secret if it does not already exist
docker secret ls | findstr db_password >nul
if errorlevel 1 (
    docker secret create db_password db_password.txt
) else (
    echo Secret db_password already exists
)

cd ..\databases

:: Build the Docker image
docker build -t userlogin-db -f Dockerfile-db .

:: Remove the stack if it already exists to avoid conflicts
docker stack rm DB-stack

timeout /t 10

:: Remove any existing network to avoid network not found error
docker network rm DB-stack_default 2>nul

:: Deploy the stack using Docker Compose
docker stack deploy -c ..\Containers\docker-compose.yml DB-stack

:: Wait for the database to initialize
timeout /t 10

cd ..\login
mvn clean package

mvn javafx:run

:: Stop the docker swarm
docker stack rm DB-stack

:: Clean up docker secrets
docker secret rm db_password

:: Cleanup secrets files
del ..\Secrets\db_password.txt
del ..\databases\init_tmp.sql

pause
