# Brian Heaphy - Monese Home Assessment
Home assessment for Monese

## Running With Docker

#### Prerequisites 
- Install Docker

### Step 1
Install the mysql docker image and run the container using the following command.

`docker run --network=monese --name mysqldb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=db -e MYSQL_USER=user 
-e MYSQL_PASSWORD=pass -d mysql:8`

### Step 2
Pull the spring boot image from my Docker Hub repository.
`docker pull brianheaphy/monese-spring`

