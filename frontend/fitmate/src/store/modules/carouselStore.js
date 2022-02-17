import { findAllCarousel } from "@/api/carousel.js";

const carouselStore = {
  namespaced: true,
  state: {
    carouselSrcList: [],
  },

  mutations: {
    SET_CAROUSEL_IMG: (state, imgSrcList) => {
      state.carouselSrcList = [];
      imgSrcList.forEach((imgSrc) => {
        state.carouselSrcList.push({
          id: imgSrc.id,
          img: imgSrc.imgSrc,
        });
      });
    },
  },

  actions: {
    getCarouselImgSrc: ({ commit }) => {
      findAllCarousel(
        ({ data }) => {
          commit("SET_CAROUSEL_IMG", data);
        },
        () => {}
      );
    },
  },
};

export default carouselStore;
