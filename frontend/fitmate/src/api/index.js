import axios from "axios";
import { FITMATE_BASE_URL, KAKAO_ADMIN_KEY } from "@/config";

function apiInstance() {
    const instance = axios.create({
        baseURL: FITMATE_BASE_URL,
        headers: {
            "Content-type" : "application/json; charset=UTF-8",
        }
    });
    return instance;
}

function payInstance() {
    const kakao = axios.create({
        // baseURL: "https://kapi.kakao.com",
        headers: {
            "Authorization" : `KakaoAK ${KAKAO_ADMIN_KEY}`,
            "Content-type" : "application/x-www-form-urlencoded;charset=utf-8",
        },
    });
    return kakao;
}

export { apiInstance, payInstance };