<template>
  <el-menu
    :default-active="$route.path"
    class="el-menu-vertical-demo side-menu"
    background-color="#545c64"
    text-color="#fff"
    active-text-color="#ffd04b"
  >
    <router-link to="/index">
      <el-menu-item
        index="/index"
        @click="selectMenu({ name: '/index', name: '首页', path: '/index' })"
      >
        <template slot="title">
          <i class="el-icon-s-home"></i>
          <span slot="title">首页</span>
        </template>
      </el-menu-item>
    </router-link>
    <div v-for="(menu, index) in menuList" :key="index">
      <el-submenu
        :index="menu.path + ''"
        v-if="
          menu.path === '' ||
          menu.path == null ||
          menu.component === '' ||
          menu.component == null
        "
      >
        <template slot="title">
          <i :class="menu.icon"></i>
          <svg-icon
            :icon-class="menu.icon"
            style="
              margin-right: 5px;
              width: 24px;
              fill: currentColor;
              text-align: center;
              font-size: 14px;
              vertical-align: middle;
            "
          />
          <span>{{ menu.name }}</span>
        </template>
        <router-link
          :to="item.path"
          v-for="item in menu.children"
          :key="item.path"
        >
          <el-menu-item :index="item.path + ''" @click="selectMenu(item)">
            <template slot="title">
              <svg-icon
                :icon-class="item.icon"
                style="
                  margin-right: 5px;
                  width: 24px;
                  fill: currentColor;
                  text-align: center;
                  font-size: 14px;
                  vertical-align: middle;
                "
              />
              <span slot="title">{{ item.name }}</span>
            </template>
          </el-menu-item>
        </router-link>
      </el-submenu>
      <router-link :to="menu.path" v-else>
        <el-menu-item :index="menu.path + ''" @click="selectMenu(menu)">
          <template slot="title">
            <svg-icon
              :icon-class="menu.icon"
              style="
                margin-right: 5px;
                width: 24px;
                fill: currentColor;
                text-align: center;
                font-size: 14px;
                vertical-align: middle;
              "
            />
            <span slot="title">{{ menu.name }}</span>
          </template>
        </el-menu-item>
      </router-link>
    </div>
  </el-menu>
</template>

<script>
export default {
  name: "sideMenu",
  computed: {
    // 菜单列表
    menuList: {
      get() {
        return this.$store.state.menuList;
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

<style scoped>
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
