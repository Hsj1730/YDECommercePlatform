<template>
  <div class="app-container" style="margin-top: -20px">
    <div class="head-container">
      <el-tabs v-model="status" type="card" @tab-click="handleOrder">
        <el-tab-pane name="9">
          <span slot="label"><i class="el-icon-s-order"></i> 全部订单</span>
        </el-tab-pane>
        <el-tab-pane name="8">
          <span slot="label"><i class="el-icon-bank-card"></i> 未支付</span>
        </el-tab-pane>
        <el-tab-pane name="0">
          <span slot="label"><i class="el-icon-refrigerator"></i> 待发货</span>
        </el-tab-pane>
        <el-tab-pane name="1">
          <span slot="label"><i class="el-icon-truck"></i> 待收货</span>
        </el-tab-pane>
        <el-tab-pane name="2">
          <span slot="label"><i class="el-icon-finished"></i> 已收货</span>
        </el-tab-pane>
        <el-tab-pane name="3">
          <span slot="label"
            ><i class="el-icon-circle-check"></i> 交易完成</span
          >
        </el-tab-pane>
        <el-tab-pane name="4">
          <span slot="label"><i class="el-icon-document"></i> 待评价</span>
        </el-tab-pane>
        <el-tab-pane name="5">
          <span slot="label"><i class="el-icon-finished"></i> 已评价</span>
        </el-tab-pane>
        <el-tab-pane name="-1">
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
          <span class="caculate-num">{{ calculateInfo.orderNum }}</span></a
        >
        <a class="caculate-title"
          >商品数 :
          <span class="caculate-num">{{ calculateInfo.goodsNum }}</span></a
        >
        <a class="caculate-title"
          >订单金额 :
          <span class="caculate-num">{{ calculateInfo.orderPrice }}</span></a
        >
        <a class="caculate-title"
          >客户数 :
          <span class="caculate-num">{{ calculateInfo.userNum }}</span></a
        >
      </div>
      <el-table
        ref="multipleTable"
        v-loading="loading"
        :data="tableData"
        size="small"
        style="width: 100%"
      >
        <el-table-column
          prop="orderId"
          align="center"
          label="订单号"
          width="140"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.orderId }}</span>
            <p>[普通订单]</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="userId"
          align="center"
          label="会员信息"
          width="100"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.username }}</span>
            <p>{{ scope.row.userPhone }}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="goodsId"
          align="center"
          label="商品信息"
          width="240"
        >
          <template slot-scope="scope">
            <div v-if="scope.row.sku">
              <div>
                <img
                  style="width: 30px; height: 30px; margin: 0; cursor: pointer"
                  :src="scope.row.attrImage"
                />
              </div>
              <span>{{ scope.row.storeName }}&nbsp;{{ scope.row.sku }}</span>
              <span>
                | ￥{{ scope.row.totalPrice }} x {{ scope.row.totalNum }}</span
              >
            </div>
            <div v-else>
              <span
                ><img
                  style="width: 30px; height: 30px; margin: 0; cursor: pointer"
                  :src="scope.row.image"
              /></span>
              <span>{{ scope.row.storeName }}</span>
              <span>
                | ￥{{ scope.row.totalPrice }} x {{ scope.row.totalNum }}</span
              >
            </div>
          </template>
        </el-table-column>
        <el-table-column
          prop="address"
          align="center"
          label="收货信息"
          width="100"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.realName }}</span>
            <p>{{ scope.row.phone }}</p>
          </template>
        </el-table-column>
        <el-table-column
          prop="payPrice"
          align="center"
          label="实际支付"
          width="100"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.payPrice }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="payStatus"
          align="center"
          label="支付方式"
          width="120"
        >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.payType === '1'">手机支付</el-tag>
            <el-tag v-if="scope.row.payType === '2'">余额支付</el-tag>
            <div>{{ scope.row.payTime }}</div>
          </template>
        </el-table-column>
        <el-table-column
          prop="status"
          align="center"
          label="订单状态"
          width="100"
        >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === '0'">待发货</el-tag>
            <el-tag v-if="scope.row.status === '1'">待收货</el-tag>
            <el-tag v-if="scope.row.status === '2'">已收货</el-tag>
            <el-tag v-if="scope.row.status === '3'">交易完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          align="center"
          label="创建时间"
          width="120"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" width="200">
          <template slot="header">
            <div
              style="display: inline-block; cursor: pointer"
              @click="toQuery"
            >
              操作<i class="el-icon-refresh" style="margin-left: 10px" />
            </div>
          </template>
          <template slot-scope="scope">
            <el-button
              slot="reference"
              type="danger"
              size="mini"
              @click="detail(scope.row)"
              >订单详情</el-button
            >
            <el-dropdown
              size="mini"
              style="margin-left: 10px"
              split-button
              type="primary"
              trigger="click"
            >
              操作
              <el-dropdown-menu>
                <el-dropdown-item>
                  <el-button
                    size="mini"
                    type="success"
                    @click="remark(scope.row)"
                  >
                    订单备注</el-button
                  >
                </el-dropdown-item>
                <el-dropdown-item v-if="scope.row.status === '0'">
                  <el-button
                    size="mini"
                    type="success"
                    @click="ship(scope.row)"
                  >
                    进行发货</el-button
                  >
                </el-dropdown-item>
                <el-dropdown-item v-if="scope.row.status === '1'">
                  <el-popover :ref="scope.row.id" placement="top" width="180">
                    <p>确定进行收货吗？</p>
                    <div style="text-align: right; margin: 0">
                      <el-button
                        size="mini"
                        type="text"
                        @click="$refs[scope.row.id].doClose()"
                        >取消</el-button
                      >
                      <el-button
                        :loading="delLoading"
                        type="success"
                        size="mini"
                        @click="receipt(scope.row.id)"
                        >确定</el-button
                      >
                    </div>
                    <el-button slot="reference" type="success" size="mini"
                      >进行收货</el-button
                    >
                  </el-popover>
                </el-dropdown-item>
                <el-dropdown-item v-if="scope.row.status === '2'">
                  <el-popover :ref="scope.row.id" placement="top" width="180">
                    <p>确定交易完成吗？</p>
                    <div style="text-align: right; margin: 0">
                      <el-button
                        size="mini"
                        type="text"
                        @click="$refs[scope.row.id].doClose()"
                        >取消</el-button
                      >
                      <el-button
                        :loading="delLoading"
                        type="success"
                        size="mini"
                        @click="success(scope.row.id)"
                        >确定</el-button
                      >
                    </div>
                    <el-button slot="reference" type="success" size="mini"
                      >交易完成</el-button
                    >
                  </el-popover>
                </el-dropdown-item>
                <el-dropdown-item v-if="scope.row.status === '0'">
                  <el-popover :ref="scope.row.id" placement="top" width="180">
                    <p>确定删除本条数据吗？</p>
                    <div style="text-align: right; margin: 0">
                      <el-button
                        size="mini"
                        type="text"
                        @click="$refs[scope.row.id].doClose()"
                        >取消</el-button
                      >
                      <el-button
                        :loading="delLoading"
                        type="primary"
                        size="mini"
                        @click="subDelete(scope.row.id)"
                        >确定</el-button
                      >
                    </div>
                    <el-button
                      slot="reference"
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      >删除</el-button
                    >
                  </el-popover>
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
        </el-table-column>
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
    <remark ref="remark" />
    <ship ref="ship" />
    <detail ref="detail" />
  </div>
