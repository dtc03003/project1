import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    houses: [],
    data: 'success',
  },
  mutations: {
    SET_WORD: (state, word) => {
      // console.log("SET_WORD", word)
      state.word = word;
    },
  },
  actions: {
    detailHouse: ({ commit }, house) => {
      commit("SET_DETAIL_HOUSE", house);
    },
  },
  modules: {
  }
})
