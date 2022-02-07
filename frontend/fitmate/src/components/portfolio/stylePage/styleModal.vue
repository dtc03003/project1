<template>
    <div class="row">
        <div class="temcontainer">
            <li style="list-style: none"  v-for="(style, id) in paginatedData" :key="id" >
                <img :src="style.thumbnail" class="item" @click="openModal(style)">
            </li>
        </div>

        <b-modal v-if="this.styleData" size="xl" scrollable ref="style-modal" hide-footer>
            <div class="row">
                <div class="col-6">
                    <img :src="this.styleData.thumbnail" class="item">
                </div>
                <div class="col-6">
                    <h1>{{this.styleData.content}}</h1>
                    <the-modal-comment/>
                </div>
            </div>
        </b-modal>
        
        <!-- <img src= "">{{ this.listArray[0].thumbnail }} -->
        <!-- <h1>{{paginatedData}}</h1> -->
        <div class="btn-cover">
            <b-button :disabled="pageNum === 0" @click="prevPage" class="page-btn btn-secondary">이전</b-button>
            <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
            <b-button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn btn-secondary">다음</b-button>
        </div>
    </div>
</template>

<script>
// import axios from 'axios'
import TheModalComment from "@/components/Stylist/TheModalComment"
// import { FITMATE_BASE_URL } from "@/config";
import { mapState, mapGetters } from 'vuex';
const memberStore = "memberStore";
export default {
    name: 'temporary',
    data() {
        return {
            pageNum: 0,
            styleData: null,
        }
    },
    props: {
        listArray: {
            type: Array,
            required: true
        },
        pageSize: {
            type: Number,
            required: false,
            default: 8
        }
    },
    components: {
        TheModalComment,
    },
    methods: {
        openModal(data) {
            this.styleData = data
            this.$refs['style-modal'].show()
            console.log(this.styleData)
        },
        nextPage () {
            this.pageNum += 1;
        },
        prevPage () {
            this.pageNum -= 1;
        },
    },
    computed: {
        ...mapState(memberStore, ["isSignin"]),
        ...mapGetters(memberStore, ["checkisSignin"]),
        
        pageCount () {
            let listLeng = this.listArray.length,
                listSize = this.pageSize,
                page = Math.floor(listLeng / listSize);
            if (listLeng % listSize > 0) page += 1;
            return page;
        },

        paginatedData () {
            const start = this.pageNum * this.pageSize,
                end = start + this.pageSize;
            return this.listArray.slice(start, end);
        }
    },
    
}
</script>

<style>
.temcontainer {
  display: grid;
  grid-template-columns: 25% 25% 25% 25%;
  padding: 10px;
  background-size: cover;
}
.item {
  width: 100%; height: 100%;
  object-fit: cover;
  padding: 10px;
  font-size: 30px;
  align-self: center;
}
.btn-cover {
    margin-top: 1.5rem;
    text-align: center;
}
.btn-cover .page-btn {
    background-color: #7e7fb9;
    width: 5rem;
    height: 2rem;
    letter-spacing: 0.5px;
    float: initial;
    line-height: 0.5 !important; 
}
.btn-cover .page-count {
    padding: 0 1rem;
}
.btn {
    background-color: #7e7fb9;
    
}
</style>