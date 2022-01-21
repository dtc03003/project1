<template>
  <div class="m-3">
    <h1>Stylist</h1>
      <label>이메일(ID) : 
        <input type="text" v-model="credentials.userid" maxlength="20">
        <div v-if="!idValid">
          유효하지 않은 이메일입니다.
        </div>
      </label>
      <br>
      <label>닉네임 : 
        <v-text-field 
          type="text"
          id="nickname"
        ></v-text-field>
      </label>
      <br>
      <label>비밀번호 : 
        <v-text-field 
          type="password"
          id="password"
        ></v-text-field>
      </label>
      <br>
      <label>비밀번호 확인 : 
        <v-text-field 
          type="password"
          id="pwdconfirm"
        ></v-text-field>
      </label>
      <br>
      <label>전화번호 : 
        <v-text-field 
          type="text"
          id="phonenumber"
        ></v-text-field>
      </label>
      <br>
      <label>계좌번호 : 
        <v-text-field 
          type="text"
          id="accountnumber"
        ></v-text-field>
      </label>
      <br>

      <label>은행 : 
        <v-text-field 
          type="dropdown"
          id="bank"
        ></v-text-field>
      </label>


  </div>
</template>

<script>

export default {
  name: 'Stylist',
  data: function () {
    
    return {
      credentials: {
        userid: null,
        nickname: null,
        password: null,
        pwdconfirm: null,
        phonenumber: null,
        accountnumber: null,
        bank: null,
      },
    }
  },
  methods: {
    signup() {
      this.$store.dispatch('signup', this.credentials)
    }
  },
  computed: {
    idValid () {
      return /^[A-Za-z0-9@.]+$/.test(this.credentials.userid)
    }
  }  
}
</script>

<style>

</style>