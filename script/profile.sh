# 쉬고있는 Profile 찾기
function find_idle_profile()
{
    echo "2: $DEPLOYMENT_GROUP_NAME"
    ## 개발 환경일 경우
    echo "- 개발 환경에 배포를 시도합니다."
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

    echo "${IDLE_PROFILE}"
}

function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ "${IDLE_PROFILE}" == alpha1 ]; then
      echo "8081"
    else
      echo "8082"
    fi
}