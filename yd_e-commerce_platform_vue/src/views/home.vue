<template>
  <el-container>
    <!--左边侧栏菜单项-->
    <el-aside width="220px;">
      <div
        style="
          text-align: center;
          background-color: #274993;
          color: #fff;
          padding: 16px 10px 16px 10px;
          font-size: 24px;
        "
      >
        亿点电子商务平台
      </div>

      <side-menu />
    </el-aside>

    <el-container>
      <!--头部工具栏-->
      <el-header>
        <div
          style="
            float: right;
            display: flex;
            justify-content: space-around;
            align-items: center;
          "
        >
          <el-avatar
            class="el-avatar"
            size="medium"
            :src="userInfo.avatar"
            style="margin-right: 3px"
          ></el-avatar>
          &nbsp;&nbsp;
          <el-dropdown>
            <span class="el-dropdown-link" style="font-size: 16px">
              {{ userInfo.nickname
              }}<i class="el-icon-arrow-down el-icon--right" />
            </span>
            <el-dropdown-menu slot="dropdown">
              <router-link to="/">
                <el-dropdown-item
                  @click.native="
                    selectMenu({ name: '/index', name: '首页', path: '/index' })
                  "
                  style="font-size: 16px"
                  ><svg-icon
                    icon-class="dashboard"
                    style="margin-right: 8px"
                  />首页</el-dropdown-item
                >
              </router-link>
              <router-link to="/user/userCenter">
                <el-dropdown-item
                  @click.native="
                    selectMenu({
                      name: '/user/userCenter',
                      name: '个人中心',
                      path: '/user/userCenter',
                    })
                  "
                  style="font-size: 16px"
                  ><svg-icon
                    icon-class="user"
                    style="margin-right: 8px"
                  />个人中心</el-dropdown-item
                >
              </router-link>
              <el-dropdown-item @click.native="logout" style="font-size: 16px"
                ><svg-icon
                  icon-class="logout"
                  style="margin-right: 8px"
                />&nbsp;退出</el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>
          <el-link
            v-for="item in this.settings.headerLink"
            :key="item.id"
            :href="item.url"
            target="_blank"
            style="font-size: 16px; font-weight: bolder"
          >
            &nbsp;{{ item.describe }}&nbsp;
          </el-link>
        </div>
      </el-header>
      <el-main>
        <!--标签页-->
        <tabs />
        <div style="margin: 0 15px">
          <router-view />
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import store from "../store";
import tabs from "./inc/tabs";
import sideMenu from "./inc/sideMenu";
import { removeToken } from "../utils/auth";
export default {
  name: "home",
  components: {
    tabs,
    sideMenu,
  },
  mounted() {
    this.getUserInfo();
  },
  computed: {
    // 菜单列表
    userInfo: {
      get() {
        return this.$store.state.userInfo;
      },
    },
  },
  methods: {
    getUserInfo() {
      this.$axios.post("/user/getLoginUserInfo").then((res) => {
        const userInfo = res.data.data;
        store.commit("setUserInfo", userInfo);
      });
    },
    logout() {
      this.$axios.post("/logout").then((res) => {
        removeToken();
        sessionStorage.clear();
        this.$message.success(res.data.msg);
        this.$store.commit("resetState");
        this.$router.push("/login");
        // location.reload(); // 为了重新实例化vue-router对象 避免bug
      });
    },
    selectMenu(item) {
      this.$store.commit("addTab", item);
    },
  },
};
</script>

<style scoped type="text/css">
.el-container {
  padding: 0;
  margin: 0;
  height: 100vh;
}

.header-avatar {
  float: right;
  width: 210px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.el-dropdown-link {
  cursor: pointer;
}

.el-main {
  color: #333;
  padding: 0;
}

.el-header {
  background-color: #17b3a3;
  color: #333;
  text-align: center;
  line-height: 60px;
  font-size: 20px;
  font-weight: bolder;
}

a {
  text-decoration: none;
}

.side-menu {
  width: 213px;
  position: fixed;
}

.el-menu-vertical-demo {
  height: 100vh;
  font-size: 24px;
  font-weight: bolder;
}
</style>
