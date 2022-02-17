import { apiInstance } from "./index.js";

const api = apiInstance();

async function roomByHostName(hostname, accessCode, success, fail){
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/chat/room/${hostname}?accessCode=${accessCode}`).then(success).catch(fail);
}
async function messagesByRoomId(roomid, idx, success, fail){
    api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
    await api.get(`/api/v1/chat/room/${roomid}/messages/?page="${idx}`).then(success).catch(fail);
}

export {roomByHostName, messagesByRoomId}

// axios(
//     {method: 'get', url: `/api/v1/chat/room/${this.roomid}/messages/?page="${this.idx}`, baseURL: 'https://localhost:8443/'}
// )
// axios({
//     method: 'get',
//     url: `/api/v1/chat/room/${this.hostname}?accessCode=${this.accessCode}`,
//     baseURL: 'https://localhost:8443/',
//     headers: {
//         "Content-type": "application/json; charset=UTF-8",
//         "Authorization": `Bearer ${localStorage.getItem('accessToken')}`
//     }
// })