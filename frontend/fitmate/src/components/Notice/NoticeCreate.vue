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
                class="mt-1"
            />
        </b-form-group>
    
        <b-form-group id="content-group" label="내용:" label-for="content" class="mt-1">
            <b-form-textarea
                id="content"
                ref="content"
                v-model="content"
                placeholder="내용 입력..."
                rows="10"
                max-rows="15"
                class="mt-1"
            >
            </b-form-textarea>
        </b-form-group>
    
        <b-button 
            class="m-1 float-right"
            @click="checkValue"
            v-bind:disabled="title == '' || content == ''"
            id="gradient-btn" align="right"
        >등록</b-button>
    
    </b-container>

</template>

<script>
import axios from "@/module/axios.js";
import { mapState } from 'vuex';
import Swal from 'sweetalert2'
const memberStore = "memberStore";

export default {
    name: "NoticeCreate",

    data() {
        return {
            id : "",
            title : "",
            content: "",
            writer : "",
            createdAt : "",
        }
    },

    computed: {
        ...mapState(memberStore, ["memberInfo"]),
    },
    
    methods: {

        checkValue() {
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
                this.$store.dispatch("getNoticeList")
                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                        toast.addEventListener('mouseenter', Swal.stopTimer)
                        toast.addEventListener('mouseleave', Swal.resumeTimer)
                    }
                })
                Toast.fire({
                    icon: 'success',
                    title: '공지사항 등록 완료!'
                })

                this.moveList();
            })
            .catch(() => {
                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                        toast.addEventListener('mouseenter', Swal.stopTimer)
                        toast.addEventListener('mouseleave', Swal.resumeTimer)
                    }
                })
                Toast.fire({
                    icon: 'error',
                    title: '등록되지 않았습니다!'
                })
            })

        },
    
        // List 페이지로 이동
        moveList() {
            this.$router.push({ name: "NoticeList" })
            .catch(() => {})
        }
    },
}
</script>

<style scoped>
h2, #title-group, #content-group, ::placeholder { font-family: "SDSamliphopangche_Basic", fantasy; }
#title-group, #content-group {font-size: 1.1rem;}
</style>