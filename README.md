# Brian Heaphy - Monese Home Assessment
Home assessment for Monese

## Running With Docker

#### Prerequisites 
- Install Docker
- Install Postman (Not necessary but a collection of HTTP commands has been provided in the git repo)

### Step 1
Install the mysql docker image and run the container using the following command.

`docker run --network=monese --name mysqldb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=db -e MYSQL_USER=user 
-e MYSQL_PASSWORD=pass -d mysql:8`

### Step 2
Pull the spring boot image from my Docker Hub repository.

`docker pull brianheaphy/monese-spring`

OPTIONAL: Alternatively, there is a Dockerfile present and the image could be built manually.

### Step 3
Run the monese-spring image using this command.

`docker container run --network=monese -d --name monese-spring -p 9090:9090 --link mysqldb:mysql brianheaphy/monese-spring`

### Step 4
Open Postman and import the .json file present in the "postman" folder in the project.
Now you have a sample of commands to run to test the application.