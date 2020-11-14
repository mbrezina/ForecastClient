#!/usr/bin/env bash


docker tag forecast-client-java:1.0.0 martiik/forecast-client-java:1.0.0

#docker tag da-sporteezone-java:1.0.3 martiik/da-sporteezone-java:1.0.3

#docker tag da-projekt-do-deploy-java:1.0.0 dolejs/da-projekt-do-deploy-java:1.0.0
##docker tag da-projekt-do-deploy-python:1.0.0 dolejs/da-projekt-do-deploy-python:1.0.0

###docker push martiik/da-sporteezone-java:tagname


docker push martiik/forecast-client-java:1.0.0
##docker push martiik/da-sporteezone-java:1.0.3
###docker push dolejs/da-projekt-do-deploy-python:1.0.0
