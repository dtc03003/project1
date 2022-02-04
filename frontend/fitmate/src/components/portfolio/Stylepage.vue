<template>
    <div class="row">
        <div class="col-12">
            <temporary/>
        </div>
        <hr>
        <div class="col-12 text-end" >
            <b-button v-b-modal.modal-1 id="registBtn">등록</b-button>
            <b-modal size="lg" id="modal-1" title="Style 등록" hide-footer>
                <b-row>
                    <b-col class="col-12">
                        <b-form-textarea id="textarea" size="lg" v-model="post.text" placeholder="Enter something..." rows="10" max-rows="10">                            
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
import temporary from "./stylePage/temporary.vue"
export default {
    data() {
        return {
            post: {
                text: '',
                image: '',
            }
        }
    },
    components: {
        UploadImages,
        temporary,
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
            },
            Posting() {
                const config = { baseUrl: 'http://localhost:9000' };
                const postInfo = {
                    content: this.post.text,
                    thumbnail: this.post.image,
                }
                console.log(postInfo)
                axios.post(`${config.baseUrl}/api/v1/portfolio/style`, postInfo)
                .then(() => {
                    alert('게시 완료')
                    console.log('정상적으로 게시되었습니다.')
                }) 
                .catch(() => {
                    alert('게시 실패')
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
