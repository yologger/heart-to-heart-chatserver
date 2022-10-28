#!/usr/bin/env bash

## 개발 환경인 경우
echo " - 개발 환경입니다."
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

IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})
echo " - PID of idle port: $IDLE_PID"

if [ -z ${IDLE_PID} ]
then
  echo " - No running application."
else
  echo " - Application is already running."
  echo " - kill application with pid: $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi