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

export { apiInstance };