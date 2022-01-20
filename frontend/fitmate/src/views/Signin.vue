<template>
    <div>
        <b-container>
            <b-row>
                <h1 id="signinTitle">FitMate</h1>
            </b-row>
            <b-row>
            </b-row>
            <div>
                <form>
                    <input type="email" placeholder="이메일" v-model="user.email"/> <br>
                    <input type="password" placeholder="비밀번호" v-model="user.password"/> <br>
                    <input type="button" value="로그인" />
                </form>
    
                <p>회원이 아니신가요?
                <input type="button" value="회원가입" /> </p>
            </div>
        </b-container>
    </div>
</template>
<script>
export default {
    name: "Signin",
    data() {
        return {
            user: {
                email: null,
                password: null
            }
        }
    }
}
</script>

<style scoped>
@font-face {
    font-family: 'Cafe24Ohsquare';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2001@1.1/Cafe24Ohsquare.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
div { border: 1px solid black; }
#signinTitle { font-family: "Cafe24Ohsquare", fantasy; }
</style>