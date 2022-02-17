<template>
    <div>
        <b-container class="detail">
            <b-row class="r">
                <b-col cols="3" class="c">
                    <img id ="thumbnail" :src="thumbnail" />
                </b-col>
                <b-col cols="9" class="c">
                    <b-row>
                        <b-col cols="6" align="left">
                            <span id="nick">{{ nickname }}</span>님
                        </b-col>
                        <b-col cols="6" align="right">
                            평점
                            <span v-for="r in rating" :key="`re-${r}`" class="star-ratings-fill">■</span>
                            <span v-for="r in (5-rating)" :key="`unre-${r}`">□</span>
                            {{ rating }}점
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col>
                            <p id="rvcontent">{{ content }}</p><br>
                            <div align="center">
                                <v-btn fab text small color="grey darken-2" @click="showContent" v-show="content.length >= 200 && showOrHide">
                                    <v-icon> mdi-chevron-down </v-icon>
                                </v-btn>
                                <v-btn fab text small color="grey darken-2" @click="hideContent" v-show="content.length >= 200 && !showOrHide">
                                    <v-icon> mdi-chevron-up </v-icon>
                                </v-btn>
                            </div>
                        </b-col>
                    </b-row>
                    <b-row>
                        <b-col align="right">
                            작성일: {{ createdAt }}
                        </b-col>
                    </b-row>
                    <b-row v-if="name == 'MyPage'">
                        <b-col align="right">
                            스타일리스트: {{ stylist }}
                        </b-col>
                    </b-row>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import dayjs from "dayjs";
export default {
    name: "ReviewDetail",
    data() {
        return {
            thumbnail: '',
            rating: 0,
            content: '',
            createdAt: '',
            showOrHide: true,
            nickname: '',
            stylist: '',
        }
    },
    props: {
        idx: Number,
        review: Object,
        name: String,
    },
    created() {
        this.thumbnail = this.review.thumbnail;
        this.nickname = this.review.member.nickname;
        this.rating = this.review.rating;
        this.createdAt = dayjs(this.review.createdAt).format("YYYY-MM-DD HH:mm");
        this.content = this.review.content;
        if(this.content.length > 200) this.content = this.content.substring(0, 200);
        this.stylist = this.review.portfolio.member.nickname;
    },
    methods: {
        showContent() {
            this.content = this.review.content;
            this.showOrHide = false;
        },
        hideContent() {
            this.content = this.content.substring(0, 200);
            this.showOrHide = true;
        }
    }
}
</script>

<style scoped>
.detail { width: 80%; font-family: 'Pretendard-SemiBold', serif; text-align: initial; font-size: 1rem; }
.r { border-radius: 1rem; box-shadow: 3px 2px 2px rgb(160, 160, 160); }
#nick {font-weight: bold; }
img#thumbnail {width: 100%; height: 100%; object-fit: contain;}
img#thumbnail:hover {transform: scale(1.15, 1.15); opacity: 1;}
.star-ratings-fill { color: rgb(105, 221, 206);}
p#rvcontent {text-align: initial;}
</style>