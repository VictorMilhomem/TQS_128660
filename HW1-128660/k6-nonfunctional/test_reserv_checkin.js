import http from "k6/http";
import { check, sleep } from "k6";

const BASE_URL = __ENV.BASE_URL || "http://localhost:8080";

export const options = {
    stages: [
        { duration: '5s', target: 10 },
        { duration: '10s', target: 10 },
        { duration: '5s', target: 0 },
    ],
};

export default function () {

    const date = new Date();
    date.setDate(date.getDate() + 1); // tomorrow
    const reservationPayload = JSON.stringify({
        restaurant: "Rest A",
        date: date.toISOString().slice(0, 10)
    });

    const createRes = http.post(`${BASE_URL}/api/reservations`, reservationPayload, {
        headers: { "Content-Type": "application/json" }
    });

    check(createRes, {
        "reservation created": (res) => res.status === 200 && res.json("token") !== undefined,
    });

    const token = createRes.json("token");


    const checkinRes = http.post(`${BASE_URL}/api/reservations/${token}/check-in`);

    check(checkinRes, {
        "check-in succeeded": (res) => res.status === 200 && res.body.includes("Checked in"),
    });

    sleep(1); // simulate user delay
}
