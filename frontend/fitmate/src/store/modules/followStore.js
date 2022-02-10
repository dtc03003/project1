import axios from "@/module/axios.js"

const followStore = {
    state: {
        isFollow: "",
        likeList: "",
        liker: "",
    },

    mutations: {
        setIsFollow(state, payload) {
            state.isFollow = payload.isFollow;
        },
        setLikeList(state, payload) {
            state.likeList = payload.likeList;
        },
        setLiker(state, payload) {
            state.likeList = payload.likeList;
        }
    },

    actions: {
        async getIsFollow({ commit }, payload) {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            let { data } = await axios.get(`/api/v1/isFollowed/${payload.nickname}`)
            commit("setIsFollow", { isFollow: data })
        },

        async getLikeList({ commit }) {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            let { data } = await axios.get(`/api/v1/member/me/like`)
            commit("setLikeList", { likeList: data })
        },

        async getLiker({ commit }, payload) {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            let { data } = await axios.get(`/api/v1/isFollowed/${payload.styleId}`)
            commit("setLiker", { liker: data })
        },

        
    }
};

export default followStore;