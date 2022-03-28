<template>
  <el-tabs
    v-model="editableTabsValue"
    type="card"
    closable
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
  computed: {
    // 标签当前选中的值
    editableTabsValue: {
      get() {
        return this.$store.state.editableTabsValue;
      },
      set(val) {
        this.$store.state.editableTabsValue = val;
      },
    },
    // 标签的内容值
    editableTabs: {
      get() {
        return this.$store.state.editableTabs;
      },
      set(val) {
        this.$store.state.editableTabs = val;
      },
    },
  },
  methods: {
    fnClickTab(target) {
      if (target.name !== this.$route.path) {
        this.$router.push(target.name);
      }
    },
    fnRemoveTab(targetName) {
      const tabs = this.editableTabs;
      let activeName = this.editableTabsValue;
      // 首页不能删除
      if (activeName === "/index" || activeName === "index") {
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
      this.$router.push(activeName);
    },
  },
};
</script>

<style scoped></style>
