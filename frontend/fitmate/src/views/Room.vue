<template>
    <div class="room">
        <h1>{{title}}</h1>
        <hr/>
        <div v-for="(m, idx) in msg" :key="idx">
            <div v-bind:class="m.style">
                <h5 style="margin:3px">
                    {{m.senderNickname}}
                </h5>
                {{m.content}}
            </div>
        </div>
        <hr/>
        <input type="text" v-model="content" placeholder="보낼 메세지" size="100"/>
        <button @click="sendMessage()">
            SEND</button>
    </div>
</template>

<script>

    import axios from 'axios'
    import Stomp from 'webstomp-client'
    import SockJS from 'sockjs-client'
    import {mapGetters, mapActions} from 'vuex'
    const memberStore = "memberStore";
    export default {
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
                accessCode: ""
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
                    baseURL: 'https://localhost:8443/',
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
                    {method: 'get', url: `/api/v1/chat/room/${this.roomid}/messages/?page="${this.idx}`, baseURL: 'https://localhost:8443/'}
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
                    alert("error : 새로고침하세요")
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
            }
        }
    };
</script>
<style scoped="scoped">
    .myMsg {
        text-align: right;
        color: gray;
    }
    .otherMsg {
        text-align: left;
    }
</style>