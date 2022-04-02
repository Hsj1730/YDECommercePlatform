<template>
  <el-dialog
    :append-to-body="true"
    :close-on-click-modal="false"
    :before-close="cancel"
    :visible.sync="dialog"
    top="5vh"
    style="padding-bottom: 5vh"
    :title="!isEdit ? '新增商品' : '编辑商品'"
    width="800px"
  >
    <el-form
      ref="form"
      :model="form"
      :inline="true"
      :rules="rules"
      size="small"
      label-width="80px"
    >
      <el-form-item label="商品分类" prop="cateId">
        <tree-select
          v-model.trim="form.cateId"
          :options="cateTree"
          :normalizer="normalizer"
          style="width: 250px"
          placeholder="选择商品分类(只能选择二级分类)"
        />
      </el-form-item>
      <br />
      <el-form-item label="商品名称" prop="storeName">
        <el-input v-model="form.storeName" clearable style="width: 250px" />
      </el-form-item>
      <el-form-item label="关键字" prop="keyword">
        <el-input v-model="form.keyword" clearable style="width: 250px" />
      </el-form-item>
      <el-form-item label="单位名" prop="unitName">
        <el-input v-model.trim="form.unitName" clearable style="width: 250px" />
      </el-form-item>
      <el-form-item label="商品条码" prop="barCode">
        <el-input
          v-model.trim="form.barCode"
          :disabled="true"
          style="width: 250px"
        />
      </el-form-item>
      <el-form-item label="商品图片" prop="image">
        <material-list
          v-model.trim="form.imageArr"
          type="image"
          :num="1"
          :width="150"
          :height="150"
        />
      </el-form-item>
      <br />
      <el-form-item label="轮播图" prop="sliderImage">
        <material-list
          v-model.trim="form.sliderImageArr"
          type="image"
          :num="4"
          :width="150"
          :height="150"
        />
      </el-form-item>
      <el-form-item label="商品简介" prop="storeInfo">
        <el-input
          v-model="form.storeInfo"
          style="width: 500px"
          rows="2"
          type="textarea"
        />
      </el-form-item>
      <el-form-item label="商品描述" prop="description">
        <el-input
          v-model="form.description"
          style="width: 500px"
          rows="4"
          type="textarea"
        />
      </el-form-item>
      <el-form-item label="商品价格" prop="price">
        <el-input-number
          v-model="form.price"
          :min="0"
          :max="99999999.99"
          :precision="2"
          :step="10.0"
          controls-position="right"
        />
      </el-form-item>
      <el-form-item label="成本价" prop="cost">
        <el-input-number
          v-model="form.cost"
          :min="0"
          :max="99999999.99"
          :precision="2"
          :step="10.0"
          controls-position="right"
        />
      </el-form-item>
      <el-form-item label="邮费" prop="postage">
        <el-input-number
          v-model="form.postage"
          :min="0"
          :max="99999999.99"
          :precision="2"
          :step="10.0"
          controls-position="right"
        />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="form.sort" :min="1" />
      </el-form-item>
      <el-form-item label="销量" prop="sales">
        <el-input-number v-model="form.sales" :min="0" :step="10" />
      </el-form-item>
      <el-form-item label="库存" prop="stock">
        <el-input-number v-model="form.stock" :min="0" :step="10" />
      </el-form-item>
      <el-form-item label="上架" prop="isShow">
        <el-radio-group v-model="form.isShow">
          <el-radio :label="'1'">是</el-radio>
          <el-radio :label="'0'" style="width: 90px">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="热卖单品" prop="isHot">
        <el-radio-group v-model="form.isHot">
          <el-radio :label="'1'">是</el-radio>
          <el-radio :label="'0'" style="width: 90px">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="促销单品" prop="isBenefit">
        <el-radio-group v-model="form.isBenefit">
          <el-radio :label="'1'">是</el-radio>
          <el-radio :label="'0'" style="width: 90px">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="精品推荐" prop="isBest">
        <el-radio-group v-model="form.isBest">
          <el-radio :label="'1'">是</el-radio>
          <el-radio :label="'0'" style="width: 90px">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="首发新品" prop="isNew">
        <el-radio-group v-model="form.isNew">
          <el-radio :label="'1'">是</el-radio>
          <el-radio :label="'0'" style="width: 90px">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="优品推荐" prop="isGood">
        <el-radio-group v-model="form.isGood">
          <el-radio :label="'1'">是</el-radio>
          <el-radio :label="'0'" style="width: 90px">否</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer" style="margin-right: 50px">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit"
        >确认</el-button
      >
    </div>
  </el-dialog>
