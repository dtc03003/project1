import { apiInstance } from "./index.js";

const api = apiInstance();

// carousel 이미지 소스 가져오기
async function findAllCarousel(success, fail) {
  await api.get(`/api/v1/carousel/all`).then(success).catch(fail);
}

// carousel 이미지 소스 저장
async function saveCarousel(imgSrc, success, fail) {
  await api.get(`/api/v1/carousel/save/${imgSrc}`).then(success).catch(fail);
}

// carousel 이미지 소스 삭제
async function deleteCarousel(carouselId, success, fail) {
  await api.get(`/api/v1/carousel/delete/${carouselId}`).then(success).catch(fail);
}

export { findAllCarousel, saveCarousel, deleteCarousel };
