import Vue from "vue";
import Vuex from "vuex";
import memberStore from "@/store/modules/memberStore.js";
import noticeStore from "@/store/modules/noticeStore.js";
import orderStore from "@/store/modules/orderStore.js";
import qnaStore from "@/store/modules/qnaStore.js";
import scheduleStore from "@/store/modules/scheduleStore";
import reviewStore from "@/store/modules/reviewStore";
import reserveStore from "@/store/modules/reserveStore";
import createPersistedState from "vuex-persistedstate";
import styleStore from "@/store/modules/styleStore";
import followStore from "@/store/modules/followStore";
import carouselStore from "@/store/modules/carouselStore";
import commentStore from "@/store/modules/commentStore";
import roomStore from "@/store/modules/roomStore";

Vue.use(Vuex);

const store = new Vuex.Store({
  modules: {
    memberStore,
    noticeStore,
    orderStore,
    qnaStore,
    scheduleStore,
    styleStore,
    reviewStore,
    reserveStore,
    followStore,
    carouselStore,
    commentStore,
    roomStore,
  },
  state: {
    comments: [],
    stylistArray: [],
  },
  mutations: {
  },
  actions: {
    logout: function ({ commit }) {
      commit("SIGNOUT");
    },
  },
  plugins: [
    createPersistedState({
      //새로고침해도 초기화 안되도록 방지
      paths: ["memberStore", "orderStore"],
    }),
  ],
});

export default store;
