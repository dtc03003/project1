import axios from "@/module/axios.js"

const followStore = {
    state: {
        isFollow: "",
        isLike: "",
        likeList: "",
        liker: "",
        countFollow: "",
        followerList: ""
    },

    mutations: {
        setIsFollow(state, payload) {
            state.isFollow = payload.isFollow;
        },
        setIsLike(state, payload) {
            state.isLike = payload.isLike;
        },
        setLikeList(state, payload) {
            state.likeList = payload.likeList;
        },
        setLiker(state, payload) {
            state.likeList = payload.likeList;
        },
        setCountFollow(state, payload) {
            state.countFollow = payload.countFollow;
        },
        setFollowerList(state, payload) {
            state.followerList = payload.followerList;
        }
    },

    actions: {
        // 팔로우 여부
        async getIsFollow({ commit }, payload) {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            let { data } = await axios.get(`/api/v1/isFollowed/${payload.nickname}`)
            commit("setIsFollow", { isFollow: data })
        },

        // 게시물 좋아요 여부
        async getIsLike({ commit }, payload) {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            let { data } = await axios.get(`/api/v1/like/isLiked/${payload.styleId}`)
            commit("setIsLike", { isLike: data })
        },

        // 사용자별 좋아요한 게시물 리스트
        async getLikeList({ commit }) {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            let { data } = await axios.get(`/api/v1/member/me/likes`)
            commit("setLikeList", { likeList: data })
        },

        // 게시물별 좋아요한 사용자 리스트
        async getLiker({ commit }, payload) {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            let { data } = await axios.get(`/api/v1/isFollowed/${payload.styleId}`)
            commit("setLiker", { liker: data })
        },

        // 사용자 팔로워 수
        async getCountFollow({ commit }, payload) {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            let { data } = await axios.get(`/api/v1/countOfFollower/${payload.nickname}`)
            commit("setCountFollow", { countFollow: data })
        },

        async getFollowerList({ commit }) {
            const accessToken = localStorage.getItem("accessToken");
            axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
            let { data } = await axios.get(`/api/v1/getFollowerList`);
            commit("setFollowerList", { followerList: data })
        }
    }
};

export default followStore;