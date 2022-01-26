import Vue from 'vue'
import Vuex from 'vuex'
import memberStore from "@/store/modules/memberStore.js";
import noticeStore from "@/store/modules/noticeStore.js";

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    memberStore,
    noticeStore,
  },
});

export default store;
