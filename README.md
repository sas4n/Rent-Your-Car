# RentYourCar

https://rentyourcar.azurewebsites.net/

## Deployment

First you need to create a jar from the application by running

```shell
./mvnw pakage
```
in the root directory of the project. The jar file will be created in the target folder.

Copy the jar file and change to deploy branch. Paste the jar file and push it to github. The jar will be automatically deployed to Azure.

## Run project locally

The project uses JDK 11 and Maven, so these 2 tools needs to be installed on your machine.

From the root directory of the project run
```shell
./mvnw spring-boot:run
```

The application will be started on localhost:8080 and it will use an in-memory H2 database.

To have the image upload working on your machine you have to modify image-path property in application-dev.properties