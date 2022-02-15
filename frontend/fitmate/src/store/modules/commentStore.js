// import axios from "@/module/axios.js"
// import {FITMATE_BASE_URL} from '@/config'

// const commentStore = {
//   state: {
//     comments:[]
//   },

//   mutations: {
//     UPDATE_COMMENT:function(state, data){
//       console.log('여기는 뮤테이션')
//       state.comments = data
//     }
//   },

//   actions: {
//     // 
//     updateComment:function(context, imageId){
//       console.log('이것은 액션에서 일어나는 일')
//       const accessToken = localStorage.getItem("accessToken");
//       axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;
//       axios.get(`${FITMATE_BASE_URL}/api/v1/portfolio/style/${imageId.id}/comments/all`)
//       .then(({ data })=> {    
//         context.commit('UPDATE_COMMENT', data)
//       })
      
//     }
//   }
// };

// export default commentStore;