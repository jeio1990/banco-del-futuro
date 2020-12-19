# RISK MAP API #

Risk map about future bank.

### Requirements ###

* Linux SO, if you can deploy with docker and using the files configuration in this repository
* Docker
* Java 1.8
* Maven


### What is this repository for? ###

* **riskmap** - code java to implement logic to calculate risk map of service provider
* **Deploy project** - project directory to deploy api with docker container

### Deploy ###

* Main project is named **future-bank**, is a maven project that has a four modules.
* Move to the riskmap directory and execute **mvn clean install**.
* Them you move to **risk-deploy** and check if in a **risk-map-api** directory you see **risk-map-1.0.0.jar**
* Into the **risk-deploy** you have to execute command **docker-compose up -d**
* With the above command you should deploy the complete environment with api and database nosql mongodb.

### API documentation ###

* The api documentation is make through swagger and when deploy project you can see that with this url: http://localhost:8082/swagger-ui.html
* Remember update the correct port in tomcat embedded with springboot
* If you want to view real documentacion online you can access with this url: http://192.168.243.65:8089/swagger-ui.html

### Postman request ###

* If you want to test API with Postman, in this repository there is a collection postman file. 
