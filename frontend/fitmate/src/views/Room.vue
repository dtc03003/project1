<template>
    <div class="room">
        <p id="fontid">{{title}}</p>
        <div class="row">
            <div class="col-12 col-xl-9">
                <conference
                    :me="me"
                    :room="room"
                />
                <!-- 여기 WebRTC들어가는 부분 -->
            </div>
            <div id="app_chat_list" class="col-12 col-xl-3 msgbox">
                <div class="row"> </div>
                <p id="firsttalk">-첫 대화시작-</p>
                <button class="downbtn" @click="gotoDown()">⏬</button>
                <div v-for="(m, idx) in msg" :key="idx">
                    <div v-bind:class="m.style">
                        <h5 class="mt-3 mb-0" style="color:black; font-size:1.7rem">
                            {{m.senderNickname}}
                        </h5>
                        <div class="msgwidth">
                            {{m.content}}
                        </div>
                    </div>
                </div>
                
            </div>  
            <div class="row rowsendform" >
                <div class="col-xl-9"></div>
                <div class="col-12 col-xl-3 sendform" >
                    <div class="col-8">
                        <b-form-input id="fontform" style="border: 0px; width: 100%" type="text" v-model="content" @keyup.enter="sendMessage()" placeholder="메세지 입력"></b-form-input>
                    </div>
                    <div class="col-4">
                        <b-button id="fontform" class="sendbtn" @click="sendMessage()">SEND</b-button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>

    import axios from 'axios'
    import Stomp from 'webstomp-client'
    import SockJS from 'sockjs-client'
    import {mapGetters, mapActions} from 'vuex'
    import Conference from '@/components/Room/Conference.vue'
    import Swal from 'sweetalert2'
    const memberStore = "memberStore";
    var bottom_flag = true;
    export default {
  components: { Conference },
        name: "Room",
        data: () => {
            return {
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
                accessCode: "",
                sendat: "",
            }
        },

        created() {
            this.getInfo();
            this.id = this.me.id;
            this.nickname = this.me.nickname;
            this.hostname = this.$route.params.hostname;
            if(this.me.nickname!=this.hostname) this.accessCode = prompt('인증키를 입력하세요', '인증키 입력');
            this.getRoomInfo();

        },
        computed: {
            ...mapGetters(memberStore, ["checkMemberInfo"])
        },
        methods: {
            ...mapActions(memberStore, ["signInMemberInfo"]),
            getInfo() { //사용자 정보 가져오기
                if (this.checkMemberInfo) 
                    this.me = this.checkMemberInfo;
                else 
                    this.importInfo(localStorage.getItem("accessToken"));
                }
            ,
            async importInfo(accessToken) { //토큰 이용
                await this.signInMemberInfo(accessToken);
                this.me = this.checkMemberInfo;
            },
            getRoomInfo() {
                axios({
                    method: 'get',
                    url: `/api/v1/chat/room/${this.hostname}?accessCode=${this.accessCode}`,
                    baseURL: 'https://192.168.0.5:8443',
                    headers: {
                        "Content-type": "application/json; charset=UTF-8",
                        "Authorization": `Bearer ${localStorage.getItem('accessToken')}`
                    }
                }).then(res => {
                    this.room = res.data
                    this.host = this.room.host;
                    this.roomid = this.room.id;
                    this.title = this.room.title;
                    this.getMessages();
                }, err => {
                    console.log(err)
                    this
                        .$router
                        .push({name: "Home"});
                });
            },
            getMessages() {
                // 채팅방 내용 불러오기
                axios(
                    {method: 'get', url: `/api/v1/chat/room/${this.roomid}/messages/?page="${this.idx}`, baseURL: 'https://192.168.0.5:8443/'}
                ).then(res => {
                    this.msg = []
                    for (let i = res.data.length - 1; i > -1; i--) {
                        let m = {
                            'senderNickname': res
                                .data[i]
                                .sender
                                .nickname,
                            'content': res
                                .data[i]
                                .content,
                            'style': res
                                .data[i]
                                .sender
                                .id == this.id
                                    ? 'myMsg'
                                    : 'otherMsg'
                        }
                        this
                            .msg
                            .push(m)
                    }
                    this.connectSocket();
                }, err => {
                    console.log(err)
                    Swal.fire({
                        icon: 'error',
                        title: '새로고침해주세요!',
                        confirmButtonColor: '#7e7fb9',
                        confirmButtonText: "확인",
                    })
                });
            },
            connectSocket() {
                // socket 연결
                let socket = new SockJS('https://localhost:8443/ws')
                this.stompClient = Stomp.over(socket)
                this
                    .stompClient
                    .connect({}, frame => {
                        console.log("success", frame)
                        this
                            .stompClient
                            .subscribe("/sub/" + this.roomid, res => {
                                let jsonBody = JSON.parse(res.body)
                                let m = {
                                    'senderNickname': jsonBody.sender.nickname,
                                    'content': jsonBody.content,
                                    'style': jsonBody.sender.id == this.id
                                        ? 'myMsg'
                                        : 'otherMsg'
                                }
                                this
                                    .msg
                                    .push(m)
                            })
                    }, err => {
                        console.log("fail", err)
                    })
            },
            sendMessage() {
                if (this.content.trim() != '' && this.stompClient != null) {
                    let chatMessage = {
                        'content': this.content,
                        'chatRoomId': this.roomid,
                        'sender': this.id
                    }
                    this
                        .stompClient
                        .send("/pub/message", JSON.stringify(chatMessage), {})
                    this.content = ''
                }
            },
            gotoDown() {
                var objDiv = document.getElementById("app_chat_list");
                if(bottom_flag){
                    objDiv.scrollTop = objDiv.scrollHeight;
                }
            }
        },
        updated: function() {
            var objDiv = document.getElementById("app_chat_list");
            if(this.msg.slice(-1)[0].style == 'myMsg'){
                if(bottom_flag){
                    objDiv.scrollTop = objDiv.scrollHeight;
                }
            }
        },
    };
