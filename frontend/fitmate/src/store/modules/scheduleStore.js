

const scheduleStore = {
    namespaced: true,
    state: {
        enroll: false,
    },
    getters: {
        getEnroll: (state) => { state.enroll },

    },
    mutations: {
        SET_ENROLL_STATUE: (state, enroll) => {state.enroll = enroll},
        
    },
    actions: {
    }
};

export default scheduleStore;