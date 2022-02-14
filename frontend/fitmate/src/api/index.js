import axios from "axios";
import { FITMATE_BASE_URL } from "@/config";

function apiInstance() {
    const instance = axios.create({
        baseURL: FITMATE_BASE_URL,
        headers: {
            "Content-type" : "application/json; charset=UTF-8",
        }
    });
    return instance;
}

function apiImgInstance() {
    const instance = axios.create({
        baseURL: FITMATE_BASE_URL,
        headers: {
            "Authorization" : `Bearer ${localStorage.getItem('accessToken')}`,
            "Content-Type" : "multipart/form-data",
        }
    });
    return instance;
}
export { apiInstance, apiImgInstance };