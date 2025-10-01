import http from 'k6/http';

export const options = {
    scenarios: {
        step_rps: {
            executor: 'ramping-arrival-rate',
            startRate: 200,
            timeUnit: '1s',
            preAllocatedVUs: 1600,
            maxVUs: 3000,
            stages: [
                { target: 1600, duration: '1m' },
                { target: 1800, duration: '1m' },
                { target: 2000, duration: '1m' },
                { target: 2200, duration: '1m' },
                { target: 2400, duration: '1m' },
            ],
        },
    },
};


export default function () {
    http.get('http://host.docker.internal:8080/healthz');
}