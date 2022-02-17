<template>
    <b-container class="bv-example-row mt-3">

        <b-row>
            <b-col align="right">
                <a id="backbtn" @click="goBack">🔙</a>
            </b-col>
        </b-row>

        <b-row class="mb-1">
            <b-col>

                <b-card
                    v-if="Object.keys(notice).length != 0"
                    :header-html="`
                    <h3>${notice.title}</h3>
                    <h5>${notice.writer}</h5>
                    <div>
                        <h6>${changeDate(notice.createdAt)}</h6>
                    </div>
                `"
                    class="mb-2 card"
                    border-variant="dark"
                    no-body
                >
                    <b-card-body class="text-left card">
                        <span style="white-space: pre-line">
                            {{ notice.content }}
                        </span>
                    </b-card-body>
                </b-card>

            </b-col>
        </b-row>


    </b-container>
</template>

<script>
import dayjs from "dayjs";
export default {
    data() {
        return{
            id: this.$route.params.id,
        }
    },

    computed: {
        notice() {
            return this.$store.state.noticeStore.notice;
        }
    },

    created() {
        this.$store.dispatch("getNotice", { id: this.id })
    },

    methods: {
        goBack() {
            this.$router.go(-1);
        },

        changeDate(date) {
            return dayjs(date).format("YYYY-MM-DD HH:MM");
        }
    },
}
</script>

<style scoped>
#backbtn { cursor: pointer; font-size: 1.5rem; }
.card {font-family: 'Pretendard-SemiBold', serif;}
</style>