<template>
  <div style="display: inline-block">
    <el-dialog
      :visible.sync="dialog"
      :close-on-click-modal="false"
      :before-close="cancel"
      :title="title"
      append-to-body
      width="475px"
      @close="cancel"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        size="small"
        label-width="88px"
      >
        <el-form-item label="新邮箱" prop="email">
          <el-input v-model="form.email" clearable style="width: 320px" />
        </el-form-item>
        <el-form-item label="当前密码" prop="password">
          <el-input
            v-model="form.password"
            type="password"
            style="width: 320px"
            clearable
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="cancel">取消</el-button>
        <el-button type="primary" @click="doSubmit">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "updateEmail",
  props: {
    email: {
      type: String,
      required: true,
      default() {
        return "";
      },
    },
  },
  data() {
    const validMail = (rule, value, callback) => {
      if (value === "" || value === null) {
        callback(new Error("新邮箱不能为空"));
      } else if (value === this.email) {
        callback(new Error("新邮箱不能与旧邮箱相同"));
      } else if (this.dataValidate.validEmail(value)) {
        callback();
      } else {
        callback(new Error("邮箱格式错误"));
      }
    };
    return {
      dialog: false,
      title: "修改邮箱",
      form: { password: "", email: "" },
      user: { email: "", password: "" },
      time: 60,
      rules: {
        password: [
          { required: true, message: "当前密码不能为空", trigger: "blur" },
        ],
        email: [{ required: true, validator: validMail, trigger: "blur" }],
      },
    };
  },
  methods: {
    cancel() {
      this.resetForm();
    },
    doSubmit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$axios({
            method: "post",
            url: "/user/resetEmail",
            data: {
              id: this.$store.state.userInfo.id,
              password: this.rsa.encrypt(this.form.password),
              email: this.form.email,
            },
          }).then((res) => {
            if (res.data.code === 200) {
              this.resetForm();
              this.$message.success("邮箱修改成功");
              this.$parent.getUserInfo();
            }
          });
        } else {
          return false;
        }
      });
    },
    resetForm() {
      this.dialog = false;
      this.$refs["form"].resetFields();
      this.form = { password: "", email: "" };
    },
  },
};
</script>

<style scoped></style>
