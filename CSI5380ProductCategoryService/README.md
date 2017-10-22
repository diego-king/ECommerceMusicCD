# CSI 5380 - Product Category Service

**Project Setup**
1. Import as Maven project
2. Requires JDK1.8+ to build and run.
3. To start application, create a new Maven run config and enter in goals "spring-boot:run".

**Database Setup**
1. Run resources/database/create_database.sql
2. Run resources/database/insert_data.sql
3. In application-dev.properties file, change following parameters based on your DB settings
	spring.datasource.username=<Your DB username>
	spring.datasource.password=<Your DB password>

**Swagger UI available at:** https://localhost:8445/swagger-ui.html
