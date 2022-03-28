<template>
  <div style="display: inline-block">
    <el-dialog
      :visible.sync="dialog"
      :close-on-click-modal="false"
      :before-close="cancel"
      :title="title"
      append-to-body
      width="500px"
      @close="cancel"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        size="small"
        label-width="88px"
      >
        <el-form-item label="旧密码" prop="oldPass">
          <el-input
            v-model="form.oldPass"
            type="password"
            auto-complete="on"
            style="width: 370px"
            clearable
          />
        </el-form-item>
        <el-form-item label="新密码" prop="newPass">
          <el-input
            v-model="form.newPass"
            type="password"
            auto-complete="on"
            style="width: 370px"
            clearable
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPass">
          <el-input
            v-model="form.confirmPass"
            type="password"
            auto-complete="on"
            style="width: 370px"
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
import { removeToken } from "../../../../utils/auth";

export default {
  name: "updatePass",
  data() {
    const confirmPass = (rule, value, callback) => {
      if (value) {
        if (this.form.newPass !== value) {
          callback(new Error("两次输入的密码不一致"));
        } else {
          callback();
        }
      } else {
        callback(new Error("请再次输入密码"));
      }
    };
    const validatePassword = (rule, value, callback) => {
      const message = this.dataValidate.validatePass(
        value,
        this.settings.level
      );
      if (message === "") {
        callback();
      } else {
        callback(new Error(message));
      }
    };
    return {
      dialog: false,
      title: "修改密码",
      form: { oldPass: "", newPass: "", confirmPass: "" },
      rules: {
        oldPass: [{ required: true, message: "请输入旧密码", trigger: "blur" }],
        newPass: [
          { required: true, message: "请输入新密码", trigger: "blur" },
          {
            min: 6,
            max: 18,
            message: "长度在 6 到 18 个字符",
            trigger: "blur",
          },
          { validator: validatePassword, trigger: "blur" },
        ],
        confirmPass: [
          { required: true, validator: confirmPass, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    cancel() {
      this.resetForm();
    },
    resetForm() {
      this.dialog = false;
      this.$refs["form"].resetFields();
      this.form = { oldPass: "", newPass: "", confirmPass: "" };
    },
    doSubmit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$axios({
            method: "post",
            url: "/user/updatePass",
            data: {
              id: this.$store.state.userInfo.id,
              oldPass: this.rsa.encrypt(this.form.oldPass),
              newPass: this.rsa.encrypt(this.form.newPass),
            },
          }).then((res) => {
            if (res.data.code === 200) {
              this.$message.success("密码修改成功，请重新登录");
              setTimeout(() => {
                this.$axios.post("/logout").then((res) => {
                  removeToken();
                  sessionStorage.clear();
                  this.$message.success(res.data.msg);
                  this.$store.commit("resetState");
                  this.$router.push("/login");
                  // location.reload(); // 为了重新实例化vue-router对象 避免bug
                });
              }, 2000);
            }
          });
        } else {
          return false;
        }
      });
    },
  },
};
</script>

<style scoped></style>
