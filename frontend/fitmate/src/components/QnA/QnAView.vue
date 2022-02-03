<template>
    <b-container class="bv-example-row mt-3">

        <b-row>
            <b-col>
                <h3 class="underline-hotpink">게시물 상세보기</h3>
            </b-col>
        </b-row>

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
                    class="mb-2"
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
                <b-form-group id="content-group" label="댓글:" label-for="content">
                    <b-form-textarea
                        id="content"
                        ref="content"
                        v-model="comment"
                        placeholder="댓글 입력..."
                        rows="5"
                        max-rows="10"
                    >
                    </b-form-textarea>
                </b-form-group>

                <b-button 
                    variant="primary"
                    class="m-1 float-right"
                    @click="checkValue"
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
                        <b-tr v-for="(comments, id) in commentInfo" :key="id">
                            <b-td>{{comments.writer}} </b-td>
                            <b-th>{{comments.comment}}</b-th>
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

const memberStore = "memberStore";

export default {
    data() {
        return{
            id: this.$route.params.id,
            comment: "",
            writer: "",
            createdAt: "",
            qnaId: ""
        }
    },

    computed: {
        ...mapState(memberStore, ["memberInfo"]),

        qna() {
            return this.$store.state.qnaStore.qna;
        }
    },

    created() {
        this.$store.dispatch("getQnA", { id: this.id })
    },

    methods: {
        checkValue() {

            this.registComment();
        },

        registComment() {
            const commentInfo = {
                "id": 0,
                "comment": this.comment,
                "writer": this.memberInfo.nickname,
                "createdAt": "",
                "qnaId": parseInt(this.id)
            };

            axios.post("/api/v1/comment", commentInfo)
            alert("댓글 등록 완료");
        }
    }
}
</script>