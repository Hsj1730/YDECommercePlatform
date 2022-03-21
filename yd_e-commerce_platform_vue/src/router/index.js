import Vue from "vue";
import VueRouter from "vue-router";
import home from "../views/home";
import index from "../views/index";
import axios from "../axios/axios";
import store from "../store";
import Element from "element-ui";
import { getToken } from "../utils/auth";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "home",
    component: home,
    redirect: "/index",
    children: [
      {
        path: "/index",
        name: "index",
        meta: {
          title: "首页",
        },
        component: index,
      },
      {
        path: "/userCenter",
        name: "userCenter",
        meta: {
          title: "个人中心",
        },
        component: () => import("../views/manage/center/index"),
      },
    ],
  },
  {
    title: "登录",
    path: "/login",
    name: "login",
    component: () => import("../views/login"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

// 路由控制
router.beforeEach((to, from, next) => {
  const token = getToken();

  if (to.meta.title) {
    document.title = to.meta.title;
  }

  let hasMenus = store.state.hasMenus;
  if (to.path === "/login") {
    next();
  } else if (!token) {
    Element.Message.error("请先登录");
    next({ path: "/login" });
  } else if (token && !hasMenus) {
    // 判断是否已经有查出菜单列表
    axios.post("user/getMenuList").then((res) => {
      // 动态绑定路由
      const newRouter = router.options.routes;

      store.commit("setMenuList", res.data);

      res.data.map((item) => {
        if (item.children) {
          item.children.map((m) => {
            const route = menuToRoute(m);
            if (route) {
              newRouter[0].children.push(route);
            }
          });
        }
        router.addRoutes(newRouter);
        hasMenus = true;
        store.commit("setHasMenus", hasMenus);
      });
    });
  }
  next();
});

// 导航转成路由
const menuToRoute = (menu) => {
  if (!menu.path) {
    return null;
  }
  const route = {
    name: menu.name,
    path: menu.path,
    meta: {
      icon: menu.icon,
      title: menu.name,
    },
  };
  route.component = () => import("@/views/" + menu.component + ".vue");
  return route;
};

export default router;
