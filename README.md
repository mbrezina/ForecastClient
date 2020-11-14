# ForecastClient

This application is a REST API client for:      https://www.visualcrossing.com/weather/weather-data-services

It shows a nice icon forecast for 3 days for any location in the world and longterm temperature series for the place. If you login with a Google account you can save up to 3 favourite places in the world and it presents weather forecast for them.

You can run the client with this Docker command - you need to pass the database parameters to the container so the container works with your database:

docker run --restart always -d -p 8080:8080  \
-e "SPRING_DATASOURCE_URL=<<datasource URL>>" \
-e "SPRING_DATASOURCE_USERNAME=<<database login>>" \
-e "SPRING_DATASOURCE_PASSWORD=<<database password>>" \
--name java martiik/forecast-client-java:1.0.0

The datasoure URL for your loccal computer would be: jdbc:mysql://localhost:3306/Forecast?serverTimezone=UTC

You should be using MySQL database:

CREATE DATABASE Forecast
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_czech_ci;

USE Forecast;

CREATE TABLE star_location
     (id INT PRIMARY KEY AUTO_INCREMENT,
     email VARCHAR(50),
     sub VARCHAR(50),
     location1 VARCHAR(50),
     location2 VARCHAR(50),
     location3 VARCHAR(50)
     );

The technical solution:
Spring Boot
Spring Data JPA
Spring Security OAUTH2 client
