# Download Maven
$mavenUrl = "https://downloads.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.zip"
$destinationPath = "C:\Temp\apache-maven-3.8.6-bin.zip"
Invoke-WebRequest -Uri $mavenUrl -OutFile $destinationPath

# Extract Maven
$extractPath = "C:\Program Files\Apache\Maven"
Expand-Archive -Path $destinationPath -DestinationPath $extractPath

# Remove the downloaded zip file
Remove-Item $destinationPath

# Set Environment Variables
$mavenHome = "$extractPath\apache-maven-3.8.6"
[System.Environment]::SetEnvironmentVariable('MAVEN_HOME', $mavenHome, [System.EnvironmentVariableTarget]::Machine)

# Add Maven to the Path
$existingPath = [System.Environment]::GetEnvironmentVariable('Path', [System.EnvironmentVariableTarget]::Machine)
$newPath = "$existingPath;$mavenHome\bin"
[System.Environment]::SetEnvironmentVariable('Path', $newPath, [System.EnvironmentVariableTarget]::Machine)

# Verify Maven Installation
mvn -version
