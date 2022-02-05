<template>
    <div>
        <div id="my_container">
            <!-- 이미지는 개인 프로필 이미지로 변경 -->
            <img id="profile" src="https://hongjunland.s3.ap-northeast-2.amazonaws.com/default_profile.jpg">
            <div>
                <h1 style="text-align:center ">{{ memberStore.state.memberInfo.nickname }}</h1>
                <b-dropdown size="lg"  variant="link" toggle-class="text-decoration-none" no-caret>
                    <template #button-content >
                        &#128274;<span ></span>
                    </template>
                    <b-dropdown-item-button v-b-modal.modal-1>회원정보 수정</b-dropdown-item-button>

                    <b-modal id="modal-1" title="회원정보 수정" hide-footer centered>
                        <div class="d-block text-center mb-3" >
                            <h3>비밀번호 재확인</h3>
                            <b-form-input type="password" v-model="this.pwdconfirm"></b-form-input>
                        </div>
                        <div class="modal_button">
                            {{ memberStore.state.memberInfo }}
                            <b-button @click="checkpwd">확인</b-button>
                            <b-button @click="$bvModal.hide('modal-1')">취소</b-button>
                        </div>
                    </b-modal>
                    <b-dropdown-item-button >회원탈퇴</b-dropdown-item-button>
                </b-dropdown>
            </div>
        </div>
    </div>
</template>

<script>
import memberStore from '@/store/modules/memberStore'
export default {
    name: 'Mypage',
    data() {
        return {
            memberStore,
            pwdconfirm:'',
        }
    },
    methods: {
        checkpwd() {
            if (this.pwdconfirm == memberStore.state.memberInfo.nickname) {
                alert('확인되었습니다.')

            } else {
                alert('비밀번호가 다릅니다.')
            }
        }
    }
}
</script>

<style>
#my_container {
    margin-top: 5rem;
}
#profile {
    display: block;
    text-align: center;
    width: "300"; height:"300";
    margin-left: auto;
    margin-right: auto;
    border-radius: 50%;
}
.modal_button {
    text-align: right;
    margin-right: 5px;
}
</style>