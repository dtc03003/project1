<template>  
    <div>
        <b-container class="bv-example-row mt-3">
            <b-row>
                <b-col sm="12" md="12" lg="12" xl="3">
                    <Profile/>
                </b-col>
                <b-col sm="12" md="12" lg="12" xl="9">
                    <b-tabs id="profiletab" content-class="mt-3" fill pills card>
                        <b-tab class="mx-1" title="Style"><StylePage/></b-tab>
                        <b-tab title="Review"><Review/></b-tab>
                        <b-tab title="Schedule"><Schedule/></b-tab>
                        <b-tab title="About"><About/></b-tab>
                    </b-tabs>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import Schedule from "@/components/portfolio/Schedule.vue";
import StylePage from "@/components/portfolio/Stylepage.vue"
import Profile from "@/components/portfolio/Profile.vue"
import About from "@/components/portfolio/About.vue"
import Review from "@/components/portfolio/Review.vue"
import axios from 'axios';
import { mapGetters } from 'vuex';
import { FITMATE_BASE_URL } from "@/config";
const memberStore = "memberStore";

export default {
    name: "Portfolio",
    components: {
        Schedule,
        StylePage,
        Profile,
        About,
        Review
    },
    data: function() {
        return {
            nickname: this.$route.params.nickname,
            portfolioconfirm: '',
        }
    },
    computed: {
        ...mapGetters(memberStore, ["checkMemberInfo"]),
    },
    mounted() {
        axios({
            url: `${FITMATE_BASE_URL}/api/v1/portfolio/${this.nickname}`,
            method: 'get',
        })
        .then((res)=> this.portfolioconfirm = res.data.about)
        .catch(()=>{})
    },
}
</script>

<style>
.nav-pills .nav-link:not(.active) {
    background-color: #FFFFFF !important;
    color: #000000 !important;
}
.nav-pills .nav-link.active, .nav-pills .show>.nav-link {
    background-color: #7e7fb9 !important;
    color: #fff !important;
}
.nav-fill .nav-item .nav-link, .nav-justified .nav-item .nav-link {
    width: 90% !important;
}
.card-header {
    background-color: #FFFFFF !important;
    border-bottom: 1px solid rgba(0,0,0,.125);
}
#profiletab {
    font-family: 'GangwonEdu_OTFBoldA';
    font-size: 1.3rem;
}
</style>