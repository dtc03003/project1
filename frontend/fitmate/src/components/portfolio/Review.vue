<template>
    <div>
        <ReviewDetail id="my-review" v-for="(review, idx) in someReviews" :key="review.id" :idx="idx" :review="review"/>
        <b-pagination align="center" class="mt-2"
            v-model="page"
            :total-rows="rows"
            :per-page="perPage"
            @change="changePage"
        ></b-pagination>
    </div>
</template>

<script>
import { mapActions, mapGetters, mapMutations } from 'vuex'
import ReviewDetail from './review/ReviewDetail.vue';
const reviewStore = "reviewStore";

export default {
    name: 'Review',
    data() {
        return {
            reviews: [],
            allReviews: [],
            someReviews: [],
            page: 1,
            perPage: 5,
            nickname: '',
        }
    },
    components: { ReviewDetail },
    created() {
        this.nickname = '지니쓰'; //스타일리스트 이름 받아오게되면 변경
        this.importReviews(this.nickname);
    },
    computed: {
        ...mapGetters(reviewStore, ["getReviews", "getPortfolioStatus", "getSomeReviews"]),
        rows() {
            return this.allReviews.length
        },
    },
    methods: {
        ...mapMutations(reviewStore, ["SET_SOME_REVEIW_LIST", "SET_REVEIW_LIST"]),
        ...mapActions(reviewStore, ["importAllReviews", "findPortfolioStatus", "fineReviewsByPage"]),
        async importReviews(nickname) {
            await this.findPortfolioStatus(nickname); //해당 스타일리스트 모든 리뷰 불러오기
            const info = {
                nickname: nickname,
                page: this.page //처음에는 1페이지
            }
            await this.fineReviewsByPage(info); //해당 스타일리스트 리뷰 5개씩 불러오기
            if(this.getPortfolioStatus) {
                await this.importAllReviews(nickname);
                this.allReviews = this.getReviews;
                this.reviews = this.getReviews;
                this.SET_REVEIW_LIST(this.reviews);
                this.someReviews = this.getSomeReviews; //페이지별 가져오기
            }
        },
        async changePage(page) {
            const info = {
                nickname: this.nickname,
                page: page
            }
            this.SET_SOME_REVEIW_LIST([]);
            await this.fineReviewsByPage(info);
            this.someReviews = this.getSomeReviews;
        }
    }

}
</script>

<style scoped>
</style>