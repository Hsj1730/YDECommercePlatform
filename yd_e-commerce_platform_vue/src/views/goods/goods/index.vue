<template>
  <el-tabs v-model="activeName" style="margin-top: -15px" @tab-click="tabClick">
    <el-tab-pane label="出售中产品" name="first">
      <on-sale ref="onSale" v-if="isFirst" />
    </el-tab-pane>
    <el-tab-pane label="待上架产品" name="second">
      <un-on-sale ref="unOnSale" v-if="isSecond" />
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
      isFirst: true,
      isSecond: false,
    };
  },
  methods: {
    tabClick() {
      if (this.activeName === "first") {
        this.isFirst = true;
        this.isSecond = false;
      } else if (this.activeName === "second") {
        this.isFirst = false;
        this.isSecond = true;
      }
    },
  },
};
</script>

<style scoped></style>
