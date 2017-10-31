# CSI 5380 - Order Process Service
###### Created By: Kenny Byrd

## 1. Project Setup
1. Import as Maven project
2. Requires JDK1.8+ to build and run.
3. To start application, create a new Maven run config and enter in goals 

```
spring-boot:run
```

Note: If the application fails to start because the port is already in use by another application
change the port # in application-dev.properties. To do this, modify the following properties:

```
server.http.port=NEW_HTTP_PORT
server.port=NEW_HTTPS_PORT
```

### 2. Database Setup
1. Run resources/database/create_database.sql using MySQL Workbench.
2. Run resources/database/insert_data.sql using MySQL Workbench.
3. In resources/application-dev.properties file, change following parameters based on your DB settings:
```
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
```

### 3. Swagger Spring REST UI
1. Running on: https://localhost:8444/swagger-ui.html
2. Note: Port 8444 will depend on the server.port property you have set in the
application-dev.properties file.

### 4. Spring REST API Test Classes
- Location: src/test/java in the "ca.edu.uottawa.csi5380.api" package.
- AccountRestControllerTest contains JUnit tests for the AccountRestController.
- OrderRestControllerTest contains JUnit tests for the OrderRestController.
- To run tests, create a new Maven run config and enter in goals 
```
mvn clean test 
```
- Optionally: 
1. In eclipse or IntelliJ, right click -> Run As -> JUnit or create a new JUnit run config.
