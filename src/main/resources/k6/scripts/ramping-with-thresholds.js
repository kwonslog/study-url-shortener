import http from 'k6/http';

export const options = {
    scenarios: {
        step_rps: {
            executor: 'ramping-arrival-rate',
            startRate: 200,
            timeUnit: '1s',
            preAllocatedVUs: 2000,
            maxVUs: 4000,
            stages: [
                { target: 1600, duration: '1m' },
                { target: 1800, duration: '1m' },
                { target: 2000, duration: '1m' },
                { target: 2200, duration: '1m' },
                { target: 2400, duration: '1m' },
            ],
            gracefulStop: '10s'
        },
    },

    // ★ SLO 위반 시 즉시 중단
    thresholds: {
        // 지연 SLO: 1분 창에서 P95 < 800ms
        'http_req_duration{expected_response:true}': [
            {
                threshold: 'p(95) < 800',
                abortOnFail: true,
                // 워밍업 노이즈가 임계 판정에 섞이지 않도록 최초 평가를 지연
                delayAbortEval: '45s',
            },
        ],

        // 에러율 SLO: < 0.5%
        http_req_failed: [
            {
                threshold: 'rate < 0.005',
                abortOnFail: true,
                delayAbortEval: '30s',
            },
        ],

        // 로드 제너레이터/시스템 포화 신호: 중단하지 않고 경고만.
        // dropped_iterations: [
        //     {
        //         threshold: 'rate <= 1',
        //         abortOnFail: false,
        //         delayAbortEval: '30s',
        //     },
        // ],
    },
};

export default function () {
    http.get('http://host.docker.internal:8080/healthz', { timeout: '5s' });
}

// k6 런타임이 자동으로 호출.
export function handleSummary(data) {
    return {
        stdout:
            `\nP95: ${Math.round(data.metrics.http_req_duration.values['p(95)'])} ms` +
            ` | err%: ${(data.metrics.http_req_failed.values.rate * 100).toFixed(3)}%` +
            ` | dropped: ${data.metrics.dropped_iterations.values.count}\n`,
        'summary.json': JSON.stringify(data, null, 2),
    };
}
