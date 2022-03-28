<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "app",
  mounted() {
    // 在页面加载时读取sessionStorage
    if (sessionStorage.getItem("store")) {
      this.$store.replaceState(
        Object.assign(
          {},
          this.$store.state,
          JSON.parse(sessionStorage.getItem("store"))
        )
      );
    }
    // 在页面刷新时将store保存到sessionStorage里
    window.addEventListener("beforeunload", () => {
      const editableTabsValue = this.$store.state.editableTabsValue;
      const editableTabs = this.$store.state.editableTabs;
      for (let i = 1; i < editableTabs.length; i++) {
        if (editableTabsValue !== editableTabs[i].name) {
          editableTabs.splice(i, 1);
          i--;
        }
      }
      sessionStorage.setItem("store", JSON.stringify(this.$store.state));
    });
  },
};
</script>

<style>
html,
body,
#app {
  font-family: "华文楷体", "Helvetica Neue", "Hiragino Sans GB",
    "WenQuanYi Micro Hei", "Microsoft Yahei", sans-serif;
  height: 100vh;
  padding: 0;
  margin: 0;
  font-size: 16px;
}
</style>
