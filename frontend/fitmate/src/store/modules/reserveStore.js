import { reservationList, reservationDetailList, mypayment } from '@/api/reserve.js'

const reserveStore = {
    namespaced: true,
    state: {
        reserveStatus: false,
        allReservations: [],
        newEvents: [],

        mypayments: [], //자신의 결제 성공 내역
        paymentsStatus: false,
    },
    getters: {
        getReservStatus: (state) => state.reserveStatus,
        getAllReservation: (state) => state.allReservations,
        getNewEvents: (state) => state.newEvents,

        getMyPayments: (state) => state.mypayments,
    },
    mutations: {
        SET_STATUS: (state, status) => state.reserveStatus = status,
        SET_ALL_RESERVATIONS: (state, reserv) => state.allReservations = reserv,
        SET_NEW_EVENTS: (state, events) => state.events = events,

        SET_MY_PAYMENTS: (state, payments) => state.mypayments = payments,
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
        },
        async importMyPayment({commit}) { //자신의 결제 내역 가져오기(결제 성공한 내역만)
            commit("SET_MY_PAYMENTS", []);
            await mypayment((response) => {
                if(response.status == 200) {
                    commit("SET_MY_PAYMENTS", response.data);
                }
            },
            () => {});
        }
    },
    modules: {
    
    }
}

export default reserveStore;