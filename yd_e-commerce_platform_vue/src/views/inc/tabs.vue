<template>
  <el-tabs
    v-model="editableTabsValue"
    type="card"
    closable="closable"
    @tab-remove="fnRemoveTab"
    @tab-click="fnClickTab"
  >
    <el-tab-pane
      v-for="item in editableTabs"
      :key="item.name"
      :label="item.title"
      :name="item.name"
    />
  </el-tabs>
</template>

<script>
export default {
  name: "tabs",
  data() {
    return {};
  },
  computed: {
    editableTabs: {
      get() {
        return this.$store.state.menus.editableTabs;
      },
      set(val) {
        this.$store.state.menus.editableTabs = val;
      },
    },
    editableTabsValue: {
      get() {
        return this.$store.state.menus.editableTabsValue;
      },
      set(val) {
        this.$store.state.menus.editableTabsValue = val;
      },
    },
  },
  methods: {
    fnClickTab(target) {
      if (target.name !== this.$route.path) {
        this.$router.push({ name: target.name });
      }
    },
    fnRemoveTab(targetName) {
      const tabs = this.editableTabs;
      let activeName = this.editableTabsValue;
      // 首页不能删除
      if (activeName === "Index") {
        return;
      }
      if (activeName === targetName) {
        tabs.forEach((tab, index) => {
          if (tab.name === targetName) {
            const nextTab = tabs[index + 1] || tabs[index - 1];
            if (nextTab) {
              activeName = nextTab.name;
            }
          }
        });
      }
      this.editableTabsValue = activeName;
      this.editableTabs = tabs.filter((tab) => tab.name !== targetName);
      this.$router.push({ name: activeName });
    },
  },
};
</script>

<style scoped></style>
