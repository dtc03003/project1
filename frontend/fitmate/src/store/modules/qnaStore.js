import axios from "@/module/axios.js"

const qnaStore = {

    state: {
        qnalist: [],
        qna: {},
        comments: {}
    },

    mutations: {
        setQnAList(state, payload) {
            state.qnalist = payload.qnalist
        },
        setQnA(state, payload) {
            state.qna = payload.qna;
        },
        setComments(state, payload) {
            state.comments = payload.comments;
        }
    },

    actions: {
        getQnAList(context) {
            return axios.get('/api/v1/qnaList')
            .then(({ data }) => {
                context.commit("setQnAList", { qnalist: data })
            })
        },

        async getQnA({ commit }, payload) {
            let { data } = await axios.get(`/api/v1/qna/${payload.id}`)
            commit("setQnA", { qna : data })
        },

        async getComments({ commit }, payload) {
            let { data } = await axios.get(`/api/v1/comment/${payload.id}`)
            commit("setComments", { comments: data })
        },

    }
}

export default qnaStore;