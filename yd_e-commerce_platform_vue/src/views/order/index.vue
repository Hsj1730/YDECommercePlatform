<template>
  <div
    class="app-container"
    style="position: relative; height: calc(100vh - 117px)"
  >
    <div class="container"></div>
  </div>
</template>

<script>
export default {
  name: "orderList",
  components: {},
  data() {
    return {
      delLoading: false,
    };
  },
  created() {},
  methods: {},
};
</script>

<style scoped lang="scss">
.container {
  height: calc(100% - 80px);
  position: absolute;
  overflow: auto;
  width: calc(100% - 20px);

  .order-caculate {
    font-size: 14px;
    color: #909399;
    border-top: 1px solid #f0f0f0;
    padding: 20px 10px;
    .caculate-title {
      display: inline-block;
      margin-right: 50px;
    }
    .caculate-num {
      font-size: 20px;
      color: #ff4949;
    }
  }

  .el-table th {
    background-color: #fafafa;
  }
}

.footer-contains {
  position: absolute;
  display: -ms-flexbox;
  display: flex;
  background-color: #f6f6f6;
  bottom: 0;
  flex-align: center;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  z-index: 999;
  padding: 0 20px 0 13px;
}

/*打印单样式编辑*/
.order-list {
  /*  height: 297mm;*/
  margin: 0 auto;
  width: 210mm;

  .order-title {
    height: 16mm;
    line-height: 16mm;
    font-size: 8mm;
    font-weight: bolder;
    text-align: center;
  }
  .order-info {
    span {
      display: inline-block;
      padding: 0 0 10px 0;
      font-size: 3mm;
    }
    span.info {
      width: 60mm;
    }
  }
  .el-table--small th,
  .el-table--small td {
    padding: 4px 0;
  }
}
</style>
