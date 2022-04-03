<template>
  <el-dialog
    :append-to-body="true"
    :close-on-click-modal="false"
    :before-close="cancel"
    :visible.sync="dialog"
    :title="!isEdit ? '新增' : '编辑'"
    width="460px"
  >
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      size="small"
      label-width="110px"
    >
      <el-form-item label="快递公司编号" prop="code">
        <el-input v-model.trim="form.code" style="width: 300px" />
      </el-form-item>
      <el-form-item label="快递公司名称" prop="name">
        <el-input v-model.trim="form.name" style="width: 300px" />
      </el-form-item>
      <el-form-item label="启用" prop="enable">
        <el-radio-group v-model="form.enable">
          <el-radio :label="'1'">是</el-radio>
          <el-radio :label="'0'" style="width: 90px">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="form.sort" :min="1" :max="99" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer" style="margin-right: 10px">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit"
        >确认</el-button
      >
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "expressInfo",
  props: {
    isEdit: {
      type: Boolean,
      required: true,
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      dialog: false,
      form: {
        id: null,
        code: "",
        name: "",
        sort: 0,
        enable: "1",
      },
      rules: {
        code: [
          { required: true, message: "请输入快递公司编号", trigger: "blur" },
          { validator: this.checkCode },
        ],
        name: [
          { required: true, message: "请输入快递公司名称", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    checkCode(rule, value, callback) {
      this.$axios({
        method: "post",
        url:
          "/express/checkCode/" +
          (this.form.id ? this.form.id : 0) +
          "/" +
          this.form.code,
      })
        .then((res) => {
          if (res.data.data !== 0) {
            return callback(new Error("该编号已存在"));
          }
          return callback();
        })
        .catch(() => {
          return callback();
        });
    },
    doSubmit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.loading = true;
          this.$axios({
            method: "post",
            url: "/express/saveExpressInfo",
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
        id: null,
        code: "",
        name: "",
        sort: 0,
        enable: "1",
      };
    },
  },
};
</script>

<style scoped></style>
