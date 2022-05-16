<template>
  <div class="app-container" style="margin-top: -20px">
    <div class="head-container">
      <el-button
        type="primary"
        size="mini"
        icon="el-icon-plus"
        @click="fnOpenAddCategoryDialog"
        style="margin-bottom: 10px"
        >新增分类</el-button
      >
      <el-dialog
        :title="title"
        :visible.sync="categoryDialogVisible"
        @close="fnCloseCategoryDialog('categoryForm')"
        width="540px"
      >
        <el-form :model="categoryForm" ref="categoryForm" label-width="100px">
          <el-form-item
            label="分类名称"
            prop="cateName"
            :rules="{
              required: true,
              message: '请填写分类名称',
              trigger: 'blur',
            }"
          >
            <el-input
              v-model.trim="categoryForm.cateName"
              placeholder="请填写分类名称"
              autocomplete="off"
              clearable
            />
          </el-form-item>
          <el-form-item label="分类图片" prop="pic">
            <material-list
              v-model="picArr"
              type="image"
              :num="1"
              :width="150"
              :height="150"
            />
          </el-form-item>
          <el-form-item label="状态" prop="isShow">
            <el-radio-group v-model="categoryForm.isShow">
              <el-radio :label="'1'">显示</el-radio>
              <el-radio :label="'0'">隐藏</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="排序号" prop="sort">
            <el-input-number
              v-model="categoryForm.sort"
              :min="1"
              :max="99"
              autocomplete="off"
            />
          </el-form-item>
          <el-form-item
            label="上级分类"
            prop="parentId"
            :rules="[
              {
                required: true,
                message: '请选择上级分类',
                trigger: 'blur',
              },
            ]"
          >
            <tree-select
              v-model.trim="categoryForm.parentId"
              :options="treeCategory"
              :normalizer="normalizer"
              :value="value"
              style="width: 100%"
              :flatten-search-results="true"
              placeholder="请选择上级分类"
            />
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="fnCloseCategoryDialog('categoryForm')"
            >取 消</el-button
          >
          <el-button
            v-if="!isEdit"
            type="primary"
            @click="fnAddCategory('categoryForm')"
            >新 增</el-button
          >
          <el-button
            v-if="isEdit"
            type="primary"
            @click="fnEditCategory('categoryForm')"
            >保 存</el-button
          >
        </span>
      </el-dialog>
      <!--表格渲染-->
      <el-table
        :data="tableData"
        size="small"
        tooltip-effect="dark"
        style="width: 100%"
        row-key="id"
        stripe
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      >
        <el-table-column label="名称" align="center" prop="cateName" />
        <el-table-column label="状态" align="center" prop="isShow">
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.isShow === '1'">显示</el-tag>
              <el-tag v-else :type="'info'">隐藏</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="排序" prop="sort" align="center" sortable />
        <el-table-column
          prop="createTime"
          label="创建时间"
          align="center"
          sortable
        />
        <el-table-column align="center">
          <template slot="header">
            <div
              style="display: inline-block; cursor: pointer"
              @click="getGoodsCategoryList"
            >
              操作<i class="el-icon-refresh" style="margin-left: 10px" />
            </div>
          </template>
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-edit"
              @click="fnOpenEditCategoryDialog(scope.row.id)"
              style="margin-right: 10px"
            />
            <el-popover :ref="scope.row.id" placement="top" width="180">
              <p>确定要删除该商品分类吗？</p>
              <div style="text-align: right; margin: 0">
                <el-button
                  size="mini"
                  type="text"
                  @click="$refs[scope.row.id].doClose()"
                  >取消</el-button
                >
                <el-button
                  type="primary"
                  size="mini"
                  @click="fnDeleteCategory(scope.row.id)"
                  >确定</el-button
                >
              </div>
              <el-button
                slot="reference"
                type="danger"
                icon="el-icon-delete"
                size="mini"
              />
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import treeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import materialList from "../../../components/Material/index";
export default {
  name: "category",
  components: {
    treeSelect,
    materialList,
  },
  data() {
    return {
      tableData: [],
      treeCategory: [],
      treeCategory2: [],
      value: ["0"],
      title: "",
      categoryDialogVisible: false,
      isEdit: false,
      picArr: [],
      categoryForm: {
        parentId: 0,
        cateName: "",
        pic: "",
        isShow: "1",
        sort: "",
      },
      disabledId: 0,
    };
  },
  mounted() {
    this.getGoodsCategoryList();
  },
  watch: {
    picArr: function (val) {
      this.categoryForm.pic = val.join(",");
    },
  },
  methods: {
    fnDeleteCategory(id) {
      this.$axios({
        method: "post",
        url: "/goodsCategory/deleteGoodsCategory",
        params: {
          id: id,
        },
      }).then((res) => {
        if (res.data.code === 200) {
          this.$message.success("删除成功");
          this.getGoodsCategoryList();
        }
      });
    },
    fnAddCategory(formName) {
      console.log(this.categoryForm.parentId);
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.categoryForm.parentId !== "0" && this.picArr.length === 0) {
            this.$message.error("子分类必须选择分类图片");
            return false;
          } else {
            if (this.categoryForm.parentId !== "0") {
              this.categoryForm.pic = this.picArr[0].url;
            } else {
              this.categoryForm.pic = null;
            }
            this.$axios({
              method: "post",
              url: "/goodsCategory/addGoodsCategory",
              data: this.categoryForm,
            }).then((res) => {
              if (res.data.code === 200) {
                this.$message.success("新增成功");
                this.categoryDialogVisible = false;
                this.$refs["categoryForm"].resetFields();
                this.categoryForm.parentId = "0";
                this.categoryForm.isShow = "1";
                this.getGoodsCategoryList();
              }
            });
          }
        } else {
          return false;
        }
      });
    },
    fnEditCategory(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.categoryForm.parentId !== 0 && this.picArr.length === 0) {
            this.$message.error("子分类必须选择分类图片");
            return false;
          } else {
            if (this.categoryForm.parentId !== 0) {
              this.categoryForm.pic = this.picArr[0].url;
            } else {
              this.categoryForm.pic = null;
            }
            this.$axios({
              method: "post",
              url: "/goodsCategory/editGoodsCategory",
              data: this.categoryForm,
            }).then((res) => {
              if (res.data.code === 200) {
                this.$message.success("编辑成功");
                this.categoryDialogVisible = false;
                this.$refs["categoryForm"].resetFields();
                this.categoryForm.parentId = "0";
                this.categoryForm.isShow = "1";
                this.getGoodsCategoryList();
              }
            });
          }
        } else {
          return false;
        }
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
        id: node.id.toString(),
        label: node.cateName,
        children: node.children,
      };
    },
    fnOpenAddCategoryDialog() {
      this.title = "新增分类";
      this.isEdit = false;
      this.categoryDialogVisible = true;
      this.categoryForm.parentId = "0";
      this.picArr = [];
    },
    fnOpenEditCategoryDialog(id) {
      this.picArr = [];
      this.disabledId = id;
      this.$axios({
        method: "post",
        url: "/goodsCategory/getGoodsCategoryById",
        params: {
          id: id,
        },
      }).then((res) => {
        this.categoryForm = res.data.data.goodsCategory;
        const materialDto = res.data.data.materialDto;
        if (materialDto !== null) {
          this.picArr.push(materialDto);
        }
        this.title = "编辑分类";
        this.isEdit = true;
        this.categoryDialogVisible = true;
        // 禁用当前id作为父id
        this.treeCategory2 = this.treeCategory.concat();
        this.treeCategory = this.treeCategory.map((item) =>
          this.disableParentId(item)
        );
      });
    },
    disableParentId(data) {
      const hasChild = data.children && data.children.length > 0;
      // 设置当前id不能作为父节点
      const isParentId = data.id === this.disabledId;
      return {
        id: data.id,
        cateName: data.cateName,
        children: hasChild
          ? data.children.map((i) => this.disableParentId(i))
          : [],
        isDisabled: isParentId,
      };
    },
    fnCloseCategoryDialog(formName) {
      this.$refs[formName].resetFields();
      Object.keys(this.categoryForm).forEach(
        (key) => (this.categoryForm[key] = "")
      );
      this.categoryForm.parentId = "0";
      this.categoryForm.isShow = "1";
      this.categoryDialogVisible = false;
      if (this.isEdit) {
        this.treeCategory = [];
        this.treeCategory = this.treeCategory2.concat();
      }
    },
    getGoodsCategoryList() {
      this.$axios({
        method: "post",
        url: "/goodsCategory/getGoodsCategoryList",
      }).then((res) => {
        let data;
        data = res.data.data;
        this.tableData = data;
        this.treeCategory = this.treeCategoryHandle(data);
      });
    },
    treeCategoryHandle(tableData) {
      const treeCategory = [];
      const category = { id: 0, cateName: "顶级类目", children: [] };
      category.children = tableData;
      treeCategory.push(category);
      return treeCategory;
    },
  },
};
</script>

<style scoped></style>
