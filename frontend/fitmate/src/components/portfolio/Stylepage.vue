<template>
    <div class="row">
        <div class="col-12">
            <styleModal :list-array="styleArray"></styleModal>
        </div>
        <hr class="mt-3">
        <div class="col-12 text-end" >
            
            <b-button v-if="this.checkauthority === 'ROLE_STYLIST'" v-b-modal.modal-1 id="registBtn">등록</b-button>
            <b-modal size="lg" id="modal-1" title="Style 등록" hide-footer>
                <b-row>
                    <b-col class="col-12">
                        <b-form-textarea id="textarea" size="lg" v-model="post.text" placeholder="최대 255자 입력 가능합니다." rows="10" max-rows="10">                            
                        </b-form-textarea>
                    </b-col>
                    <b-col class="col-12">
                        <UploadImages ref="image" @changed="handleImages"/>
                    </b-col>
                    <b-col class="col-12">
                        <b-button id="submitBtn" @click="Posting">게시하기</b-button>
                    </b-col>
                </b-row>
            </b-modal>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import UploadImages from "vue-upload-drop-images"
import styleModal from "./stylePage/styleModal.vue"
import { FITMATE_BASE_URL } from "@/config";
import { mapGetters } from 'vuex';

const memberStore = "memberStore";

export default {
    data() {
        return {
            post: {
                text: '',
                image: '',
            },
            styleArray: [],
            checkauthority: '',
        }
    },
    components: {
        UploadImages,
        styleModal,
    },
    computed: {
        ...mapGetters(memberStore, ["checkMemberInfo"]),
    },
    created() {
        axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/${this.checkMemberInfo.nickname}/styles/all`)
        .then(({ data })=> {
            console.log(data)
            this.styleArray = data;
        })
        this.checkauthority = this.checkMemberInfo.authority
        console.log(this.checkauthority)
    },
    methods:{
            handleImages(files){
                const config = { baseUrl: 'http://localhost:9000' };
                const formData = new FormData();
                const image = this.$refs['image'].files[0]

                formData.append('images', image);
                console.log(files)
                console.log(formData)

                axios.post(`${config.baseUrl}/api/v1/images`, formData, {
                    header: { 'Content-Type': 'multipart/form-data' }
                })
                .then((res) => {
                    this.post.image = res.data.src
                    console.log(res.data.src)
                })
                // if (files.length > 1) {
                //     for (this.i = 0; this.i <= files.length ; this.i++ ){
                //         const image = this.$refs['image'].files[this.i]
                //         formData.append('images', image)
                //         axios.post(`${config.baseUrl}/api/v1/images`, formData, {
                //             header: { 'Content-Type': 'multipart/form-data' }
                //         })
                //         .then((res) => {
                //             this.post.image.append(res.data.src) 
                //             console.log(res.data.src)
                //         })
                //     }
                // } else {
                //     const image = this.$refs['image'].files[0]
                //     formData.append('images', image);
                //     axios.post(`${config.baseUrl}/api/v1/images`, formData, {
                //         header: { 'Content-Type': 'multipart/form-data' }
                //     })
                //     .then((res) => {
                //         this.post.image = res.data.src
                //         console.log(res.data.src)
                //     })
                // }
            },

            async Posting() {
                const postInfo = {
                    content: this.post.text,
                    thumbnail: this.post.image,
                }
                console.log(postInfo)
                const accessToken = localStorage.getItem("accessToken");
                axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
                await axios.post(`${FITMATE_BASE_URL}/api/v1/portfolio/style`, postInfo)
                .then(() => {
                    alert('게시 완료')
                    console.log('정상적으로 게시되었습니다.')
                    window.location.reload();
                }) 
                .catch((err) => {
                    alert('게시 실패')
                    console.log(err)
                })
            }
        }
}
</script>

<style>
@media (min-width: 992px) { 
   .modal .modal-lg { 
        max-width: 50% !important;
        width: 50% !important;
    }
 }
#submitBtn { background-color: #7e7fb9; width: 100%;} /* 올해의 색상코드: #6667AB */
#registBtn { background-color: #7e7fb9; }
.hr-sect::before,
.hr-sect::after {
content: "";
flex-grow: 1;
background: rgba(0, 0, 0, 0.35);
height: 1px;
font-size: 0px;
line-height: 0px;
margin: 0px 16px;
}
</style>
