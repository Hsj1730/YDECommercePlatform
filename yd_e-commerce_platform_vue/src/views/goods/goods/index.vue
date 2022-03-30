<template>
  <el-tabs v-model="activeName" style="margin-top: -15px" @tab-click="tabClick">
    <el-tab-pane label="出售中产品" name="first">
      <on-sale ref="onSale" />
    </el-tab-pane>
    <el-tab-pane label="待上架产品" name="second">
      <un-on-sale ref="unOnSale" />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import "@/styles/description.scss";
import onSale from "./part/onSale";
import unOnSale from "./part/unOnSale";
export default {
  components: { onSale, unOnSale },
  name: "goods",
  data() {
    return {
      activeName: "first",
    };
  },
  methods: {
    tabClick() {
      if (this.activeName === "first") {
        this.$refs.onSale.init();
      } else if (this.activeName === "second") {
        this.$refs.unOnSale.init();
      }
    },
  },
};
</script>

<style scoped></style>
