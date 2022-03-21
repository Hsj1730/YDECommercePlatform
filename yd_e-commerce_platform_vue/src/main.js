import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";

import Element from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

import axios from "./axios/axios";

import Cookies from "js-cookie";

import settings from "./settings";

import rsa from "./utils/ras";

import dataValidate from "./utils/validate";

import "./styles/index.scss";

import "normalize.css/normalize.css"; // A modern alternative to CSS resets

import "./icons"; // icon

Vue.prototype.rsa = rsa;

Vue.prototype.settings = settings;

Vue.prototype.dataValidate = dataValidate;

// require("./mock/mock"); // 引入mock数据，关闭则注释该行

Vue.use(Element, {
  size: "small",
  zIndex: 2000,
});

Vue.prototype.$axios = axios;
Vue.prototype.Cookies = Cookies;
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
