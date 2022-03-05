import axios from "@/module/axios.js"
import memberStore from "./memberStore";

// const BACK_URL = 'http://i6d105.p.ssafy.io'
const BACK_URL = 'http://localhost:8080/'
const styleStore = {
  state: {
    styles: []
  },

  mutations: {
    GET_PORTFOLIO_STYLES:function(state, data){
      state.styles=[]
      data.forEach(data => {
        const style = {
          // namespace:data.namespace,
          id: data.id,
          about: data.about,
          member: data.member
          // content: data.content,
          // thumbnail: data.thumbnail,
          // createdAt: data.createdAt,
          // portfolio: data.portfolio
        }
        state.styles.push(style)
      });
    }
  },

  actions: {
    getPortfolioStyles: function ({commit}) {
      axios({
        method:'get',
        url: `${BACK_URL}/api/v1/portfolios/`,
        headers: memberStore.state.accessToken
      })
        .then( res=> {
          commit('GET_PORTFOLIO_STYLES', res.data)
        })
        .catch(() => {})
    }
  },
};

export default styleStore;