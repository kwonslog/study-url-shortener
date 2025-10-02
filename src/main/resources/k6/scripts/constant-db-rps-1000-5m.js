import http from 'k6/http';

export const options = {
    
    // 수집 되는 지표에 포함될 기본 tag 값.
    tags: {
        env: 'dev',
        service: 'zero-logic-api',
        version: '0.0.1'
    },

    scenarios: {
        step1: {
            executor: 'constant-arrival-rate',
            // 요청수(현재 1초당 요청 수)
            rate: 1000,
            // rate 단위(현재 1초)
            timeUnit: '1s',
            // n분간 부하 발생
            duration: '5m',
            // 가상 유저 사전 할당
            preAllocatedVUs: 10,
            // 가상 유저 상한
            maxVUs: 50,
            // 시나리오 종료 시 마무리 시간
            gracefulStop: '10s'
        }
    },

    thresholds: {
        // 성공한 요청을 기준으로 판단
        'http_req_duration{expected_response:true}': [
            {
                threshold: 'p(95) < 800',
                abortOnFail: true,
                // 워밍업 노이즈가 섞이지 않도록 최초 평가를 지연
                delayAbortEval: '30s',
            },
            {
                threshold: 'p(99) < 2000',
                abortOnFail: true,
                delayAbortEval: '30s',
            },
        ],

        // 에러율 SLO: < 0.5%
        http_req_failed: [
            {
                threshold: 'rate < 0.05',
                abortOnFail: false,
                delayAbortEval: '30s',
            },
        ]
    },
};

export default function () {
    http.get('http://host.docker.internal:8080/dbping');
}
