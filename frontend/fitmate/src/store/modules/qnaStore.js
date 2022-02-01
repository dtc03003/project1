import axios from "@/module/axios.js"

const qnaStore = {

    state: {
        qna: {}
    },

    mutations: {
        setQnA(state, payload) {
            state.qna = payload.qna;
        }
    },

    actions: {
        async getQnA({ commit }, payload) {
            let { data } = await axios.get(`/api/v1/qna/${payload.id}`)
            commit("setQnA", { qna : data })
        }
    }
}

export default qnaStore;