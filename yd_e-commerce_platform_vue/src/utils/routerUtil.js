import axios from "../axios/axios";
import router from "../router";
import store from "../store";

export function getMenuList() {
  // 刷新侧边菜单
  axios.post("user/getMenuList").then((res) => {
    // 动态绑定路由
    const newRouter = router.options.routes;
    store.commit("setMenuList", res.data.data);
    res.data.data.map((item) => {
      if (item.children) {
        item.children.map((m) => {
          const route = menuToRoute(m);
          if (route) {
            newRouter[0].children.push(route);
          }
        });
      }
      router.addRoutes(newRouter);
      store.commit("setHasMenus", true);
    });
  });
}

function menuToRoute(menu) {
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
}
