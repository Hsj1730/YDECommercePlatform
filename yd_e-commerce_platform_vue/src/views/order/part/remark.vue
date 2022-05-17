<template>
  <el-dialog
    :append-to-body="true"
    :close-on-click-modal="false"
    :before-close="cancel"
    :visible.sync="dialog"
    title="订单备注"
    width="500px"
  >
    <el-form ref="form" :model="form" size="small" label-width="80px">
      <el-form-item label="订单号">
        <el-input
          v-model="form.orderId"
          :disabled="true"
          style="width: 370px"
        />
      </el-form-item>
      <el-form-item label="订单备注">
        <el-input
          v-model="form.remark"
          style="width: 370px"
          rows="5"
          type="textarea"
        />
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
  name: "remark",
  props: {},
  data() {
    return {
      loading: false,
      dialog: false,
      form: {
        id: null,
        orderId: "",
        remark: "",
      },
    };
  },
  methods: {
    cancel() {
      this.resetForm();
    },
    doSubmit() {
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/goodsOrder/saveRemark",
        data: {
          id: this.form.id,
          remark: this.form.remark,
        },
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

    resetForm() {
      this.dialog = false;
      this.$refs["form"].resetFields();
      this.form = {
        id: null,
        orderId: "",
        remark: "",
      };
    },
  },
};
</script>

<style scoped></style>
