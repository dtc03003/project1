import {roomByHostName, messagesByRoomId} from '@/api/room.js'
const roomStore ={
    namespaced: true,
    state:{
        hostname: "",
        id: -1,
        title: '',
        roomid: -1,
        idx: 0,
        msg: [],
        content: "",
        stompClient: null,
        host: {},
        me: {},
        room: {},
        accessCode: "스타일리스트508f6f16-c10f-4e0f-ac6b-c4509441e22c",
        sendat: "",
    },
    getters: {
        getHostName : (state) => state.hostname,
        getId : (state) => state.id,
        getTitle : (state) => state.title,
        getRoomId : (state) => state.roomid,
        getIdx : (state) => state.idx,
        getMsg : (state) => state.msg,
        getContent : (state) => state.content,
        getStompClient: (state) => state.stompClient,
        getHost: (state) => state.host,
        getMe: (state) => state.me,
        getRoom: (state) => state.room,
        getAccessCode: (state) => state.accessCode,
        getSendat: (state) => state.sendat,
    },
    mutations: {
        SET_HOST_NAME : (state,hostname) => state.hostname = hostname,
        SET_ID : (state, id) => state.id = id,
        SET_TITLE : (state, title) => state.title = title,
        SET_ROOM_ID : (state, roomid) => state.roomid = roomid,
        SET_IDX : (state, idx) => state.idx = idx,
        SET_MSG : (state, msg) => state.msg = msg,
        SET_CONTENT : (state, content) => state.content = content,
        SET_STOMPCLIENT: (state, stompClient) => state.stompClient = stompClient,
        SET_HOST: (state,host) => state.host = host,
        SET_ME: (state,me) => state.me = me,
        SET_ROOM: (state,room) => state.room = room,
        SET_ACCESS_CODE: (state,accessCode) => state.accessCode = accessCode,
        SET_SENDAT: (state,sendat) => state.sendat =sendat,
    },
    actions : {
        async findRoomByHostName({commit}, entryInfo){
            console.log(entryInfo);
            await roomByHostName(entryInfo.hostname, entryInfo.accessCode ,(response)=>{
                if(response.state==200){
                    commit("SET_ROOM", true);
                }
            },
            ()=>{this.$router.push({name: "Home"})}
            );
        },
        async findAllMessagesByRoomId({commit}, roomPage){
            await messagesByRoomId(roomPage.id, roomPage.idx ,(response)=>{
                if(response.state==200){
                    commit("SET_MSG", response.data);
                }
            },()=>{this.$router.push({name: "Home"})}
            );
        }
    }
}

export default roomStore;


// async function findRoomByHostName(hostname, accessCode, success, fail){
//     api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
//     await api.get(`/api/v1/chat/room/${hostname}?accessCode=${accessCode}`).then(success).catch(fail);
// }
// async function findAllMessagesByRoomId(roomid, idx, success, fail){
//     api.defaults.headers.common["Authorization"] = `Bearer ${localStorage.getItem('accessToken')}`;
//     await api.get(`/api/v1/chat/room/${roomid}/messages/?page="${idx}`).then(success).catch(fail);
// }