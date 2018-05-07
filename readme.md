#MyRetail REST Service

MyRetail RESTful service provides the API to:
1. Retrieve Product information from multiple sources by Product Id.
2. Modify the price information in the NoSQL database.

##Pre-requisites/required software

1. Java 8 installed
2. Maven 3 installed
3. Git Client Intalled
4. SoapUI/Any REST Client

##Technologies Used


1. Spring Boot - https://projects.spring.io/spring-boot/
2. Amazon DynamoDB - https://aws.amazon.com/dynamodb/
3. Maven - https://maven.apache.org/

##Instructions to Setup locally

1. Clone The Repository from Git Hub to a local workstation -- git clone https://github.com/pnamburi/myRetail-RESTFul-service.git
2. Open Command Prompt/Terminal and Change Directory Location to the project directory -- cd <PATH>/myRetail-RESTFul-service
3. Build the project using Maven - mvn clean install
3. Setup and run Local DynamoDB database -- mvn exec:java -Dexec.mainClass="com.myretail.product.config.SetupLocalDynamoDB" -Dexec.classpathScope="test" -Psetup-db
4. Note: The DinamoDB service keep printing the message "18:06:00.431 [pool-4-thread-3] DEBUG com.amazonaws.services.dynamodbv2.local.shared.access.sqlite.SQLiteDBAccess - SELECT TableName, TableInfo FROM dm;", just ignore that and keep the service running
5. Open another Command Prompt/Terminal and Change Directory Location to the project directory -- cd <WORKSTATION_PATH>/myRetail-RESTFul-service
6. Run the Spring boot app with the jar create from Maven install in Step 3 --  java -jar -Dspring.profiles.active=local target/myRetail-RESTFul-service-1.0.0-SNAPSHOT.jar

##Instructions to run in production

1. Setup Amazon DunamoDB credentials as described in the developer huide https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/CodeSamples.Java.html#CodeSamples.Java.Credentials 
2. Copy/deploy the release build project jar to the production servers
3. Run the Spring boot application from the jar create  --  java -jar -Dspring.profiles.active=prod <JAR_LOCATION>/myRetail-RESTFul-service-1.0.0-RELEASE.jar

##Sample Requests and Responses with curl

 
## GET Method:
 
### Request:
 
 curl -X GET --header 'Accept: application/json' 'http://localhost:8080/products/13860428'
 
### Response:
 
 {"id":13860428,"name":"The Big Lebowski (Blu-ray)","current_price":{"value":17.44,"currency_code":"USD"}}
 

## PUT Method:

###Request:

curl -X PUT --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 14.99,"currency_code":"USD"}} \ 
  ' 'http://localhost:8080/products/'
  
###Response:

Empty Response with HTTP 200 success code
 


 