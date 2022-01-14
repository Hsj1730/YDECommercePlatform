import Vue from 'vue'
import App from './App.vue'
import Element from 'element-ui'
import router from './router/index'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use( Element, { size: 'small', zIndex: 3000 } )

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  render: h => h(App),
})// .$mount('#app')
