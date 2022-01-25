<template>
    <b-container class="bv-example-row mt-3">
        <h2>공지사항 등록</h2>
        <b-form-group
            id="title-group"
            label="제목:"
            label-for="title"
            description=""
        >

            <b-form-input
                id="title"
                ref="title"
                type="text"
                required
                placeholder="제목 입력..."
            />
        </b-form-group>
    
        <b-form-group id="question-group" label="내용:" label-for="question">
            <b-form-textarea
                id="question"
                ref="question"
                placeholder="내용 입력..."
                rows="10"
                max-rows="15"
            >
            </b-form-textarea>
        </b-form-group>
    
        <b-button 
            variant="primary"
            class="m-1 float-right"
            @click="checkValue"
        >등록</b-button>
    
    </b-container>

</template>

<script>
export default {
    data() {
        return {
            id : "",
            title : "",
            content: "",
            writer : "",
            createdAt : ""
        }
    },


    // 데이터
    created(){
    
    },
    
    methods: {
        // 제목 내용 작성에 이상없는지 확인
        checkValue() {
            
            // 이상 없으면 페이지 이동
            this.moveList();
        },
    
        // 등록 요청 시 보낼 데이터
        registNotice() {
    
        },
    
        // List 페이지로 이동
        moveList() {
            this.$router.push({ name: "NoticeList" });
        },
    },

}
</script>