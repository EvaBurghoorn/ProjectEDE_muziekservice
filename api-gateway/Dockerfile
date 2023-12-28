FROM openjdk:17-jdk-alpine
EXPOSE 8084
ARG GOOGLE_CLIENTSECRET
ARG GOOGLE_CLIENTID
ARG GOOGLE_CLIENTSECRET=$GOOGLE_CLIENTSECRET
ARG GOOGLE_CLIENTID=$GOOGLE_CLIENTID

ARG USER_SERVICE_BASEURL="https://user-service-evaburghoorn.cloud.okteto.net/"
ARG PLAYLIST_SERVICE_BASEURL="https://playlist-service-evaburghoorn.cloud.okteto.net/"
ARG RATING_SERVICE_BASEURL="https://rating-service-evaburghoorn.cloud.okteto.net/"
ARG MUSICPODCAST_SERVICE_BASEURL="https://musicpodcast-service-evaburghoorn.cloud.okteto.net/"


ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]