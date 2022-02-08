import { apiInstance } from "./index.js";

const api = apiInstance();

//예약 내역 가져오기(날짜, 시간)
async function reservationList(nickname, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/portfolio/${nickname}/reservations/timeList`).then(success).catch(fail);
}

//예약 내역 가져오기(디테일하게)
async function reservationDetailList(nickname, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/portfolio/${nickname}/reservations/all`).then(success).catch(fail);
}

//예약 내역 가져오기(앞으로 존재하는 내역)
async function reservationNowList(nickname, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/portfolio/${nickname}/reservations?state=IN_PROGRESS`).then(success).catch(fail);
}

//예약하기
async function enrollReservation(nickname, info, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.post(`/api/v1/portfolio/${nickname}/reservation`, JSON.stringify(info)).then(success).catch(fail);
}

//카카오페이 결제 요청
async function readypay(params, success, fail) {
    console.log(params);
    await api.post("/api/v1/payment/ready", JSON.stringify(params)).then(success).catch(fail);
}

export { reservationList, reservationDetailList, reservationNowList, enrollReservation, readypay };