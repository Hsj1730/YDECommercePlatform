<template>
  <el-dialog
    :append-to-body="true"
    :close-on-click-modal="false"
    :before-close="cancel"
    :visible.sync="dialog"
    top="5vh"
    style="padding-bottom: 5vh"
    title="会员信息"
    width="400px"
  >
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      size="small"
      label-width="70px"
    >
      <el-form-item label="用户名" prop="username">
        <el-input
          v-model.trim="form.username"
          :disabled="true"
          style="width: 270px"
        />
      </el-form-item>
      <el-form-item label="用户头像" prop="userImage">
        <material-list
          v-model.trim="form.userImageArr"
          type="image"
          :num="1"
          :width="150"
          :height="150"
        />
      </el-form-item>
      <el-form-item label="真实姓名" prop="realName">
        <el-input v-model.trim="form.realName" style="width: 270px" />
      </el-form-item>
      <el-form-item label="用户备注" prop="mark">
        <el-input v-model.trim="form.mark" style="width: 270px" />
      </el-form-item>
      <el-form-item label="手机号" prop="phone">
        <el-input
          v-model.trim="form.phone"
          oninput="value=value.replace(/[^\d]/g,'')"
          style="width: 270px"
          maxLength="11"
        />
      </el-form-item>
      <el-form-item label="身份证号" prop="cardId">
        <el-input
          v-model.trim="form.cardId"
          maxLength="18"
          style="width: 270px"
        />
      </el-form-item>
      <el-form-item label="详细地址" prop="address">
        <el-input v-model.trim="form.address" style="width: 270px" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer" style="margin-right: 20px">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit"
        >确认</el-button
      >
    </div>
  </el-dialog>
</template>

<script>
import materialList from "../../../components/Material/index";
export default {
  name: "memberInfo",
  components: { materialList },
  data() {
    const validateRealName = (rule, value, callback) => {
      if (value !== "") {
        if (this.dataValidate.validChinese(value)) {
          callback();
        } else {
          callback(new Error("请输入中文"));
        }
      } else {
        callback();
      }
    };
    const validateCardId = (rule, value, callback) => {
      if (value !== "") {
        if (this.dataValidate.validCardId(value)) {
          callback();
        } else {
          callback(new Error("请输入正确的身份证"));
        }
      } else {
        callback();
      }
    };
    return {
      loading: false,
      dialog: false,
      form: {
        userId: null,
        username: "",
        phone: "",
        userImage: "",
        userImageArr: [],
        realName: "",
        cardId: "",
        mark: "",
        address: "",
      },
      rules: {
        realName: [{ validator: validateRealName }],
        phone: [
          { required: true, message: "请输入手机号", trigger: "blur" },
          { validator: this.checkMobile },
        ],
        cardId: [{ validator: validateCardId }],
      },
    };
  },
  watch: {
    "form.userImageArr": function (val) {
      this.form.userImage = val.join(",");
    },
  },
  methods: {
    checkMobile(rule, value, callback) {
      if (!this.dataValidate.validateMobile(value)) {
        return callback(new Error("请输入正确的手机号码"));
      } else {
        this.$axios({
          method: "post",
          url:
            "/member/checkMobile/" +
            (this.form.userId ? this.form.userId : 0) +
            "/" +
            this.form.phone,
        })
          .then((res) => {
            if (res.data.data !== 0) {
              return callback(new Error("该手机号已存在"));
            }
            return callback();
          })
          .catch(() => {
            return callback();
          });
      }
    },
    doSubmit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.userImageArr.length === 0) {
            this.$message.error("请添加用户头像");
            return false;
          }
          // 图片处理
          this.form.userImage = this.form.userImageArr[0].url;
          this.loading = true;
          this.$axios({
            method: "post",
            url: "/member/saveMemberInfo",
            data: this.form,
          })
            .then((res) => {
              if (res.data.code === 200) {
                this.$message.success("保存成功");
                this.resetForm();
                this.$parent.init();
              }
              this.loading = false;
            })
            .catch(() => {
              this.loading = false;
            });
        } else {
          return false;
        }
      });
    },
    cancel() {
      this.resetForm();
    },
    resetForm() {
      this.dialog = false;
      this.$refs["form"].resetFields();
      this.form = {
        userId: null,
        username: "",
        phone: "",
        userImage: "",
        userImageArr: [],
        realName: "",
        cardId: "",
        mark: "",
        address: "",
      };
    },
  },
};
</script>

<style scoped></style>
