@echo off
REM Navigate to the databases directory
cd ..\databases

REM Prompt the user to enter the database password for USER Root
set /p "DB_PASSWORD=Enter DB Password for USER Root: "
echo.

REM Save the credentials in db_secrets.env
echo DB_PASSWORD=%DB_PASSWORD%> db_secrets.env

REM Generate a random 256-bit AES key using OpenSSL
for /f "delims=" %%i in ('openssl rand -base64 32') do set aes_key=%%i

REM Print the key
echo Generated AES Key: %aes_key%

REM Export the key as an environment variable (optional)
set AES_KEY=%aes_key%

REM Alternatively, save the key to a file
echo %aes_key%> ..\Secrets\AES_KEY.txt

echo AES key generated and saved

REM Load environment variables from db_secrets.env
setlocal enabledelayedexpansion
for /f "tokens=* delims=" %%i in (db_secrets.env) do (
    set "line=%%i"
    set "var=!line:~0,12!"
    set "value=!line:~13!"
    set "!var!=!value!"
)

REM Navigate to the Secrets directory
cd ..\Secrets

REM Write the database password to a file
echo %DB_PASSWORD%> db_password.txt

REM Stop and remove any existing container if it exists
docker swarm leave --force 2>nul
docker stop userlogin-db-container 2>nul
docker rm userlogin-db-container 2>nul

REM Remove the stack if it already exists to avoid conflicts
REM docker stack rm DB-stack 2>nul

REM Initialize Docker Swarm if not already initialized
docker swarm init 2>nul

REM Create a Docker secret for the database password if it doesn't already exist
docker secret ls | findstr /r /c:" db_password " >nul
if errorlevel 1 (
    docker secret create db_password db_password.txt
) else (
    echo Secret db_password already exists
)

REM Navigate back to the databases directory
cd ..\databases

REM Build the Docker image
docker build -t userlogin-db -f Dockerfile-db .

REM Wait for a moment to ensure stack is removed
timeout /t 10 /nobreak >nul

REM Remove any existing network to avoid network not found error
docker network rm DB-stack_default 2>nul

REM Deploy the stack using Docker Compose
docker stack deploy -c ..\Containers\docker-compose.yml DB-stack

REM Wait for the database to initialize
timeout /t 10 /nobreak >nul

REM Navigate to the login directory
cd ..\login

REM Build and package the Maven project
mvn clean package

REM Run the JavaFX application
mvn javafx:run

REM Stop the Docker stack
docker stack rm DB-stack

REM Clean up secret files
del ..\Secrets\db_password.txt
del ..\Secrets\AES_KEY.txt
del ..\databases\db_secrets.env

endlocal
