@echo off
setlocal enabledelayedexpansion

REM Prompt the user to enter their GitHub username
set /p GHCR_USERNAME=Enter your GitHub username: 

REM Prompt the user to enter their GitHub personal access token
set /p GHCR_TOKEN=Enter your GitHub personal access token: 

REM Authenticate with GitHub Container Registry
echo %GHCR_TOKEN% | docker login ghcr.io -u %GHCR_USERNAME% --password-stdin

REM Check if the login was successful
if %errorlevel% neq 0 (
    echo Login failed. Please check your username and token.
    exit /b %errorlevel%
)

REM Pull the latest image from GitHub Container Registry
docker pull ghcr.io/magn4/userlogin-db:latest

REM Check if the pull was successful
if %errorlevel% neq 0 (
    echo Image pull failed. Please check the image name and tag.
    exit /b %errorlevel%
)

REM Create the db_password.txt file if it doesn't exist and add 'password123' to it
if not exist ..\Secrets\db_password.txt (
    echo password123> ..\Secrets\db_password.txt
)

REM Run the Docker container
docker run -d --name userlogin-db -p 3308:3308 ghcr.io/magn4/userlogin-db:latest

REM Check if the container started successfully
if %errorlevel% neq 0 (
    echo Failed to start the Docker container. Please check the Docker run command.
    exit /b %errorlevel%
)

echo Docker container started successfully.

REM Navigate to the login directory
cd ..\login

REM Build and package the Maven project
mvn clean package

REM Check if Maven build was successful
if %errorlevel% neq 0 (
    echo Maven build failed. Please check the build logs.
    exit /b %errorlevel%
)

REM Run the JavaFX application
mvn javafx:run

REM Check if JavaFX application ran successfully
if %errorlevel% neq 0 (
    echo Failed to run the JavaFX application. Please check the application logs.
    exit /b %errorlevel%
)

REM Stop the Docker container
docker stop userlogin-db

REM Remove the Docker container
docker rm userlogin-db

REM Cleanup - Remove the GitHub token variable for security
set "GHCR_TOKEN="

REM Clean up secret files
del ..\Secrets\db_password.txt
del ..\Secrets\AES_KEY.txt
del ..\databases\db_secrets.env

endlocal
