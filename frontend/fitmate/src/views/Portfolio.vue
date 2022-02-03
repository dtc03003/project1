<template>
    <div>
        <b-container class="bv-example-row mt-3">
            <b-row>
                <b-col>
                    <img id="stylist" src="https://hongjunland.s3.ap-northeast-2.amazonaws.com/default_profile.jpg" width="300" height="300">
                    <!-- <h2 style="text-align:center">{{ memberStore.state.memberInfo }}</h2> -->
                    <!-- <img src="" alt=""> -->
                </b-col>
                <b-col cols="9">
                    <b-tabs content-class="mt-3" fill pills card>
                        <b-tab class="mx-1" title="Style"><StylePage/></b-tab>
                        <b-tab title="Review"></b-tab>
                        <b-tab title="Schedule" active><Schedule/></b-tab>
                        <b-tab title="About"></b-tab>
                    </b-tabs>
                </b-col>
            </b-row>
        </b-container>
    </div>
</template>

<script>
import Schedule from "@/components/portfolio/Schedule.vue";
import StylePage from "@/components/portfolio/StylePage"
import memberStore from '@/store/modules/memberStore'

export default {
    name: "Portfolio",
    components: {
        Schedule,
        StylePage
    },
    data: function() {
        return {
            memberStore,
        }
    }
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
</style>