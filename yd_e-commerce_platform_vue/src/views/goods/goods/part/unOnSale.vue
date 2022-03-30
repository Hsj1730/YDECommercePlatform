<template>
  <div class="app-container">
    <div class="head-container">
      <!-- 搜索 -->
<!--      <el-input-->
<!--        v-model="query.value"-->
<!--        clearable-->
<!--        placeholder="输入搜索内容"-->
<!--        style="width: 200px"-->
<!--        class="filter-item"-->
<!--        @keyup.enter.native="toQuery"-->
<!--      />-->
    </div>
  </div>
</template>

<script>
export default {
  name: "unOnSale",
  mounted() {
    this.init();
  },
  methods: {
    init() {
      console.log(222222);
    },
  },
};
</script>

<style scoped></style>
