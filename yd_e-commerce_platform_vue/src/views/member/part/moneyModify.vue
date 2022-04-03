<template>
  <el-dialog
    :append-to-body="true"
    :close-on-click-modal="false"
    :before-close="cancel"
    :visible.sync="dialog"
    title="余额修改"
    width="400px"
  >
    <el-form ref="form" :model="form" size="small" label-width="70px">
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model.trim="form.username"
          :disabled="true"
          style="width: 270px"
        />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model.trim="form.phone"
          :disabled="true"
          style="width: 270px"
        />
      </el-form-item>
      <el-form-item label="用户余额" prop="nowMoney">
        <el-input-number
          v-model="form.nowMoney"
          :min="0"
          :max="99999999.99"
          :precision="2"
          :controls="false"
          :disabled="true"
        />
      </el-form-item>
      <el-form-item label="修改余额" prop="el-radio-grouptype">
        <el-radio-group v-model="form.type">
          <el-radio label="1">增加</el-radio>
          <el-radio label="2">减少</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="修改余额" prop="modifyMoney">
        <el-input-number
          v-model="form.modifyMoney"
          :min="0"
          :max="99999999.99"
          :precision="2"
          :step="10.0"
          controls-position="right"
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
  name: "moneyModify",
  data() {
    return {
      loading: false,
      dialog: false,
      form: {
        userId: null,
        username: "",
        phone: "",
        type: "1",
        nowMoney: 0,
        modifyMoney: 0,
      },
    };
  },
  methods: {
    doSubmit() {
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/member/modifyMemberBalance/" + this.form.userId,
        data: {
          type: this.form.type,
          modifyMoney: this.form.modifyMoney,
        },
      })
        .then((res) => {
          this.loading = false;
          if (res.data.code === 200) {
            this.$message.success("余额修改成功");
            this.dialog = false;
            this.resetForm();
            this.$parent.init();
          } else {
            this.queryBalance(this.form.userId);
          }
        })
        .catch(() => {
          this.loading = false;
        });
    },
    cancel() {
      this.dialog = false;
      this.resetForm();
    },
    resetForm() {
      this.$refs["form"].resetFields();
      this.form = {
        userId: null,
        username: "",
        phone: "",
        type: "1",
        nowMoney: 0,
        modifyMoney: 0,
      };
    },
    queryBalance(id) {
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/member/getMemberBalance/" + id,
      })
        .then((res) => {
          const data = res.data.data;
          this.form = {
            userId: data.userId,
            username: data.username,
            phone: data.phone,
            type: this.form.type,
            nowMoney: data.nowMoney,
            modifyMoney: this.form.modifyMoney,
          };
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
  },
};
</script>

<style scoped></style>
