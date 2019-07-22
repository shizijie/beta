#pull base images
FROM openjdk:8-jdk-alpine

MAINTAINER xl

ENV APP_NAME=beta-auth
ENV LC_LANG=en_US.UTF-8

#ENV path=
#ENV java_home

ENV JAVA_OPTS=""
ENV APP_OPTS=""

WORKDIR /usr/local/${APP_NAME}

COPY beta-auth/build/libs/${APP_NAME}.jar ./
RUN chmod -Rf 755 /usr/local/${APP_NAME}/

ENTRYPOINT java ${JAVA_OPTS} -jar ${APP_NAME}.jar ${APP_OPTS} --server.port=80

EXPOSE 80