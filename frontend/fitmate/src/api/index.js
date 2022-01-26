import axios from "axios";
import {} from "@/config";

function apiInstance() {
    const instance = axios.create({
        headers: {
            "Content-type" : "application/json; charset=utf-8;",
        }
    });
    return instance;
}

export { apiInstance };