import axios from "@/module/axios.js"

const qnaStore = {

    state: {
        qna: {},
        comments: {}
    },

    mutations: {
        setQnA(state, payload) {
            state.qna = payload.qna;
        },
        setComments(state, payload) {
            state.comments = payload.comments;
        }
    },

    actions: {
        async getQnA({ commit }, payload) {
            let { data } = await axios.get(`/api/v1/qna/${payload.id}`)
            commit("setQnA", { qna : data })
        },

        async getComments({commit}, payload) {
            let { data } = await axios.get(`/api/v1/comment/${payload.id}`)
            console.log(data);
            commit("setComments", { comments: data })
        },

        // getComments(context) {
        //     return axios.get(`/api/v1/comment/${payload.id}`)
        //         .then(({ data }) => {
        //             console.log(data);
        //             context.commit("setComments", { comments : data })
        //     })
        // },

    }
}

export default qnaStore;