<template>
  <el-dialog
    :append-to-body="true"
    :close-on-click-modal="false"
    :before-close="cancel"
    :visible.sync="dialog"
    title="订单详情"
    width="700px"
  >
    <el-card>
      <div slot="header">
        <span>收货信息</span>
      </div>
      <div class="text item">用户名:{{ form.username }}</div>
      <div class="text item">联系电话:{{ form.userPhone }}</div>
      <br />
      <div class="text item">收货人: {{ form.realName }}</div>
      <div class="text item">联系电话: {{ form.phone }}</div>
      <div class="text item">收货地址: {{ form.address }}</div>
    </el-card>
    <el-card>
      <div slot="header">
        <span>订单信息</span>
      </div>
      <el-row :gutter="24">
        <el-col :span="12">
          <div class="text item">订单编号: {{ form.orderId }}</div>
          <div class="text item">商品总数: {{ form.totalNum }}</div>
          <div class="text item">支付邮费: {{ form.payPostage }}</div>
          <div class="text item">实际支付: {{ form.payPrice }}</div>
          <div class="text item">
            支付方式: {{ form.payType === "1" ? "手机支付" : "余额支付" }}
          </div>
        </el-col>
        <el-col :span="12">
          <div class="text item">
            订单状态:
            {{
              form.status === "0"
                ? "待发货"
                : form.status === "1"
                ? "待收货"
                : form.status === "2"
                ? "已收货"
                : "交易完成"
            }}
          </div>
          <div class="text item">商品单价: {{ form.totalPrice }}</div>
          <div class="text item">邮费单价: {{ form.totalPostage }}</div>
          <div class="text item">创建时间: {{ form.createTime }}</div>
          <div class="text item">支付时间: {{ form.payTime }}</div>
        </el-col>
      </el-row>
    </el-card>
    <el-card v-if="form.status !== '0'">
      <div slot="header">
        <span>物流信息</span>
      </div>
      <div class="text item">快递公司编号:{{ form.deliverySn }}</div>
      <div class="text item">快递公司:{{ form.deliveryName }}</div>
      <div class="text item">快递单号:{{ form.deliveryId }}</div>
    </el-card>
    <el-card v-if="form.status === '3' && form.isEvaluate === '1'">
      <div slot="header">
        <span>评价信息</span>
      </div>
      <div class="text item">{{ form.evaluate }}</div>
    </el-card>
    <el-card>
      <div slot="header">
        <span>备注信息</span>
      </div>
      <div class="text item">{{ form.remark }}</div>
    </el-card>
  </el-dialog>
</template>

<script>
export default {
  name: "detail",
  data() {
    return {
      loading: false,
      dialog: false,
      form: {},
    };
  },
  methods: {
    cancel() {
      this.dialog = false;
      this.form = {};
    },
  },
};
</script>

<style scoped>
.text {
  font-size: 12px;
}

.item {
  padding: 6px 0;
}
</style>
