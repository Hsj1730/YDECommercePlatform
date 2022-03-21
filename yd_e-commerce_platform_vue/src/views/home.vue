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
      <side-menu class="side-menu" />
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
              <el-dropdown-item :underline="false">
                <router-link to="/" style="font-size: 16px"
                  >&nbsp;首页</router-link
                >
              </el-dropdown-item>
              <el-dropdown-item :underline="false">
                <router-link
                  :to="{ name: 'userCenter' }"
                  style="font-size: 16px"
                  >&nbsp;个人中心</router-link
                >
              </el-dropdown-item>
              <el-dropdown-item @click.native="logout" style="font-size: 16px"
                >&nbsp;退出</el-dropdown-item
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
import sideMenu from "./inc/sideMenu";
import tabs from "./inc/tabs";
import { removeToken } from "../utils/auth";
export default {
  name: "home",
  components: {
    sideMenu: sideMenu,
    tabs: tabs,
  },
  data() {
    return {
      userInfo: {
        id: "",
        nickname: "",
        avatar: "",
      },
    };
  },
  mounted() {
    this.getUserInfo();
  },
  methods: {
    getUserInfo() {
      this.$axios.post("/user/getLoginUserInfo").then((res) => {
        this.userInfo = res.data.data;
      });
    },
    logout() {
      this.$axios.post("/logout").then((res) => {
        // TODO 后期可以rm
        localStorage.clear();
        removeToken();
        sessionStorage.clear();
        this.$message.success(res.data.msg);
        this.$store.commit("resetState");
        this.$router.push("/login");
        location.reload();
      });
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
</style>
