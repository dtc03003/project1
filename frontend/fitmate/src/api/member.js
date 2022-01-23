import { apiInstance } from "./index.js";

const api = apiInstance();

//로그인
async function signin(member, success, fail) {
    await api.post("", JSON.stringify(member)).then(success).catch(fail); //추후 swagger 확인 후 작성
}

export { signin };