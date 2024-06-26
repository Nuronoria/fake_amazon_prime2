
# Thinks i have added:

# 1. **DataBases** 

- deployment files are now in the ./databases directory
# 2. **Docker-Compose** 

- File that will deploy 2 docker container,

![](/Documentation/Screenshots/Docker-compose.png)
	1. One that has all the databases named: *userlogin-db* 
	2. One that should run the application, but it turned out that running a Java GUI application in a docker container is a bad idea
	3. The docker-compose file open communication between the 2 container so that the application can have access to the Databases.
# 3. **Dockerfile**:
![](/Documentation/Screenshots/Dockerfile.png)

# 4. **Dockerfile-db**:
![](/Documentation/Screenshots/Dockerfile-db.png)

# 5. **Setup and Deploy for UNIX:**

## Setup for Unix:
![](/Documentation/Screenshots/Setup_Unix.png)
- The **Setup_Unix.sh** bash script will
	1. First: make sure that *Docker* is installed in the machine where the we want to deploy the project, if not installed the script will provide you with the link where you can install docker from and ask you to rerun the script.
	2. Second: it will check if *Maven* is installed in the machine, if not it will download it using the *wget* command and then add it to the /etc/profile.d directory so that you can use the *mvn* command from the terminal.
- After Setting Up everything the script will tell you that all the requirement have been installed.
## Deploy the GUI:
![](/Documentation/Screenshots/GUI_Unix.png)
- The **GUI_Unix.sh** bash script will 
	1. *Move* to the *Databases* directory where the Dockerfile-db is situated
	2. *Stop and Remove* all containers that are running with the same name as the one we are trying to make.
	3. It will build a new docker image named userlogin-db
	4. It will run it on port 3308 so that the GUI can get access to it
	5. It will now go back to the main Directory (/login) where the pom.xml is situated
	6. Now it will two command that will clean old packages and create a new jar file. And then it will run the javafx application using the
```bash
mvn javafx:run
```


# 6. **Setup and Deploy for Windows:**

- The Setup and Deployment in Windows *batch* script follow the same steps as the UNIX *Bash* script but in a *batch* format
## Setup for Windows:
![Setup_Windows](/Documentation/Screenshots/Setup_Windows.png)
## Deploy the GUI:
![GUI_Windows](/Documentation/Screenshots/GUI_Windows.png)


# 7. **Security implementations:**

## 1. Sensetive infos:

- I have added all sensetive informations like login usernames and password to a secrets.properties file and added it to the .gitignore file.
	These secrets can be interacted with by importing the properties file, and then using the Property getter commands.


## 2. Securing project against *SQL Injections*:


## 3. Incrypting Login infos in Database:

