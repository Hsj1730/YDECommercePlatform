<template>
  <!--  <el-menu-->
  <!--    :default-active="this.$store.state.menus.editableTabsValue"-->
  <!--    class="el-menu-vertical-demo"-->
  <!--    background-color="#545c64"-->
  <!--    text-color="#fff"-->
  <!--    active-text-color="#ffd04b">-->

  <!--    <router-link to="/index">-->
  <!--      <el-menu-item index="Index" @click="selectMenu({name: 'Index', title: '首页'})">-->
  <!--        <template slot="title">-->
  <!--          <i class="el-icon-s-home"></i>-->
  <!--          <span slot="title">首页</span>-->
  <!--        </template>-->
  <!--      </el-menu-item>-->
  <!--    </router-link>-->
  <!--    <el-submenu :index="menu.name" v-for="menu in menuList" :key="menu.name">-->
  <!--      <template slot="title">-->
  <!--        <i :class="menu.icon"></i>-->
  <!--        <span>{{menu.title}}</span>-->
  <!--      </template>-->
  <!--      <router-link :to="item.path" v-for="item in menu.children" :key="item.path">-->
  <!--        <el-menu-item :index="item.name" @click="selectMenu(item)">-->
  <!--          <template slot="title">-->
  <!--            <i :class="item.icon"></i>-->
  <!--            <span slot="title">{{item.title}}</span>-->
  <!--          </template>-->
  <!--        </el-menu-item>-->
  <!--      </router-link>-->
  <!--    </el-submenu>-->
  <!--  </el-menu>-->
  <el-menu
    class="el-menu-vertical-demo"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
  >
    <router-link to="/index">
      <el-menu-item index="0">
        <template slot="title">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </template>
      </el-menu-item>
    </router-link>
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-s-operation"></i>
        <span>系统管理</span>
      </template>
      <router-link to="/manage/user">
        <el-menu-item index="1-1">
          <template slot="title">
            <i class="el-icon-s-custom"></i>
            <span slot="title">用户管理</span>
          </template>
        </el-menu-item>
      </router-link>
      <router-link to="/manage/role">
        <el-menu-item index="1-2">
          <template slot="title">
            <i class="el-icon-rank"></i>
            <span slot="title">角色管理</span>
          </template>
        </el-menu-item>
      </router-link>
      <router-link to="/manage/menu">
        <el-menu-item index="1-3">
          <template slot="title">
            <i class="el-icon-menu"></i>
            <span slot="title">菜单管理</span>
          </template>
        </el-menu-item>
      </router-link>
      <router-link to="/manage/dict">
        <el-menu-item index="1-4">
          <template slot="title">
            <i class="el-icon-s-order"></i>
            <span slot="title">字典管理</span>
          </template>
        </el-menu-item>
      </router-link>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "sideMenu",
  data() {
    return {};
  },
  computed: {
    menuList: {
      get() {
        return this.$store.state.menus.menuList;
      },
    },
  },
  methods: {
    selectMenu(item) {
      this.$store.commit("addTab", item);
    },
  },
};
</script>

<style scoped type="text/css">
.el-menu-vertical-demo {
  height: 100vh;
  font-size: 24px;
  font-weight: bolder;
}
</style>
