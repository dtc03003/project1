<template>
    <div>
        <b-container class="detail">
            <b-row>
                <b-col cols="3" class="r">
                    {{ thumbnail }}
                </b-col>
                <b-col cols="9" class="r">
                    <b-row>
                        <b-col cols="6" align="left">
                            <span id="nick">{{ nickname }}</span>님
                        </b-col>
                        <b-col cols="6" align="right">
                            평점
                            <span v-for="r in rating" :key="`re-${r}`">■</span>
                            <span v-for="r in (5-rating)" :key="`unre-${r}`">□</span>
                            {{ rating }}점
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col>
                            {{ content }}<br>
                            <div align="center">
                                <v-btn fab text small color="grey darken-2" @click="showContent" v-show="content.length >= 50 && showOrHide">
                                    <v-icon> mdi-chevron-down </v-icon>
                                </v-btn>
                                <v-btn fab text small color="grey darken-2" @click="hideContent" v-show="content.length >= 50 && !showOrHide">
                                    <v-icon> mdi-chevron-up </v-icon>
                                </v-btn>
                            </div>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col align="right">
                            {{ createdAt }}
                        </b-col>
                    </b-row>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import dayjs from "dayjs";
const reviewStore = "reviewStore";
export default {
    name: "ReviewDetail",
    data() {
        return {
            thumbnail: '',
            nickname: '',
            rating: 0,
            content: '',
            createdAt: '',
            showOrHide: true,
        }
    },
    props: {
        idx: Number,
        review: Object,
    },
    computed: {
        ...mapGetters(reviewStore, ["getReviews", "getSomeReviews", "getReviewStatus"]),
    },
    created() {
        if(this.getReviewStatus) {
            this.thumbnail = this.review.thumbnail;
            this.nickname = this.review.member.nickname;
            this.rating = this.review.rating;
            this.createdAt = dayjs(this.review.createdAt).format("YYYY-MM-DD HH:mm");
            this.content = this.review.content;
            if(this.content.length > 50) this.content = this.content.substring(0, 50);
        }
    },
    methods: {
        showContent() {
            this.content = this.review.content;
            this.showOrHide = false;
        },
        hideContent() {
            this.content = this.content.substring(0, 50);
            this.showOrHide = true;
        }
    }
}
</script>

<style scoped>
.detail { width: 80%; }
.r { border: 1px solid black; }
#nick {font-weight: bold;}
</style>