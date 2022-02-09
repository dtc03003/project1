<template>
    <div class="row">
        <div class="col-10">
            <h1>About </h1>
        </div>
        <div class="col-2">
            <b-button class="submitBtn" @click="Posting">수정</b-button>
        </div>
        <p>{{profileData.about}}</p>
    </div>
</template>

<script>
import axios from "@/module/axios.js";
import {mapState} from 'vuex'

const memberStore = "memberStore";

export default {
    data() {
        return {
            profileData : [],
        }
    },

    computed: {
        ...mapState(memberStore, ["memberInfo"]),
    },

    created () {
        axios.get(`/api/v1/portfolio/${this.memberInfo.nickname}`)
        .then(({ data }) => {
            console.log(data);
            this.profileData = data;
        })
        
    }
}
</script>
<style scoped>
.submitBtn { background-color: #7e7fb9 !important; width: 70%;}
</style>