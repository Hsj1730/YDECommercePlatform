import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: "",
    menuList: [], // 菜单列表
    hasMenus: false, // 判断是否已加载菜单
    editableTabsValue: "index",
    editableTabs: [
      {
        title: "首页",
        name: "/index",
      },
    ],
    userInfo: {},
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    // 设置菜单的值 state 相当于this menuList 传的值
    setMenuList(state, menuList) {
      state.menuList = menuList;
    },
    setHasMenus(state, hasMenus) {
      state.hasMenus = hasMenus;
    },
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    },
    addTab(state, val) {
      let index = state.editableTabs.findIndex(
        (item) => item.name === val.path
      );
      if (index === -1) {
        let tabs = {
          title: val.name,
          name: val.path,
        };
        state.editableTabs.push(tabs);
      }
      state.editableTabsValue = val.path;
    },
    resetState: (state) => {
      state.menuList = [];
      state.token = "";
      state.userId = {};
      state.hasMenus = false;
      state.editableTabsValue = "index";
      state.editableTabs = [
        {
          title: "首页",
          name: "/index",
        },
      ];
    },
  },
  actions: {},
  modules: {},
});