</script>
<style scoped="scoped">
@media screen and (max-width:1200px) {
    .msgbox {
        position: relative;
        border-radius: 10px;
        background: #f8f8ff;
        height: 500px; 
        overflow-y: scroll; 
    }
}
@media screen and (min-width:1200px) {
    .msgbox {
        position: relative;
        border-radius: 10px;
        background: #f8f8ff;
        height: 700px; 
        overflow-y: scroll; 
    }
}
.myMsg {
    text-align: -webkit-right !important;
    color: white;
    font-size: 1.2rem;
    font-family: 'GangwonEdu_OTFBoldA';
}
.otherMsg {
    text-align: -webkit-left !important;
    color: white;
    font-size: 1.2rem;
    font-family: 'GangwonEdu_OTFBoldA';
}
.room {
    margin: 2rem;
    max-height: 800px;
}

.msgbox::-webkit-scrollbar {
    width: 10px;
}
.msgbox::-webkit-scrollbar-thumb {
    background-color: #7e7fb9;
    border-radius: 10px;
    background-clip: padding-box;
    border: 2px solid transparent;
}
.msgbox::-webkit-scrollbar-track {
    background-color: white;
}
.msgwidth {
    max-width: 70%; 
    width: fit-content;
    text-align: left;
    padding: 7px;
    white-space:pre-line;
    word-break:break-all;
    background-color: #7e7fb9;
    border-radius: 9px;
}
.sendbtn {
    background-color: #7e7fb9;
}
.sendform {
    /* position: absolute; */
    /* bottom: 0px; */
    /* width: 100%; */
    display: flex;
    border-radius: 10px;
    background-color: white;
    text-align: right;
}
.downbtn{
    position: absolute;
    font-size: 20pt;
    top: 8px;
    right: 15px;
}
#fontform {font-size:1.2rem; font-family: 'GangwonEdu_OTFBoldA';}
p#fontid {font-size: 40pt; margin-left:35px; font-family: 'GangwonEdu_OTFBoldA';}
p#firsttalk {font-size: 20pt; text-align: center; font-family: 'GangwonEdu_OTFBoldA';}
.room#wrap {
    min-height: 85vh !important;
    position: relative;
    width: 100%;
}
.rowsendform {
    margin-left: auto;
}
</style>