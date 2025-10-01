import http from 'k6/http';

export const options = {
    scenarios: {
        open_loop_test: {
            executor: 'constant-arrival-rate',
            rate: 200,               // 초당 200 RPS
            timeUnit: '1s',          // rate 단위
            duration: '1m',          // 총 1분 실행
            preAllocatedVUs: 500,    // 미리 띄울 가상 유저 수
            maxVUs: 2000,            // 최대 가상 유저 수
        },
    },
};

export default function () {
    http.get('http://host.docker.internal:8080/healthz');
}