import { apiInstance, apiImgInstance } from "./index.js";

const api = apiInstance();
const imgapi = apiImgInstance();

//포트폴리오 존재 여부 확인
async function findPortfolio(nickname, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/portfolio/${nickname}`).then(success).catch(fail);
}

//특정 스타일리스트의 리뷰 쓰기
async function writeReview(review, nickname, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.post(`/api/v1/portfolio/${nickname}/review`, JSON.stringify(review)).then(success).catch(fail);
}

//해당 스타일리스트의 페이지별 모든 리뷰 불러오기
async function reviewByPage(info, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/portfolio/${info.nickname}/reviews?page=${info.page}`).then(success).catch(fail);
}

//해당 스타일리스트의 모든 리뷰 불러오기
async function reviewAll(nickname, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/portfolio/${nickname}/reviews/all`).then(success).catch(fail);
}

//해당 스타일리스트의 리뷰 하나씩 불러오기
async function reviewOne(nickname, id, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/portfolio/${nickname}/review/${id}`).then(success).catch(fail);
}

async function uploadImage(formData, success, fail) { //이미지 업로드
    imgapi.post("/api/v1/images", formData).then(success).catch(fail);
}

export { writeReview, reviewByPage, reviewAll, reviewOne, findPortfolio, uploadImage };