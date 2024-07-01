@echo off

:: Check if Docker is installed
where docker >nul 2>&1
if %errorlevel% neq 0 (
    echo Docker is not installed. Please install Docker from https://docs.docker.com/get-docker/ and run this script again.
    exit /b 1
)

:: Check if Maven is installed
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven is not installed. Downloading and installing Maven...
    set "MAVEN_VERSION=3.8.6"
    set "MAVEN_DIR=C:\opt\maven"
    set "MAVEN_TAR=apache-maven-%MAVEN_VERSION%-bin.zip"

    if not exist %MAVEN_DIR% (
        mkdir %MAVEN_DIR%
    )

    powershell -command "Invoke-WebRequest -Uri https://downloads.apache.org/maven/maven-3/%MAVEN_VERSION%/binaries/%MAVEN_TAR% -OutFile C:\temp\%MAVEN_TAR%"

    powershell -command "Expand-Archive -Path C:\temp\%MAVEN_TAR% -DestinationPath %MAVEN_DIR% -Force"

    :: Setting environment variables
    setx MAVEN_HOME "%MAVEN_DIR%\apache-maven-%MAVEN_VERSION%"
    setx PATH "%PATH%;%MAVEN_HOME%\bin"

    echo Maven installed successfully. Please restart your terminal and run this script again.
    exit /b 1
)

echo All required tools are installed.
pause
