import Vue from 'vue'

import Vuex from 'vuex'
import VueRouter from 'vue-router'
import ElementUI from 'element-ui'
import 'jquery'
import axios from 'axios'
import router from './router'
import store from './store'
// import 'element-ui/lib/theme-chalk/index.css'
import './assets/theme/orange/index.css'

Vue.use(Vuex)
Vue.use(VueRouter);
Vue.use(ElementUI);

Vue.prototype.$http = axios;

Vue.config.devtools = process.env.NODE_ENV !== 'production';

new Vue({
    store,
    router,
}).$mount('#app');

