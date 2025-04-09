import http from "k6/http";
import { check } from "k6";

const BASE_URL = __ENV.BASE_URL || "http://localhost:8080";

export const options = {
  stages: [
    { duration: '5s', target: 20 },  // ramp up
    { duration: '10s', target: 20 }, // stay
    { duration: '5s', target: 0 },   // ramp down
  ],
};

export default function () {
  const restaurant = "Rest A";
  const date = new Date();
  date.setDate(date.getDate() + 1); // reserve for tomorrow

  const payload = `restaurant=${restaurant}&date=${date.toISOString().slice(0, 10)}`;

  const res = http.post(`${BASE_URL}/reserve`, payload, {
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    },
  });

  check(res, {
    "status is 200": (r) => r.status === 200,
    "token received": (r) => r.body.includes("token")
  });
}
