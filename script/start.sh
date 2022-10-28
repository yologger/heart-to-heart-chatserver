#!/usr/bin/env bash

RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

if [ ${RESPONSE_CODE} -ge 400 ] # 400 ~ 500 에러
then
    CURRENT_PROFILE=alpha2
else
    CURRENT_PROFILE=$(curl -s http://localhost/profile)
fi

if [ ${CURRENT_PROFILE} == alpha1 ]
then
  IDLE_PROFILE=alpha2
else
  IDLE_PROFILE=alpha1
fi

echo " - IDLE PROFILE: ${IDLE_PROFILE}"

if [ "${IDLE_PROFILE}" == alpha1 ]; then
  IDLE_PORT="8081"
else
  IDLE_PORT="8082"
fi

echo " - IDLE PORT: $IDLE_PORT"

REPOSITORY=/home/ec2-user/app/heart-to-heart-chatserver
PROJECT_NAME=heart-to-heart-chatserver

cp $REPOSITORY/zip/build/libs/*.jar $REPOSITORY

JAR_NAME=$(ls -tr $REPOSITORY/ | grep jar | tail -n 1)

echo " - JAR_NAME: ${JAR_NAME}"

echo " - JAR 권한 부여"
chmod +x $REPOSITORY/$JAR_NAME

nohup java -Dspring.config.location=/home/ec2-user/app/heart-to-heart-chatserver/application-$IDLE_PROFILE.yml -Dspring.profiles.active=$IDLE_PROFILE -jar $REPOSITORY/$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
echo " - 앱 실행 완료"