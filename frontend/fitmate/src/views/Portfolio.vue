<template>  
    <div>
        <!-- <div v-if="!this.input_about" > -->
        <div>
            <b-container class="bv-example-row mt-5">
                <b-row>
                <b-col></b-col>
                <b-col class="col-8">
                    <b-card class="text-center mt-3" style="max-width: 90rem" align="left">
                        <h1 id="signinTitle">{{ this.checkMemberInfo.name }} 님의 포트폴리오를 만드시겠습니까?</h1>
                        <b-form-input  v-model="portfoliocreate.about" placeholder="아무 단어나 입력하세요."></b-form-input>
                        <b-form-input  v-model="portfoliocreate.price" placeholder="아무 단어나 입력하세요."></b-form-input>
                        <b-form-input @keyup.enter="createportfolio" v-model="portfoliocreate.bio" placeholder="아무 단어나 입력하세요."></b-form-input>
                    </b-card>
                </b-col>
                <b-col></b-col>
                </b-row>    
            </b-container>
        </div>

        <!-- <div v-else-if="this.input_about">     -->
        <div>
            <b-container class="bv-example-row mt-3">
                <b-row>
                    <b-col>
                        <Profile/>
                    </b-col>
                    <b-col cols="9">
                        <b-tabs content-class="mt-3" fill pills card>
                            <b-tab class="mx-1" title="Style"><StylePage/></b-tab>
                            <b-tab title="Review"></b-tab>
                            <b-tab title="Schedule" active><Schedule/></b-tab>
                            <b-tab title="About"><About/></b-tab>
                        </b-tabs>
                    </b-col>
                </b-row>
            </b-container>
        </div>
    </div>
</template>

<script>
import Schedule from "@/components/portfolio/Schedule.vue";
import StylePage from "@/components/portfolio/Stylepage.vue"
import Profile from "@/components/portfolio/Profile.vue"
import About from "@/components/portfolio/About.vue"
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
    },
    data: function() {
        return {
            portfoliocreate : {
                about : '',
                price : '',
                bio : '',
            }
        }
    },
    computed: {
        ...mapGetters(memberStore, ["checkMemberInfo"]),
    },
    created() {
    },
    methods: {
        async createportfolio() {
            const portinfo = {
                about : this.portfoliocreate.about,
                price : this.portfoliocreate.price,
                bio : this.portfoliocreate.bio,
            }     
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            await axios.post(`${FITMATE_BASE_URL}/api/v1/portfolio`, portinfo)
            .then((res) => {
                alert('포트폴리오 생성완료')
                console.log(res)
            }) 
            .catch((err) => {
                alert('포트폴리오 생성실패')
                console.log(err)
            })
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