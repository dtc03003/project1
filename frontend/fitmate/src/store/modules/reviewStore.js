import { findPortfolio, reviewAll } from '@/api/review.js'

/* 리뷰 관련 상태 관리(vuex) */
const reviewStore = {
    namespaced: true,
    state: {
        portfolioStatus: false,
        reviews: {},

    },
    getters: {
        getPortfolioStatus: (state) => state.portfolioStatus,
        getReviews: (state) => state.reviews,
    },
    mutations: {
        SET_PORTFOLIO_STATUS: (state, status) => state.portfolioStatus = status,
        SET_REVEIW_LIST: (state, reviews) => state.reviews = reviews,
    },
    actions: {
        async importAllReviews({commit}, nickname) {
            await reviewAll(nickname, (response) => {
                if(response.status == 200) {
                    commit("SET_REVEIW_LIST", response.data);
                }
            },
            (error) => {
                console.log(error);
            });
        },
        async findPortfolioStatus({commit}, nickname) {
            await findPortfolio(nickname, (response) => {
                if(response.status == 200) {
                    commit("SET_PORTFOLIO_STATUS", true);
                }
            },
            (error) => {
                console.log(error);
            });
        }
    },
    modules: {

    }
};


export default reviewStore;