<template>  
    <div>
<<<<<<< HEAD
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
=======
        <!-- <div v-if="!this.input_about" > -->
        <div>
            <b-container class="bv-example-row mt-5">
                <b-row>
                <b-col></b-col>
                <b-col class="col-8">
                    <b-card class="text-center mt-3" style="max-width: 90rem" align="left">
                        <h1 id="signinTitle">{{ memberStore.state.memberInfo.name }} 님의 포트폴리오를 만드시겠습니까?</h1>
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
>>>>>>> feature/Mypage_303
    </div>
</template>

<script>
import Schedule from "@/components/portfolio/Schedule.vue";
import StylePage from "@/components/portfolio/Stylepage.vue"
import Profile from "@/components/portfolio/Profile.vue"
import About from "@/components/portfolio/About.vue"
import memberStore from '@/store/modules/memberStore'
import axios from 'axios';

export default {
    name: "Portfolio",
    components: {
        Schedule,
        StylePage,
<<<<<<< HEAD
        Profile,
        About,
=======
>>>>>>> feature/Mypage_303
    },
    data: function() {
        return {
            memberStore,
            portfoliocreate : {
                about : '',
                price : '',
                bio : '',
            }
        }
    },
    methods: {
        createportfolio() {
            const config = { baseUrl: 'http://localhost:9000' };            
            const accessToken = localStorage.getItem("accessToken");
            axios({
                method: 'post',
                url: `${config.baseUrl}/api/v1/portfolio`,
                data: this.portfoliocreate,
                headers: { Authorization: `Bearer ${accessToken}` }
            })
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