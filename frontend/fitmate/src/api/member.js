import { apiInstance } from "./index.js";

const api = apiInstance();

//로그인
async function signin(member, success, fail) {
    await api.post("/auth/login", JSON.stringify(member)).then(success).catch(fail);
}

//로그인한 사용자 정보
async function getMemberInfo(accessToken, suceess, fail) {
    api.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
    await api.get("/api/v1/member/me").then(suceess).catch(fail);
}

//토큰 재발급(access Token 만료시)
async function reissue(info, success, fail) {
    await api.post("/auth/reissue", JSON.stringify(info)).then(success).catch(fail);
}

//카카오 간편로그인
async function sendKakao(token, success, fail) {
    await api.post("/auth/login/kakao", JSON.stringify(token)).then(success).catch(fail);
}


export { signin, getMemberInfo, reissue, sendKakao };