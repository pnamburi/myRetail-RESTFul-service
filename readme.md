#MyRetail REST Service

MyRetail RESTful service provides the API to:

    1. Retrieve Product information from mutiple sources by Product Id

    2. Modify the price information in the NoSQL database

##Pre-requisites
1) Java 8 installed
2) Maven 3 installed
3) Git Client Intalled
4) SoapUI/Any REST Client

##Technologies Used
-----------------

1. Spring Boot - https://projects.spring.io/spring-boot/
2. Amazon DynamoDB - https://aws.amazon.com/dynamodb/
3. Maven - https://maven.apache.org/

##Instructions to Setup
---------------------
1. Clone The Repository from Git Hub to a local workstation -- git clone https://github.com/pnamburi/myRetail-RESTFul-service.git
2. Open Command Prompt/Terminal and Change Directory Location to the project directory -- cd <WORKSTATION_PATH>/myRetail-RESTFul-service
3. Build the project using Maven - mvn clean install
3. Setup and run Local DynamoDB database -- mvn exec:java -Dexec.mainClass="com.myretail.product.config.SetupLocalDynamoDB" -Dexec.classpathScope="test" -Psetup-db
4. Run the Spring boot app with the jar create from Maven install in Step 3 --  java -jar -Dspring.profiles.active=local target/myRetail-RESTFul-service-1.0.0-SNAPSHOT.jar
5. Test the get product API from the brouser -- http://localhost:8080/products/13860428
6. Test the update product API with SOAP UI or any REST client  --- http://localhost:8080/products -- JSON Input : {   "id": 13860428,   "name": "The Big Lebowski (Blu-ray)",   "current_price":    {      "value": 17.44,      "currency_code": "USD"   }}
7. Import the sample SoapUI project located in test resources directory for Integration testing(Can be Automated) -- <WORKSTATION_PATH>/myRetail-RESTFul-service/src/test/resources.
 



##Sample Requests and Responses with curl
--------------------------
 
## GET Request:
 
### Request:
 
 `curl -X GET --header 'Accept: application/json' 'http://localhost:8080/products/13860428'`
 
 ### Response:
 
 {"id":13860428,"name":"The Big Lebowski (Blu-ray)","current_price":{"value":17.44,"currency_code":"USD"}}
 
 ## PUT Request:

Following PUT request will store information of productID:13860428 in NOSQL database

###Request:

`curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 14.99,"currency_code":"USD"}} \ 
  ' 'http://localhost:8080/products/'`
  
###Response:

{"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value":13.49,"currency_code":"USD"}
 


 