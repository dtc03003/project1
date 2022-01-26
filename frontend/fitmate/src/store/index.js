import Vue from 'vue'
import Vuex from 'vuex'
import memberStore from "@/store/modules/memberStore.js";

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    memberStore,
  },
});

export default store;