</template>

<script>
import remark from "./part/remark";
import ship from "./part/ship";
import detail from "./part/detail";
export default {
  name: "orderList",
  components: { ship, remark, detail },
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
        { key: "username", display_name: "用户名" },
        { key: "userPhone", display_name: "用户手机号" },
        { key: "realName", display_name: "收货人姓名" },
        { key: "addressPhone", display_name: "收货人电话" },
      ],
      calculateInfo: {
        orderNum: 0,
        goodsNum: 0,
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
  created() {
    this.init();
  },
  methods: {
    detail(data) {
      const _this = this.$refs.detail;
      _this.form = data;
      _this.dialog = true;
    },
    remark(data) {
      const _this = this.$refs.remark;
      _this.form = {
        id: data.id,
        orderId: data.orderId,
        remark: data.remark,
      };
      _this.dialog = true;
    },
    ship(data) {
      const _this = this.$refs.ship;
      _this.form = {
        id: data.id,
        deliveryName: data.deliveryName,
        deliveryId: data.deliveryId,
        deliveryType: "express",
        deliverySn: data.deliverySn,
      };
      _this.dialog = true;
    },
    receipt(id) {
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/goodsOrder/receipt/" + id,
      })
        .then((res) => {
          if (res.data.code === 200) {
            this.$refs[id].doClose();
            this.delLoading = false;
            this.$message.success("更新成功");
            this.toQuery();
          }
        })
        .catch(() => {
          this.$refs[id].doClose();
          this.delLoading = false;
        });
    },
    success(id) {
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/goodsOrder/success/" + id,
      })
        .then((res) => {
          if (res.data.code === 200) {
            this.$refs[id].doClose();
            this.delLoading = false;
            this.$message.success("更新成功");
            this.toQuery();
          }
        })
        .catch(() => {
          this.$refs[id].doClose();
          this.delLoading = false;
        });
    },
    subDelete(id) {
      this.delLoading = true;
      this.$axios({
        method: "post",
        url: "/goodsOrder/deleteGoodsOrder/" + id,
      })
        .then((res) => {
          if (res.data.code === 200) {
            this.$refs[id].doClose();
            this.delLoading = false;
            this.$message.success("删除成功");
            this.toQuery();
          }
        })
        .catch(() => {
          this.$refs[id].doClose();
          this.delLoading = false;
        });
    },
    init() {
      this.page = {
        current: 1,
        pageSize: 10,
        total: 0,
      };
      this.toQuery();
    },
    reset() {
      this.query = {
        value: "",
        type: "orderId",
      };
      this.init();
    },
    toQuery() {
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/goodsOrder/getGoodsOrderList",
        data: {
          query: this.query,
          pageNum: this.page.current,
          pageSize: this.page.pageSize,
          status: this.status,
        },
      })
        .then((res) => {
          if (res.data.code === 200) {
            this.loading = false;
            this.calculateInfo = res.data.data.calculateInfo;
            this.tableData = res.data.data.tableData.list;
            this.page.total = res.data.data.tableData.total;
            this.page.current = res.data.data.tableData.pageNum;
            this.page.pageSize = res.data.data.tableData.pageSize;
          }
        })
        .catch(() => {
          this.loading = false;
        });
    },
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
