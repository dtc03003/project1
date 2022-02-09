import { reservationList, reservationDetailList } from '@/api/reserve.js'

const reserveStore = {
    namespaced: true,
    state: {
        reserveStatus: false,
        allReservations: [],
        newEvents: [],
    },
    getters: {
        getReservStatus: (state) => state.reserveStatus,
        getAllReservation: (state) => state.allReservations,
        getNewEvents: (state) => state.newEvents,
    },
    mutations: {
        SET_STATUS: (state, status) => state.reserveStatus = status,
        SET_ALL_RESERVATIONS: (state, reserv) => state.allReservations = reserv,
        SET_NEW_EVENTS: (state, events) => state.events = events,
    },
    actions: {
        async getReservList({ commit }, nickname) { //전체 예약 내역 가져오기
            await reservationList(nickname, (response) => {
                if(response.status == 200) {
                    commit("SET_STATUS", true)
                    commit("SET_ALL_RESERVATIONS", response.data);
                }
            },
            () => {});
        },
        async getReservDetailList({ commit }, nickname) { //전체 예약 디테일 내역 가져오기
            await reservationDetailList(nickname, (response) => {
                if(response.status == 200) {
                    commit("SET_STATUS", true)
                    commit("SET_ALL_RESERVATIONS", response.data);
                }
            },
            () => {});
        }
    },
    modules: {
    
    }
}

export default reserveStore;