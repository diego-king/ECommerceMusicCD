# CSI 5380 - Order Process Service

**Project Setup**
1. Import as Maven project
2. Requires JDK1.8+ to build and run.
3. To start application, create a new Maven run config and enter in goals "spring-boot:run".

**Database Setup**
1. Run resources/database/create_database.sql
2. Run resources/database/insert_data.sql
3. In application-dev.properties file, change following parameters based on your DB settings.<br />
	1) spring.datasource.username=<Your DB username> <br />
	2) spring.datasource.password=<Your DB password> <br />
	
**Swagger UI available at:** https://localhost:8444/swagger-ui.html
