import axios from "@/module/axios.js"

const noticeStore = {

    state: {
        // 공지 리스트
        noticelist: [],

        // 공지 하나
        notice: {}
    },

    mutations: {
        setNoticeList(state, payload) {
            state.noticelist = payload.noticelist;
        },
        setNotice(state, payload) {
            state.notice = payload.notice;
        }
    },

    actions: {
        // 리스트 받아오기
        getNoticeList(context) {
            return axios.get("/api/v1/noticeList")
                .then(({ data }) => {
                    context.commit("setNoticeList", {noticelist : data})
                })
        },

        // 공지 하나 받아오기
        async getNotice({ commit }, payload) {
            let { data } = await axios.get(`/api/v1/notice/${payload.id}`)
            commit("setNotice", { notice: data });
        },
    },
};

export default noticeStore;