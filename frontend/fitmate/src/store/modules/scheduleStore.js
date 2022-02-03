

const scheduleStore = {
    namespaced: true,
    state: {
        selectedOpen: false,
        date: "",

    },
    getters: {
        getSelectedOpen: (state) => state.selectedOpen,
        getSelectedDate: (state) => state.date,
    },
    mutations: {
        SET_SELECTED_OPEN: (state, selectedOpen) => state.selectedOpen = selectedOpen,
        SET_SELECTED_DATE: (state, date) => state.date = date,
    },
    actions: {
    }
};

export default scheduleStore;