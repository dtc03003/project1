<template>
    <b-container class="bv-example-row mt-3">

        <b-row class="mb-1">
            <b-col>
                <b-card
                    v-if="Object.keys(qna).length != 0"
                    :header-html="`
                    <h3>${qna.title}</h3>
                    <h5>${qna.writer}</h5>
                    <div>
                        <h6>${qna.createdAt}</h6>
                    </div>
                `"
                    class="mb-2 card"
                    border-variant="dark"
                    no-body
                >
                    <b-card-body class="text-left">
                        <span style="white-space: pre-line">
                            {{ qna.content }}
                        </span>
                    </b-card-body>
                </b-card>

                <!-- 댓글 등록 -->
                <b-form-group id="content-group" label="댓글:" label-for="content" class="form">
                    <b-form-textarea
                        id="content"
                        ref="content"
                        v-model="comment"
                        placeholder="댓글 입력..."
                        rows="5"
                        max-rows="10"
                        class="mt-1"
                    >
                    </b-form-textarea>
                </b-form-group>
                
                <b-button 
                    class="btn m-1 float-right enrollBtn"
                    @click="registComment"
                    v-bind:disabled="comment == ''"
                >등록</b-button>

                <!-- 댓글 목록 -->
                <b-table-simple hover responsive>

                    <b-thead head-variant="dark">
                        <b-tr>
                            <b-th>작성자</b-th>
                            <b-th>댓글</b-th>
                            <b-th>작성일</b-th>
                        </b-tr>
                    </b-thead>

                    <b-tbody>
                        <b-tr v-for="(comments, id) in commentInfos" :key="id" class="form">
                            <b-td>{{comments.writer}} </b-td>
                            <b-td width="700" style="word-break:break-all">{{comments.comment}}</b-td>
                            <b-td>{{comments.createdAt}}</b-td>
                        </b-tr>
                    </b-tbody>

                </b-table-simple>

            </b-col>
        </b-row>

    </b-container>
</template>

<script>
import axios from "@/module/axios.js";
import { mapState } from 'vuex';
import Swal from 'sweetalert2'

const memberStore = "memberStore";

export default {
    data() {
        return{
            id: this.$route.params.id,
            comment: "",
            writer: "",
            createdAt: "",
            qnaId: "",
        }
    },

    computed: {
        ...mapState(memberStore, ["memberInfo"]),

        qna() {
            return this.$store.state.qnaStore.qna;
        },
        commentInfos () {
            return this.$store.state.qnaStore.comments;
        }
    },

    created() {
        this.$store.dispatch("getQnA", { id: this.id })
        this.$store.dispatch("getComments", { id: this.id })
    },

    methods: {
        async registComment() {
            const commentInfo = {
                "id": 0,
                "comment": this.comment,
                "writer": this.memberInfo.nickname,
                "createdAt": "",
                "qnaId": parseInt(this.id)
            };

            // *async, await 을 쓰는 이유
            // 1.  새로운 댓글을 보내는 코드
            await axios.post("/api/v1/comment", commentInfo)

            // 2.  db에 들어간 데이터를 다시 불러 오는 코드
            this.$store.dispatch("getComments", { id: this.id })

            // async, await를 사용하지 않으니 1번이 실행되어서 db에 데이터가 저장되기 전에 
            // 2번이 실행되어 새로운 댓글을 들고오지 못하는 상황이었음
            
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })
            Toast.fire({
                icon: 'success',
                title: '댓글 등록 완료!'
            })
        },
    }
}
</script>

<style lang="scss" scoped>
.btn {
    float: right;
}
.card, .form {
    font-family: 'Pretendard-SemiBold', serif;
}
$main-color: #8763FB;
.enrollBtn {
    background: linear-gradient(to right, #a6a8f3, $main-color);
    border-color: $main-color;
    vertical-align: middle;
    padding: 0.75em 1.25em;
    font-size: 0.8rem;
    font-family: 'Pretendard-SemiBold', serif;
}
</style>