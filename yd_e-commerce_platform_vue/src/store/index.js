import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: "",
    menuList: [], // 菜单列表
    permList: [],
    hasMenus: false, // 判断是否已加载菜单
    editableTabsValue: "Index",
    editableTabs: [
      {
        title: "首页",
        name: "/index",
      },
    ],
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
    // 设置菜单的值 state 相当于this menuList 传的值
    setMenuList(state, menuList) {
      state.menuList = menuList;
    },
    setPermList(state, perms) {
      state.permList = perms;
    },
    setHasMenus(state, hasMenus) {
      state.hasMenus = hasMenus;
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

      state.hasMenus = false;
      state.editableTabsValue = "Index";
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
