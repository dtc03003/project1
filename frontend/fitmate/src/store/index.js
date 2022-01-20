import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
<<<<<<< HEAD
    houses: [],
=======
    data:'success',
>>>>>>> 30f82936f4db987d274d742250281261e85a8f5e
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
