<template>
  <div
    class="app-container"
    style="position: relative; height: calc(100vh - 117px)"
  >
    <div class="container" style="margin-top: -20px">
      <el-tabs v-model="status" type="card" @tab-click="handleOrder">
        <el-tab-pane name="-9">
          <span slot="label"><i class="el-icon-s-order"></i> 全部订单</span>
        </el-tab-pane>
        <el-tab-pane name="0">
          <span slot="label"><i class="el-icon-bank-card"></i> 未支付</span>
        </el-tab-pane>
        <el-tab-pane name="1">
          <span slot="label"><i class="el-icon-refrigerator"></i> 未发货</span>
        </el-tab-pane>
        <el-tab-pane name="2">
          <span slot="label"><i class="el-icon-truck"></i> 待收货</span>
        </el-tab-pane>
        <el-tab-pane name="3">
          <span slot="label"><i class="el-icon-document"></i> 待评价</span>
        </el-tab-pane>
        <el-tab-pane name="4">
          <span slot="label"
            ><i class="el-icon-circle-check"></i> 交易完成</span
          >
        </el-tab-pane>
        <el-tab-pane name="-1">
          <span slot="label"><i class="el-icon-back"></i> 退款中</span>
        </el-tab-pane>
        <el-tab-pane name="-2">
          <span slot="label"><i class="el-icon-finished"></i> 已退款</span>
        </el-tab-pane>
        <el-tab-pane name="-4">
          <span slot="label"><i class="el-icon-circle-close"></i> 已删除</span>
        </el-tab-pane>
      </el-tabs>
      <div class="head-container">
        <el-input
          v-model="query.value"
          clearable
          placeholder="输入搜索内容"
          style="width: 200px"
          class="filter-item"
          @keyup.enter.native="toQuery"
        />
        <el-select
          v-model="query.type"
          clearable
          placeholder="类型"
          class="filter-item"
          style="width: 150px; padding-left: 10px"
        >
          <el-option
            v-for="item in queryTypeOptions"
            :key="item.key"
            :label="item.display_name"
            :value="item.key"
          />
        </el-select>
        <el-button
          style="margin-left: 10px"
          class="filter-item"
          size="mini"
          type="success"
          icon="el-icon-search"
          @click="toQuery"
          >搜索</el-button
        >
        <el-button
          type="danger"
          class="filter-item"
          size="mini"
          icon="el-icon-refresh"
          @click="reset"
          >重置</el-button
        >
      </div>
      <!--订单数据统计-->
      <div class="order-caculate">
        <a class="caculate-title"
          >订单数 :
          <span class="caculate-num">{{ caculateInfo.orderNum }}</span></a
        >
        <a class="caculate-title"
          >商品数 :
          <span class="caculate-num">{{ caculateInfo.storeNum }}</span></a
        >
        <a class="caculate-title"
          >订单金额 :
          <span class="caculate-num">{{ caculateInfo.orderPrice }}</span></a
        >
        <a class="caculate-title"
          >客户数 :
          <span class="caculate-num">{{ caculateInfo.userNum }}</span></a
        >
      </div>
      <el-table
        ref="multipleTable"
        v-loading="loading"
        :data="tableData"
        size="small"
        style="width: 100%"
      >
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page.current"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="page.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="page.total"
        style="float: right; margin-top: 10px; padding-bottom: 10px"
      />
    </div>
  </div>
</template>

<script>
export default {
  name: "orderList",
  components: {},
  data() {
    return {
      delLoading: false,
      loading: false,
      tableData: [],
      status: "0",
      createTime: "",
      query: {
        value: "",
        type: "orderId",
      },
      queryTypeOptions: [
        { key: "orderId", display_name: "订单号" },
        { key: "realName", display_name: "收货人姓名" },
        { key: "phone", display_name: "收货人电话" },
      ],
      caculateInfo: {
        orderNum: 0,
        storeNum: 0,
        orderPrice: 0,
        userNum: 0,
      },
      page: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
    };
  },
  created() {},
  methods: {
    handleOrder(tab) {
      this.status = tab.name;
      this.toQuery();
    },
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.toQuery();
    },
    handleCurrentChange(val) {
      this.page.current = val;
      this.toQuery();
    },
  },
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
