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
      <el-form-item label="轮播图标题" prop="title">
        <el-input v-model.trim="form.title" style="width: 300px" />
      </el-form-item>
      <el-form-item label="轮播图路由" prop="url">
        <el-input v-model.trim="form.url" style="width: 300px" />
      </el-form-item>
      <el-form-item label="轮播图" prop="image">
        <material-list
          v-model.trim="form.imageArr"
          type="image"
          :num="1"
          :width="150"
          :height="150"
        />
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
import materialList from "../../../../components/Material/index";
export default {
  name: "rotationInfo",
  components: { materialList },
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
        title: "",
        image: "",
        imageArr: [],
        url: "",
        enable: "1",
        sort: 0,
      },
      rules: {
        title: [
          { required: true, message: "请输入轮播图标题", trigger: "blur" },
        ],
        url: [{ required: true, message: "请输入轮播图路由", trigger: "blur" }],
      },
    };
  },
  watch: {
    "form.imageArr": function (val) {
      this.form.image = val.join(",");
    },
  },
  methods: {
    cancel() {
      this.resetForm();
    },
    doSubmit() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.imageArr.length === 0) {
            this.$message.error("请添加轮播图图片");
            return false;
          }
          // 图片处理
          this.form.image = this.form.imageArr[0].url;
          this.loading = true;
          this.$axios({
            method: "post",
            url: "/rotation/saveRotationInfo",
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
    resetForm() {
      this.dialog = false;
      this.$refs["form"].resetFields();
      this.form = {
        id: null,
        title: "",
        image: "",
        imageArr: [],
        url: "",
        enable: "1",
        sort: 0,
      };
    },
  },
};
</script>

<style scoped></style>
