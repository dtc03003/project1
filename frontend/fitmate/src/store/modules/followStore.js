import axios from "@/module/axios.js"

const followStore = {
    state: {
        isFollow: ""
    },

    mutations: {
        setIsFollow(state, payload) {
            state.isFollow = payload.isFollow;
        }
    },

    actions: {
        async getIsFollow({ commit }, payload) {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

            let { data } = await axios.get(`/api/v1/isFollowed/${payload.nickname}`)
            commit("setIsFollow", { isFollow: data })
        }
    }
};

export default followStore;