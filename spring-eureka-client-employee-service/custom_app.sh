#!/bin/bash

exec java $BOOTAPP_JAVA_OPTS -jar -Dspring.profiles.active=$ENV_NAME $BOOTAPP_PATH --server.port=$SERVER_PORT