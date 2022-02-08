import { apiInstance } from "./index.js";

const api = apiInstance();

async function reservationList(nickname, success, fail) {
    await api.get(`/api/v1/portfolio/${nickname}/reservations`).then(success).catch(fail);
}

async function reservationDetailList(nickname, success, fail) {
    await api.get(`api/v1/portfolio/${nickname}/reservations/all`).then(success).catch(fail);
}

export { reservationList, reservationDetailList };