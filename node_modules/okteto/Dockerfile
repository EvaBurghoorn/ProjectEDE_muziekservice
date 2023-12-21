FROM node:latest
LABEL maintainer="Anderson Oliveira"
COPY . /www
WORKDIR . /www
RUN npm install
ENTRYPOINT npm start
EXPOSE 3000