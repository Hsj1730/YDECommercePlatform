<template>
  <el-dialog
    :append-to-body="true"
    :close-on-click-modal="false"
    :before-close="cancel"
    :visible.sync="dialog"
    title="发货"
    width="500px"
  >
    <el-form ref="form" :model="form" size="small" label-width="80px">
      <el-form-item label="快递公司">
        <el-select
          v-model="form.deliverySn"
          filterable
          placeholder="请选择"
          style="width: 370px"
        >
          <el-option
            v-for="item in express"
            :key="item.id"
            :label="item.name"
            :value="item.code"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit"
        >确认</el-button
      >
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "ship",
  data() {
    return {
      loading: false,
      dialog: false,
      express: [],
      form: {
        id: null,
        deliveryName: "",
        deliveryType: "express",
        deliveryId: "",
        deliverySn: "",
      },
    };
  },
  created() {
    this.init();
  },
  methods: {
    doSubmit() {
      if (this.form.deliverySn === "" || this.form.deliverySn == null) {
        this.$message.error("请选择物流公司");
        return;
      }
      this.express.forEach((data) => {
        if (data.code === this.form.deliverySn) {
          this.form.deliveryName = data.name;
        }
      });
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/goodsOrder/saveExpress",
        data: this.form,
      })
        .then((res) => {
          if (res.data.code === 200) {
            this.loading = false;
            this.$message.success("保存成功");
            this.dialog = false;
            this.$parent.init();
          }
        })
        .catch(() => {
          this.loading = false;
        });
    },
    cancel() {
      this.resetForm();
    },
    resetForm() {
      this.dialog = false;
      this.$refs["form"].resetFields();
      this.form = {
        id: null,
        deliveryName: "",
        deliveryType: "express",
        deliveryId: "",
        deliverySn: "",
      };
    },
    init() {
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/goodsOrder/getExpressList",
      })
        .then((res) => {
          this.loading = false;
          this.express = res.data.data;
        })
        .catch(() => {
          this.loading = false;
        });
    },
  },
};
</script>

<style scoped></style>
