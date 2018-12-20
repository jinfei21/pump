import {api} from '../../api'

const state = {
    appId: '',
    env: '',
    isCollapse: false
};

const getters = {
    getAppId: state => state.appId,
    getEnv: state => state.env,
    getCollapseState: state => state.isCollapse
};

const actions = {

};

const mutations = {
    refreshAppId(state, data) {
        state.appId = data;
    },
    refreshEnv(state, data) {
        state.env = data;
    },
    refreshCollapseState(state, data) {
        state.isCollapse = data;
    }
};

export default {
    state,
    getters,
    actions,
    mutations
}