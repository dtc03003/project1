<template>
    <div>

        <img id="stylist" :src="this.memberInfo.profile" width="300" height="300">
        <h1 class="mt-3" v-if="this.checkauthority == 'ROLE_STYLIST'">{{ profileData.nickname }}</h1>
        <p class="mt-3" v-if="this.checkauthority == 'ROLE_STYLIST'">{{ profileData.bio }} </p>

        <div class="mt-5">
            <b-icon icon="suit-heart-fill" font-scale="3" variant="danger" style="margin-right:60px;"></b-icon>
            <b-icon icon="chat-dots" font-scale="4" class="mr-2" style="margin-right:60px;"></b-icon>
            <b-icon icon="share-fill" font-scale="4"></b-icon>
        </div>

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
            checkauthority: '',
        }
    },

    computed: {
        ...mapState(memberStore, ["memberInfo"]),
    },

    created () {
        this.checkauthority = this.memberInfo.authority;
        console.log(this.checkauthority)

        if(this.checkauthority == 'ROLE_STYLIST'){
            axios.get(`/api/v1/portfolio/${this.memberInfo.nickname}`)
            .then(({ data }) => {
                console.log(data);
                this.profileData = data;
            })
        }
    }
}
</script>

<style>
b-icon {
    margin-right:10px;
}
</style>