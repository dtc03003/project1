import { apiInstance } from "./index.js";

const api = apiInstance();

//로그인
async function signin(member, success, fail) {
    await api.post("/auth/login", JSON.stringify(member)).then(success).catch(fail);
}

//로그인한 사용자 정보
async function getMemberInfo(accessToken, suceess, fail) {
    api.defaults.headers.common["accessToken"] = accessToken;
    await api.get("/api/v1/member").then(suceess).catch(fail);
}

export { signin, getMemberInfo };