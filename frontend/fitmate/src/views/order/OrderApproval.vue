<template>
    <div>
        <h2>결제가 완료되었습니다.</h2>
    </div>
</template>

<script>
const tokens = {};
export default {
    name: "OrderApproval",
    data() {
        return {
            pg_token: '',
        }
    },
    created() {
        window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(str, key, value) { tokens[key] = value; });
        if(tokens['pg_token']) {
            this.pg_token = tokens['pg_token'];
        }
        console.log(this.pg_token);
    },
    methods: {

    },
}
</script>

<style>

</style>