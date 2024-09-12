# Read Me First

### Pre-requisites

* Gradle version 8
* Java version 17

### Steps to run the application
Please follow the following instructions carefully to run this MicroService:

* As you have cloned this project, go to the root directory of this project.
* Firstly clean the project, using the command:
   ````
  ./gradlew clean
  ````
* Now build the project using the below command, it will compile the code, run the junit tests and  create the jar in the folder build/libs/
  ````
  ./gradlew build
  ````

* Run the application by -
  ````
  ./gradlew bootRun
  ````
  It will start the applicantion at port 8080.

# Sample Request and Response

### Request
* ### Money Transfer
````
curl --location --request POST 'http://localhost:8080/api/v1/money-transfer' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cif": "4444444444",
    "sourceAccount": "01111111111",
    "destinationAccount": "KRYP09UN0985156734568923",
    "amount": "125000.00",
    "currency": "USD",
    "reason": "Play",
    "notes": "lunch"
}'
````
If successful it will give HTTP status 200 else it will give 500.
### Response
````
{
"status": "success",
"data": {
  "timestamp": "2024-09-09T23:22:02.087952",
  "transactionStatus": "FAILED",
  "failureReason": "BALANCE"
  }
}
````

# Technologies Used
* Java 17
* Spring Boot 3
* H2 In-memory Database
* REST APIs
* Junit5
* Entity created in DB:  TransferEntity
* C3 architectural pattern

# Swagger Used
* Swagger is used to document the APIs. You can access the swagger documentation at   
  http://localhost:8080/swagger-ui/index.html
* Swagger version springdoc-openapi-starter-webmvc-ui:2.6.0 which is compatible with Spring Boot 3

