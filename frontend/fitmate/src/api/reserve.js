import { apiInstance } from "./index.js";

const api = apiInstance();
//예약 내역 가져오기(날짜, 시간)
async function reservationList(nickname, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/portfolio/${nickname}/reservations/schedule`).then(success).catch(fail);
}

//예약 내역 가져오기(디테일하게)
async function reservationDetailList(nickname, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/portfolio/${nickname}/reservations`).then(success).catch(fail);
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

//예약 취소하기
async function deleteReservation(id, success, fail) {
    await api.delete(`/api/v1/reservation/${id}`).then(success).catch(fail);
}

async function putComplete(id, success, fail) {
    await api.put(`/api/v1/reservation/complete/${id}`).then(success).catch(fail);
}

async function putCancel(id, success, fail) {
    await api.put(`/api/v1/reservation/cancel/${id}`).then(success).catch(fail);
}
//-----------------------------------------------------------------------------------------------------------------

//카카오페이 결제 요청
async function readypay(params, success, fail) {
    await api.post("/api/v1/payment/ready", JSON.stringify(params)).then(success).catch(fail);
}

//결제 완료(결제 내역에 push)
async function finpay(reservation, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.post(`/api/v1/payment`, JSON.stringify(reservation)).then(success).catch(fail);
}

//나의 결제 내역 가져오기
async function mypayment(success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/payments/me`).then(success).catch(fail);
}

//결제 내역 찾기
async function findPaymentById(id, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.put(`/api/v1/payment/${id}`).then(success).catch(fail);
}

export { reservationList, reservationDetailList, reservationNowList, enrollReservation, deleteReservation,
        readypay, finpay, mypayment, findPaymentById, putComplete, putCancel };