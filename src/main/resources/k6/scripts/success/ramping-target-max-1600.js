import http from 'k6/http';

export const options = {
    scenarios: {
        step_rps: {
            executor: 'ramping-arrival-rate',
            startRate: 200,
            timeUnit: '1s',
            preAllocatedVUs: 500,
            maxVUs: 2000,
            stages: [
                { target: 200, duration: '1m' },
                { target: 400, duration: '1m' },
                { target: 800, duration: '1m' },
                { target: 1200, duration: '1m' },
                { target: 1600, duration: '1m' },
            ],
        },
    },
};


export default function () {
    http.get('http://host.docker.internal:8080/healthz');
}