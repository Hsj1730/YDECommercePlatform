import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import axios from './axios'

import settings from './settings'

import rsa from './utils/rsa'

Vue.prototype.rsa = rsa

Vue.prototype.settings = settings

require('./mock') // 引入mock数据，关闭则注释该行

Vue.use(Element, {
  size: 'small',
  zIndex: 2000
})

Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
