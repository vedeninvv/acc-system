import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router'
import axios from "axios";

Vue.config.productionTip = false

const accessToken = localStorage.getItem('accessToken')
if (accessToken) {
    axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`
}

new Vue({
    vuetify,
    router,
    render: h => h(App)
}).$mount('#app')
