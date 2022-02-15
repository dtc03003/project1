<template>
    <div class="row">
        <div class="col-12">
            <div v-show="styleArray[0]" >
                <styleModal :list-array="styleArray"></styleModal>
            </div>
            
            <div v-show="!styleArray[0]" align="center">
                <p id="noreview">아직 등록한 게시물이 없습니다😢</p>
            </div>
        </div>
        <hr class="mt-3">
        <div class="col-12 text-end" >
            
            <b-button  v-if="checkMemberInfo.nickname == this.profileData.nickname &&  this.checkauthority == 'ROLE_STYLIST'" v-b-modal.modal-1 id="registBtn">등록</b-button>
            <b-modal size="lg" id="modal-1" title="Style 등록" hide-footer>
                <b-row>
                    
                    <b-col class="col-12">
                        <h3>게시글</h3>
                        <b-form-textarea id="textarea" size="lg" v-model="post.text" placeholder="최대 255자 입력 가능합니다." rows="10" max-rows="10">                            
                        </b-form-textarea>
                        <hr>
                    </b-col>
                    <!-- <b-col class="col-12"> -->
                    <v-container fluid>
                        
                        <h3>태그 및 이미지</h3>
                        <v-combobox
                            v-model="inputtags"
                            hide-selected
                            hint="Maximum of 5 tags"
                            label="ex)출근룩, 하객룩"
                            clearable
                            multiple
                            persistent-hint
                            small-chips
                        >
                        </v-combobox>
                    </v-container>
                    <!-- </b-col> -->
                    <b-col class="col-12">
                        <UploadImages :max="1" ref="image" @changed="handleImages"/>
                    </b-col>
                    <b-col class="col-12">
                        <b-button id="submitBtn" @click="Posting" :disabled="!this.postconfirm">게시하기</b-button>
                    </b-col>
                </b-row>
            </b-modal>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import Swal from 'sweetalert2'
import UploadImages from "vue-upload-drop-images"
import styleModal from "./stylePage/styleModal.vue"
import { FITMATE_BASE_URL } from "@/config";
import { mapGetters } from 'vuex';

const memberStore = "memberStore";

export default {
    data() {
        return {
            nickname: this.$route.params.nickname,
            post: {
                text: '',
                image: '',
            },
            value: ['apple', 'orange'],
            inputtags: [],
            styleArray: [],
            checkauthority: '',
            profileData : [],
            postconfirm: true,
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
        axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/${this.nickname}`)
        .then(({ data }) => {
            this.profileData = data;
        })
        
        axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/${this.nickname}/styles/all`)
        .then(({ data })=> {
            this.styleArray = data;
        })

        this.checkauthority = this.checkMemberInfo.authority
        
    },
    methods:{
            handleImages(files){
                const formData = new FormData();
                const image = this.$refs['image'].files[0]
                
                let fileExt = image.name.substring(image.name.lastIndexOf(".") + 1)

                if (["jpeg","jpg","png","bmp"].includes(fileExt) && image.size <= 1048576) {
                    console.log(fileExt)

                    formData.append('images', image);
                    console.log(files)
                    console.log(formData)

                    axios.post(`${FITMATE_BASE_URL}/api/v1/images`, formData, {
                        header: { 'Content-Type': 'multipart/form-data' }
                    })
                    .then((res) => {
                        this.post.image = res.data.src
                        console.log(res.data.src)
                    })
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: '파일 형식에 맞지 않거나 \n사진 크기가 너무 큽니다! \nclear All 후 다시 진행해주세요!',
                        confirmButtonColor: '#7e7fb9',
                        confirmButtonText: "확인",
                    })
                    this.image == ''
                    return
                }
            },

            async Posting() {
                const postInfo = {
                    content: this.post.text,
                    thumbnail: this.post.image,
                }
                
                if (postInfo.content == '' || postInfo.thumbnail == '') {
                    Swal.fire({
                        icon: 'error',
                        title: '빈 내용이 있습니다!',
                        confirmButtonColor: '#7e7fb9',
                        confirmButtonText: "확인",
                    })
                    return
                } else {
                    console.log(postInfo)
                    const accessToken = localStorage.getItem("accessToken");
                    axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
                    await axios.post(`${FITMATE_BASE_URL}/api/v1/portfolio/style`, postInfo)
                    .then(({data}) => {
                        const tagging = {
                            styleId : data.id,
                            tag : this.inputtags
                        }
                        axios.post(`${FITMATE_BASE_URL}/api/v1/tag/tagging`, tagging)
                        .then((res)=>console.log(res))
                        
                        this.postconfirm = false

                        const Toast = Swal.mixin({
                            toast: true,
                            position: 'top-end',
                            showConfirmButton: false,
                            timer: 1000,
                            timerProgressBar: true,
                            didOpen: (toast) => {
                                toast.addEventListener('mouseenter', Swal.stopTimer)
                                toast.addEventListener('mouseleave', Swal.resumeTimer)
                            }
                        })
                        Toast.fire({
                            icon: 'success',
                            title: '게시 완료!'
                        }).then(()=>window.location.reload())

                    }) 
                }
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
p#noreview {font-size: 20pt; font-family: 'GangwonEdu_OTFBoldA';}
</style>