</template>

<script>
import treeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import materialList from "../../../../components/Material/index";
export default {
  name: "goodsInfo",
  components: { treeSelect, materialList },
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
      cateTree: [],
      form: {
        id: null,
        image: "",
        sliderImage: "",
        barCode: "",
        imageArr: [],
        sliderImageArr: [],
        storeName: "",
        unitName: "",
        storeInfo: "",
        keyword: "",
        cateId: null,
        price: 0,
        cost: 0,
        postage: 0,
        sort: 1,
        sales: 0,
        stock: 0,
        isShow: "1",
        isHot: "0",
        isBenefit: "0",
        isBest: "0",
        isNew: "0",
        isGood: "0",
        description: "",
      },
      rules: {
        cateId: [
          { required: true, message: "请选择商品分类", trigger: "blur" },
        ],
        storeName: [
          { required: true, message: "请输入商品名称", trigger: "blur" },
        ],
        unitName: [
          { required: true, message: "请输入商品单位", trigger: "blur" },
        ],
        keyword: [
          { required: true, message: "请输入商品关键字", trigger: "blur" },
        ],
        storeInfo: [
          { required: true, message: "请填写商品简介", trigger: "blur" },
        ],
      },
    };
  },
  watch: {
    "form.imageArr": function (val) {
      this.form.image = val.join(",");
    },
    "form.sliderImageArr": function (val) {
      if (val) {
        this.form.sliderImage = val.join(",");
      }
    },
  },
  methods: {
    doSubmit() {
      this.loading = true;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.imageArr.length === 0) {
            this.$message.error("请添加商品图片");
            return false;
          }
          if (this.form.sliderImageArr.length === 0) {
            this.$message.error("请添加商品轮播图");
            return false;
          }
          // 图片处理
          this.form.image = this.form.imageArr[0].url;
          this.form.sliderImage = "";
          this.form.sliderImageArr.forEach((item) => {
            this.form.sliderImage += item.url + ",";
          });
          // 去掉最后一个逗号
          if (this.form.sliderImage.length > 0) {
            this.form.sliderImage = this.form.sliderImage.substr(
              0,
              this.form.sliderImage.length - 1
            );
          }
          this.$axios({
            method: "post",
            url: "/goods/saveGoodsInfo",
            data: this.form,
          }).then((res) => {
            if (res.data.code === 200) {
              this.$message.success("保存成功");
              this.resetForm();
              this.$parent.init();
            }
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
        image: "",
        sliderImage: "",
        barCode: "",
        imageArr: [],
        sliderImageArr: [],
        storeName: "",
        unitName: "",
        storeInfo: "",
        keyword: "",
        cateId: null,
        price: 0,
        cost: 0,
        postage: 0,
        sort: 1,
        sales: 0,
        stock: 0,
        isShow: "1",
        isHot: "0",
        isBenefit: "0",
        isBest: "0",
        isNew: "0",
        isGood: "0",
        description: "",
      };
    },
    getCateTree() {
      this.$axios({
        method: "post",
        url: "/goodsCategory/getGoodsCategoryList/",
      }).then((res) => {
        this.cateTree = res.data.data;
      });
    },
    normalizer(node) {
      if (
        node.children == null ||
        node.children === "null" ||
        node.children.length === 0
      ) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.cateName,
        children: node.children,
      };
    },
  },
};
</script>

<style scoped>
/deep/ .el-input-number--small {
  width: 150px;
  line-height: 30px;
}
</style>
