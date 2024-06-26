@echo off
setlocal

:: Check if Docker is installed
docker --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Docker is not installed. Please install Docker from https://docs.docker.com/get-docker/ and run this script again.
    pause
    exit /b
)

:: Check if Maven is installed
mvn -v >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven is not installed. Downloading and installing Maven...
    set MAVEN_URL=https://downloads.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.zip
    set MAVEN_DIR=C:\Maven
    set MAVEN_ZIP=%TEMP%\maven.zip
    
    echo Downloading Maven...
    powershell -Command "Invoke-WebRequest -Uri %MAVEN_URL% -OutFile %MAVEN_ZIP%"
    
    echo Extracting Maven...
    powershell -Command "Expand-Archive -Path %MAVEN_ZIP% -DestinationPath C:\"
    move /Y "C:\apache-maven-3.8.6" %MAVEN_DIR%
    
    echo Setting environment variables...
    setx MAVEN_HOME "%MAVEN_DIR%"
    set "PathAdd=%MAVEN_DIR%\bin"
    setx PATH "%PATH%;%PathAdd%"
    
    echo Maven installed successfully.
    echo Please restart your Command Prompt and run this script again.
    pause
    exit /b
)

echo All required tools are installed.
endlocal
pause
