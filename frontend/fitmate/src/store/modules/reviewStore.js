import { findPortfolio, reviewAll, reviewByPage, uploadImage, writeReview, findReviewsById,
        findCountOfFollower, updateRatingOfFollwer} from '@/api/review.js'

/* 리뷰 관련 상태 관리(vuex) */
const reviewStore = {
    namespaced: true,
    state: {
        reviewStatus: false,
        portfolioStatus: false,
        portfolioData: [],
        reviews: [],
        someReviews: [],
        reviewStatusByUser: false,
        reviewsByUser: [],
        imagesrc: '',
        rvupload: false,
        rating: 0,
        waste: '',
    },
    getters: {
        getReviewStatus: (state) => state.reviewStatus,
        getPortfolioStatus: (state) => state.portfolioStatus,
        getReviews: (state) => state.reviews,
        getSomeReviews: (state) => state.someReviews,
        getPortfolioData: (state) => state.portfolioData,
        getReviewStatusByUser: (state) => state.reviewStatusByUser,
        getReviewsByUser: (state) => state.reviewsByUser,
        getImagesrc: (state) => state.imagesrc,
        getRvupload: (state) => state.rvupload,
        getRating: (state) => state.rating,
    },
    mutations: {
        SET_REVIEW_STATUS: (state, status) => state.reviewStatus = status,
        SET_PORTFOLIO_STATUS: (state, status) => state.portfolioStatus = status,
        SET_REVEIW_LIST: (state, reviews) => state.reviews = reviews,
        SET_SOME_REVEIW_LIST: (state, someReviews) => state.someReviews = someReviews,
        SET_PORTFOLIO_DATA: (state, portfolioData) => state.portfolioData = portfolioData,

        SET_REVIEW_STATUS_BY_USER: (state, status) => state.reviewStatusByUser = status,
        SET_REVIEWS_BY_USER: (state, reviews) => state.reviewsByUser = reviews,

        SET_IMAGE_SRC: (state, image) => state.imagesrc = image,
        SET_REVIEW_UPLOAD: (state, rvupload) => state.rvupload = rvupload,
        SET_RATING: (state, rating) => state.rating = rating,

        SET_WASTE: (state, waste) => state.waste = waste,
    },
    actions: {
        async importAllReviews({commit}, nickname) { //해당 스타일리스트의 모든 리뷰 가져오기
            await reviewAll(nickname, (response) => {
                if(response.status == 200) {
                    commit("SET_REVEIW_LIST", response.data);
                    commit("SET_REVIEW_STATUS", true);
                }
            },
            () => {});
        },
        async findPortfolioStatus({commit}, nickname) { //해당 스타일리스트 포트폴리오 존재 여부 확인
            commit("SET_PORTFOLIO_STATUS", false);
            commit("SET_PORTFOLIO_DATA", {});
            await findPortfolio(nickname, (response) => {
                if(response.status == 200) {
                    commit("SET_PORTFOLIO_STATUS", true);
                    commit("SET_PORTFOLIO_DATA", response.data);
                }
            },
            () => {});
        },
        async fineReviewsByPage({commit}, info) { //해당 스타일리스트의 페이지별 리뷰찾기
            await reviewByPage(info, (response) => {
                if(response.status == 200) {
                    commit("SET_SOME_REVEIW_LIST", response.data);
                }
            },
            () => {});
        },
        async findAllReviews({commit}, id) { //자신의 모든 리뷰 가져오기(사용자, 스타일리스트 상관X)
            commit("SET_REVIEW_STATUS_BY_USER", false);
            await findReviewsById(id, (response) => {
                if(response.status == 200) {
                    commit("SET_REVIEWS_BY_USER", response.data);
                    commit("SET_REVIEW_STATUS_BY_USER", true);
                }
            },
            () =>{});
        },
        async uploadRVImage({commit}, formData) { //이미지 업로드
            await uploadImage(formData, (response) => {
                if(response.status == 200) {
                    commit("SET_IMAGE_SRC", response.data.src);
                }
            },() => {});
        },
        async writeNewReview({commit}, info) { //리뷰쓰기
            commit("SET_REVIEW_UPLOAD", false);
            const reviewinfo = {
                content: info.content,
                rating: info.rating,
                thumbnail: info.thumbnail,
            }
            await writeReview(info.nickname, reviewinfo, (response) => {
                if(response.status == 200) {
                    commit("SET_REVIEW_UPLOAD", true);
                }
            },() => {});
        },
        async findCount({commit}, nickname) { //팔로워 수 찾기
            await findCountOfFollower(nickname, (response) => {
                if(response.status == 200) {
                    commit("SET_RATING", response.data);
                }
            },() => {});
        },
        async updateRating({commit}, info) { //평점 갱신
            await updateRatingOfFollwer(info, (response) => {
                if(response.status == 200) {
                    commit("SET_WASTE", '');
                }
            },() => {});
        }
    },
    modules: {

    }
};


export default reviewStore;