FROM openjdk:11-jre-slim

MAINTAINER shubhamwanne <shubhamwanneaws@gmail.com>

ENV ENV_NAME local
ENV BOOTAPP_JAVA_OPTS -Xms512m -Xmx512m
ENV LOG_PATH "/logs"
ENV BOOTAPP_USR="root" BOOTAPP_GROUP="root" BOOTAPP_PATH="/spring-eureka-server-0.0.1-SNAPSHOT.jar"
ENV SERVER_PORT 8761

EXPOSE $SERVER_PORT

COPY custom_app.sh /custom_app.sh
RUN chmod 555 /custom_app.sh

USER root

COPY target/*.jar $BOOTAPP_PATH
RUN chmod 555 $BOOTAPP_PATH && touch $BOOTAPP_PATH

RUN mkdir $LOG_PATH
RUN chmod 777 $LOG_PATH && touch $LOG_PATH

USER $BOOTAPP_USR

ENTRYPOINT ["/custom_app.sh"]