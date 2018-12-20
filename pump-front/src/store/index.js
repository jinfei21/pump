import Vue from 'vue'
import Vuex from 'vuex'
import demo from './model/demo'
import pump from './model/pump'
import gravitee from 'gravitee-vue-support/store/gravitee'

Vue.use(Vuex);

const debug_mode = process.env.NODE_ENV !== 'production';

export default new Vuex.Store({
    modules: {
        demo,
        pump,
        gravitee
    },
    strict: debug_mode
})
