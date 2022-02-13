import { apiInstance, apiImgInstance } from "./index.js";

const api = apiInstance();
const imgapi = apiImgInstance();

//포트폴리오 존재 여부 확인
async function findPortfolio(nickname, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/portfolio/${nickname}`).then(success).catch(fail);
}

//특정 스타일리스트의 리뷰 쓰기
async function writeReview(nickname, review, success, fail) {
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
async function reviewOne(id, success, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/review/${id}`).then(success).catch(fail);
}

//이미지 업로드
async function uploadImage(formData, success, fail) {
    await imgapi.post("/api/v1/images", formData).then(success).catch(fail);
}

//각자의 아이디(db 저장 순서 번호)로 자신의 리뷰 조회
async function findReviewsById(id, success, fail) {
    await api.get(`/api/v1/members/${id}/reviews`).then(success).catch(fail);
}

//해당 스타일리스트 팔로워수 구하기(review 등록 시 갱신 필요해서)
async function findCountOfFollower(nickname, success, fail) {
    await api.get(`/api/v1/countOfFollwer/${nickname}`).then(success).catch(fail);
}

//해당 스타일리스트 평점 갱신
async function updateRatingOfFollwer(info, success, fail) {
    await api.put(`/api/v1/rate/update`, JSON.stringify(info)).then(success).catch(fail);
}

export { writeReview, reviewByPage, reviewAll, reviewOne, findPortfolio, uploadImage, findReviewsById,
    findCountOfFollower, updateRatingOfFollwer};