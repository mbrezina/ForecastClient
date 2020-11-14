#!/usr/bin/env bash

mvn clean package
docker build -t forecast-client-java:1.0.0 .
