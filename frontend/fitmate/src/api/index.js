import axios from "axios";
import { FITMATE_BASE_URL } from "@/config";

function apiInstance() {
    const instance = axios.create({
        baseURL: FITMATE_BASE_URL,
        headers: {
            "Content-type" : "application/json",
        }
    });
    return instance;
}

export { apiInstance };