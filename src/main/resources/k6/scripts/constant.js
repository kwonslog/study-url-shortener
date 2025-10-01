import http from 'k6/http';                              // k6의 HTTP 모듈 임포트

export const options = {                                 // k6 실행 옵션(시나리오 정의 등)
    scenarios: {                                           // 여러 시나리오를 병렬/순차로 구성하는 블록

        step1: {                                             // 1단계 시나리오 이름
            executor: 'constant-arrival-rate',                 // '고정 도착률' 실행기: 초당 일정 개수의 요청을 생성
            rate: 1600,                                        // 목표 도착률(초당 요청 수, timeUnit과 함께 해석)
            timeUnit: '1s',                                    // rate의 시간 단위(여기선 1초 기준으로 1600 req)
            duration: '1m',                                    // 이 시나리오를 1분 동안 유지
            preAllocatedVUs: 1600,                             // 사전 할당 VU 수(부하 급증 대비 미리 확보)
            maxVUs: 3000,                                      // 필요 시 확장 가능한 VU 상한(스파이크/지연 대응)
            gracefulStop: '10s',                               // 시나리오 종료 시 진행 중 반복을 마무리할 유예 시간
        },

        // step2: {                                             // 2단계 시나리오
        //     executor: 'constant-arrival-rate',                 // 동일 실행기(고정 도착률)
        //     startTime: '1m',                                   // 전체 테스트 시작 후 1분 시점에 시작(= step1 바로 다음)
        //     rate: 1800,                                        // 초당 1800 요청 목표
        //     timeUnit: '1s',                                    // 1초 기준
        //     duration: '1m',                                    // 1분 유지
        //     preAllocatedVUs: 1600,                             // 사전 할당 VU(전역 풀을 공유한다는 느낌으로 보면 됨)
        //     maxVUs: 3000,                                      // VU 상한
        //     gracefulStop: '10s',                               // 종료 유예
        // },
        //
        // step3: {                                             // 3단계
        //     executor: 'constant-arrival-rate',
        //     startTime: '2m',                                   // 2분 시점 시작
        //     rate: 2000,                                        // 초당 2000 요청
        //     timeUnit: '1s',
        //     duration: '1m',
        //     preAllocatedVUs: 1600,
        //     maxVUs: 3000,
        //     gracefulStop: '10s',
        // },
        //
        // step4: {                                             // 4단계
        //     executor: 'constant-arrival-rate',
        //     startTime: '3m',                                   // 3분 시점 시작
        //     rate: 2200,
        //     timeUnit: '1s',
        //     duration: '1m',
        //     preAllocatedVUs: 1600,
        //     maxVUs: 3000,
        //     gracefulStop: '10s',
        // },
        //
        // step5: {                                             // 5단계
        //     executor: 'constant-arrival-rate',
        //     startTime: '4m',                                   // 4분 시점 시작
        //     rate: 2400,
        //     timeUnit: '1s',
        //     duration: '1m',
        //     preAllocatedVUs: 1600,
        //     maxVUs: 3000,
        //     gracefulStop: '10s',
        // },
    },

    // ★ SLO 위반 시 즉시 중단
    thresholds: {
        // 지연 SLO
        'http_req_duration{expected_response:true}': [
            {
                threshold: 'p(95) < 1000',
                abortOnFail: true,
                // 워밍업 노이즈가 임계 판정에 섞이지 않도록 최초 평가를 지연
                delayAbortEval: '45s',
            },
        ],

        // 에러율 SLO: < 0.5%
        http_req_failed: [
            {
                threshold: 'rate < 0.005',
                abortOnFail: false,
                delayAbortEval: '30s',
            },
        ],

        // 로드 제너레이터/시스템 포화 신호
        'dropped_iterations{scenario:step1}': [
            {
                threshold: 'rate == 0',
                abortOnFail: false,
                delayAbortEval: '30s',
            },
        ],
    },
};

export default function () {                             // 각 VU(가상 사용자)가 수행하는 테스트 본문
    http.get('http://host.docker.internal:8080/healthz', { // 대상 엔드포인트로 GET 요청
        tags: { expected_response: 'true' },                 // 성공 응답(2xx/3xx)으로 기대한다는 태그(임계치 필터링에 씀)
    });
}
