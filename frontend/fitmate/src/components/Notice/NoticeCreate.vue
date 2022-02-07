<template>
    <b-container class="bv-example-row mt-3">
        <h2>공지사항 등록</h2>
        <b-form-group
            id="title-group"
            label="제목:"
            label-for="title"
            description=""
        >
            <b-form-input
                id="title"
                ref="title"
                v-model="title"
                type="text"
                placeholder="제목 입력..."
            />
        </b-form-group>
    
        <b-form-group id="content-group" label="내용:" label-for="content">
            <b-form-textarea
                id="content"
                ref="content"
                v-model="content"
                placeholder="내용 입력..."
                rows="10"
                max-rows="15"
            >
            </b-form-textarea>
        </b-form-group>
    
        <b-button 
            variant="primary"
            class="m-1 float-right"
            @click="checkValue"
        >등록</b-button>
    
    </b-container>

</template>

<script>
import axios from "@/module/axios.js";
import { mapState } from 'vuex';

const memberStore = "memberStore";

export default {
    name: "NoticeCreate",

    data() {
        return {
            id : "",
            title : "",
            content: "",
            writer : "",
            createdAt : ""
        }
    },

    computed: {
        ...mapState(memberStore, ["memberInfo"]),
    },
    
    methods: {
        // 제목 내용 작성에 이상없는지 확인
        checkValue() {
            // 체크 할 부분


            // 데이터 전송
            this.registNotice();

            // 페이지 이동
            this.moveList();
        },
    
        // 등록 요청 시 보낼 데이터
        registNotice() {
            const noticeInfo = {
                "id": 0,
                "title": this.title,
                "content": this.content,
                "writer": this.memberInfo.nickname,
                "createdAt": "",
            };

            axios.post("/api/v1/notice", noticeInfo)
            .then(() => {
                alert("등록완료!");
                this.moveList();
            })
            .catch((err) => {
                alert("등록실패!");
                console.log(err)
            })
        },
    
        // List 페이지로 이동
        moveList() {
            console.log("리스트로 이동");
            this.$router.push({ name: "NoticeList" }).catch(()=>{});
        },
    },
}
</script>