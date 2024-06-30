#!/bin/bash

# Check if Docker is installed
if ! command -v docker &> /dev/null
then
    echo "Docker is not installed. Please install Docker from https://docs.docker.com/get-docker/ and run this script again."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null
then
    echo "Maven is not installed. Downloading and installing Maven..."
    MAVEN_VERSION=3.8.6
    MAVEN_DIR=/opt/maven
    MAVEN_TAR=apache-maven-$MAVEN_VERSION-bin.tar.gz

    sudo mkdir -p $MAVEN_DIR
    wget https://downloads.apache.org/maven/maven-3/$MAVEN_VERSION/binaries/$MAVEN_TAR -P /tmp
    sudo tar -xzvf /tmp/$MAVEN_TAR -C $MAVEN_DIR --strip-components=1

    echo "Setting environment variables..."
    echo "export MAVEN_HOME=$MAVEN_DIR" | sudo tee -a /etc/profile.d/maven.sh
    echo "export PATH=\$PATH:\$MAVEN_HOME/bin" | sudo tee -a /etc/profile.d/maven.sh
    source /etc/profile.d/maven.sh

    echo "Maven installed successfully. Please restart your terminal and run this script again."
    exit 1
fi

echo "All required tools are installed."
