# ForecastClient

This application is a REST API client for:      https://www.visualcrossing.com/weather/weather-data-services

It shows a nice forecast for 3 days and longterm temperature series for any location in the world. If you login with a Google account you can save up to 3 favourite places and you get the weather forecast for them whenever you log in. The user favourite places are stored in a MySQL database.

When the client is opened fo rthe first time it show the weather forecast for a default location which can bet set up by parameter when starting the Docker container with the application. You can change the default location according to the place where the server with the application is running.

You can run the client with this Docker command - you need to pass the database parameters to the container so the container works with your database:

docker run --restart always -d -p 8080:8080 -e "WEATHER_API_PLACE=<<a default place for the forecast>>" -e "SPRING_DATASOURCE_URL=<<datasource URL>>" \
-e "SPRING_DATASOURCE_USERNAME=<<database login>>" -e "SPRING_DATASOURCE_PASSWORD=<<database password>>" --name java martiik/forecast-client-java:1.0.0

The datasource URL for your local computer would be: jdbc:mysql://localhost:3306/Forecast?serverTimezone=UTC

You should be using MySQL database, if you do not have it installed locally use this command to run it in a Docker container:
docker run --name forecast-client-database -e MYSQL_ROOT_PASSWORD=root-pw -e MYSQL_DATABASE=Sporteezone -e MYSQL_USER=student -e MYSQL_PASSWORD=password -p 3306:3306 mysql

SQL commands to set the database up:

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
Java 11
Spring Boot
Spring Data JPA
Spring Security OAUTH2 client
Thymeleaf template engine
