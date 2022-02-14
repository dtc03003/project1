import { enrollReservation, deleteReservation, readypay, finpay } from '@/api/reserve.js'

const orderStore = {
    namespaced: true,
    state: {
        id: '',
        date: '',
        time: '',
        reserveStatus: false,
        payStatus: false,
        pc_url: "",
        mobile_url: "",
        app_url: "",
        orderData: "",
        styleList: "",
    },
    getters: {
        getID: (state) => state.id,
        getDate: (state) => state.date,
        getTime: (state) => state.time,
        getReserveStatus: (state) => state.reserveStatus,
        getPayStatus: (state) => state.payStatus,
        getPCUrl: (state) => state.pc_url,
        getMobileUrl: (state) => state.mobile_url,
        getAPPUrl: (state) => state.app_url,
        getOrderData: (state) => state.orderData,
        getStyleList: (state) => state.styleList,

    },
    mutations: {
        SET_ID: (state, id) => { state.id = id; },
        SET_DATE: (state, date) => { state.date = date; },
        SET_TIME: (state, time) => { state.time = time; },
        SET_RESERVE_STATUS: (state, reserveStatus) => { state.reserveStatus = reserveStatus; },
        SET_PAY_STATUS: (state, status) => { state.payStatus = status },
        SET_PC_URL: (state, pc_url) => { state.pc_url = pc_url },
        SET_MOBILE_URL: (state, mobile_url) => { state.mobile_url = mobile_url },
        SET_APP_URL: (state, app_url) => { state.app_url = app_url },
        SET_ORDER_DATA: (state, data) => {state.orderData = data},
        SET_STYLELIST: (state, styleList) => {state.styleList = styleList},
    },
    actions: {
        setDate({commit}, date) {
            commit("SET_DATE", date); 
        },
        setTime({commit}, time) {
            commit(this.SET_TIME, time); 
        },
        async registOrder({commit}, info) { //예약 등록
            const reserveInfo = {
                "cost": info.cost,
                "startTime": info.startTime,
                "endTime": info.endTime,
                "state": "IN_PROGRESS"
            }
            await enrollReservation(info.nickname, reserveInfo, (response) => {
                if(response.status == 200) {
                    commit("SET_ORDER_DATA", response.data);
                    commit("SET_RESERVE_STATUS", true);
                    commit("SET_ID", response.data.id);
                    commit("SET_PC_URL", '');
                    commit("SET_MOBILE_URL", '');
                    commit("SET_APP_URL", '');
                }
            },
            () => { commit("SET_RESERVE_STATUS", false);});
        },
        async deleteOrder({commit}, info) { //예약 취소
            await deleteReservation(info.id, (response) => {
                if(response.status == 200) {
                    commit("SET_ORDER_DATA", []);
                    commit("SET_RESERVE_STATUS", false);
                    commit("SET_ID", "");
                }
            },
            () => {});
        },
        async requestKakaoPay({commit}, payinfo) { //카카오 결제 요청
            await readypay(payinfo, (response) => {
                if(response.status == 200) {
                    commit("SET_PC_URL", response.data.next_redirect_pc_url);
                    commit("SET_MOBILE_URL", response.data.next_redirect_mobile_url);
                    commit("SET_APP_URL", response.data.next_redirect_app_url);
                    commit("SET_PAY_STATUS", true);

                }
            },
            () => { commit("SET_PAY_STATUS", false); });
        },
        async approvalPay({commit}, payinfo) { //결제 승인 완료
            await finpay(payinfo, (response) => {
                if(response.status == 200) {
                    commit("SET_PAY_STATUS", false); //결제 끝났으니 끝!
                    commit("SET_RESERVE_STATUS", false); //예약 상태도 끝!
                    commit("SET_ORDER_DATA", []);
                }
            },
            () => {});
        },
    }
}

export default orderStore;