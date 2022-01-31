
const orderStore = {
    namespaced: true,
    state: {
        date: '',
        time: '',
    },
    getters: {
        getDate: (state) => state.date,
        getTime: (state) => state.time,

    },
    mutations: {
        SET_DATE: (state, date) => { state.date = date; },
        SET_TIME: (state, time) => { state.time = time; },
    },
    actions: {
        setDate({commit}, date) {
            commit("SET_DATE", date); 
        },
        setTime({commit}, time) {
            commit(this.SET_TIME, time); 
        },
    }
}

export default orderStore;