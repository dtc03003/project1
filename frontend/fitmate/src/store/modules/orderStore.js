import { kakaopay } from '@/api/kakaopay.js'

const orderStore = {
    namespaced: true,
    state: {
        date: '',
        time: '',
        payStatus: false, //임시
    },
    getters: {
        getDate: (state) => state.date,
        getTime: (state) => state.time,

    },
    mutations: {
        SET_DATE: (state, date) => { state.date = date; },
        SET_TIME: (state, time) => { state.time = time; },
        SET_PAY: (state, payStatus) => {state.payStatus = payStatus}, //임시
    },
    actions: {
        setDate({commit}, date) {
            commit("SET_DATE", date); 
        },
        setTime({commit}, time) {
            commit(this.SET_TIME, time); 
        },
        async requestKakaoPay({ commit }, payinfo) { //카카오 결제 요청
            console.log("요청 store");
            console.log(payinfo);
            await kakaopay(payinfo, (response) => {
                if(response.status == 200) {
                    commit("SET_PAY", true);
                    console.log(response);
                }else {
                    console.log(response);
                }
            },
            (error) => {
                console.log(error);
            });
        },
    }
}

export default orderStore;