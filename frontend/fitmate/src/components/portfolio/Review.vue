<template>
    <div>
        <ReviewDetail v-for="(review, idx) in reviews" :key="idx" :idx="idx"/>
    </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import ReviewDetail from './review/ReviewDetail.vue';
const reviewStore = "reviewStore";

export default {
  components: { ReviewDetail },
    name: 'Review',
    data() {
        return {
            reviews: {},
        }
    },
    created() {
        this.importReviews("지니쓰");
    },
    computed: {
        ...mapGetters(reviewStore, ["getReviews", "getPortfolioStatus"]),
    },
    methods: {
        ...mapActions(reviewStore, ["importAllReviews", "findPortfolioStatus"]),
        async importReviews(nickname) {
            await this.findPortfolioStatus(nickname);
            if(this.getPortfolioStatus) {
                await this.importAllReviews(nickname);
                this.reviews = this.getReviews;
                console.log(this.reviews)
            }
        }
    }

}
</script>

<style scoped>
</style>