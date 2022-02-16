<template>
    <div class="row">
        <div class="col-10">
            <h1>About </h1>
        </div>
        <div class="col-2">
            <b-button v-if="checkMemberInfo.nickname == this.profilenick && checkMemberInfo.authority == 'ROLE_STYLIST'" class="submitBtn" @click="$bvModal.show('aboutmodify')">수정</b-button>
        </div>
        <b-modal id="aboutmodify" size="lg" centered header-class="modalHeader" hide-footer>
            <template #modal-title>
                <h3 class="d-inline" id="fontchange" style="font-size: 1.7rem">✅ 소개글 수정</h3>
            </template>
            <b-row>
                <b-form-textarea id="textarea"  v-model="abouttext" placeholder="자신을 나타낼 수 있는 소개글을 적어보세요!" rows="8" size="md"
                ></b-form-textarea>
                <b-col class="col-2"></b-col>
                <b-col class="col-4 trytocenter" >
                    <b-button class="exitbtn" id="fontchange" @click="modifyabout()">수정</b-button>
                </b-col>
                <b-col class="col-4 trytocenter">
                    <b-button class="exitbtn" id="fontchange" @click="$bvModal.hide('aboutmodify')">취소</b-button>
                </b-col>
            </b-row>
        </b-modal>
        <div class="row">
            <b-card class="col-12 border-color" >
                <span style="white-space: pre-line">
                    {{profileData.about}}
                </span>
            </b-card>
        </div>
    </div>
</template>

<script>
import axios from "@/module/axios.js";
import {mapState, mapGetters, mapActions} from 'vuex'
import { FITMATE_BASE_URL } from "@/config";
import Swal from 'sweetalert2'
const memberStore = "memberStore";

export default {
    data() {
        return {
            nickname: this.$route.params.nickname,
            profileData : [],
            abouttext: '',
            profilenick: '',
        }
    },

    computed: {
        ...mapState(memberStore, ["memberInfo"]),
        ...mapGetters (memberStore, ['checkMemberInfo']),
        content() { 
            return this.content.split('\n').join('<br>'); //return this.content.replace(/(?:\r\n|\r|\n)/g, '<br>'); 정규식 
        },
    },

    created () {
        axios.get(`/api/v1/portfolio/${this.nickname}`)
        .then(({ data }) => {
            this.profileData = data;
            this.profilenick = data.nickname
        })
    },

    methods: {
        ...mapActions(memberStore, ["signInMemberInfo"]),
        modifyabout() {
            const bioinfo = {
                about : this.abouttext,
                price : this.profileData.price,
                bio : this.profileData.bio,
            }
            if (bioinfo.about == '') {
                Swal.fire({
                    title: '소개글을 입력해주세요!',
                    confirmButtonText: '확인',
                    icon: 'error'
                })
                return
            }
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            axios.put(`${ FITMATE_BASE_URL }/api/v1/portfolio/about`, bioinfo)
            .then(() => {
                Swal.fire({
                    title: '소개글이 저장되었습니다!',
                    confirmButtonText: '확인',
                    icon: 'success'
                }).then((res) => {
                    if(res.isConfirmed) {
                        let accessToken = localStorage.getItem("accessToken");
                        this.signInMemberInfo(accessToken);
                        window.location.reload()
                    }
                })
            })
        }
    },
}
</script>
<style scoped>
.submitBtn { background-color: #7e7fb9 !important; width: 70%;}
.border-color {
    height: 22rem;
    border-block-color: #7e7fb9 !important;
}
.trytocenter {
    text-align: center;
}
#fontchange {
    font-family: 'GangwonEdu_OTFBoldA' ;
    font-size: 1.2rem;
}
.modalHeader {
    color: white;
    background-color: #7e7fb9;
    text-align: center;
    font-weight: 600;
}
</style>