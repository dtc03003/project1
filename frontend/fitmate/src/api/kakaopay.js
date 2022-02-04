import { payInstance } from "./index.js";

const api = payInstance();

//카카오페이 결제 요청
async function kakaopay(params, success, fail) {
    console.log("접근?")
    await api.post("https://kapi.kakao.com/v1/payment/ready", JSON.stringify(params)).then(success).catch(fail);
}

export default { kakaopay };
