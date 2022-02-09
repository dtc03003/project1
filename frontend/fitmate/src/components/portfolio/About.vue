<template>
    <div>
        <h1>About </h1>
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
            nickname: this.$route.params.nickname,
            profileData : [],
        }
    },

    computed: {
        ...mapState(memberStore, ["memberInfo"]),
    },

    created () {
        axios.get(`/api/v1/portfolio/${this.nickname}`)
        .then(({ data }) => {
            console.log(data);
            this.profileData = data;
        })
        
    }
}
</script>