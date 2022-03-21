<template>
  <div>
    <el-button
      type="primary"
      icon="el-icon-plus"
      @click="fnOpenAddMenuDialog"
      style="margin-bottom: 10px"
      >新增菜单</el-button
    >
    <el-table
      :data="tableData"
      size="small"
      tooltip-effect="dark"
      style="width: 100%"
      row-key="id"
      border
      stripe
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="菜单名称" />
      <el-table-column prop="icon" label="图标" />
      <el-table-column prop="path" label="菜单URL" />
      <el-table-column prop="component" label="菜单组件" />
      <el-table-column prop="type" label="类型">
        <template slot="type" slot-scope="scope">
          <el-tag size="small" v-if="scope.row.type === '1'">目录</el-tag>
          <el-tag size="small" v-if="scope.row.type === '2'">菜单</el-tag>
          <el-tag
            size="small"
            v-else-if="scope.row.enable === '0'"
            type="danger"
            >禁用</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序号" />
      <el-table-column prop="status" label="状态">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.enable === '1'" type="success"
            >启用</el-tag
          >
          <el-tag
            size="small"
            v-else-if="scope.row.enable === '0'"
            type="danger"
            >禁用</el-tag
          >
          <el-tag
            size="small"
            v-else-if="scope.row.hidden === '0'"
            type="danger"
            >隐藏</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="creatDate" width="180" label="创建时间" />
      <el-table-column prop="action" label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="fnToEditMenu(scope.row.id)"
            >编辑</el-button
          >
          <el-button
            type="text"
            v-if="scope.row.enable === '0'"
            @click="fnChangeEnable(scope.row.id, '1')"
            >启用</el-button
          >
          <el-button
            type="text"
            v-if="scope.row.enable === '1'"
            @click="fnChangeEnable(scope.row.id, '0')"
            >禁用</el-button
          >
          <el-button
            type="text"
            v-if="scope.row.hidden === '0'"
            @click="fnChangeHidden(scope.row.id, '1')"
            >显示</el-button
          >
          <el-button
            type="text"
            v-if="scope.row.hidden === '1'"
            @click="fnChangeHidden(scope.row.id, '0')"
            >隐藏</el-button
          >
          <el-popconfirm
            title="确定要删除吗？"
            style="margin-left: 10px"
            @confirm="fnDeleteMenu(scope.row.id)"
          >
            <el-button slot="reference" type="text">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--添加菜单-->
    <el-dialog
      title="菜单信息"
      :visible.sync="menuDialogVisible"
      @close="fnCloseMenuDialog('menuForm')"
      width="35%"
    >
      <el-form :model="menuForm" ref="menuForm" label-width="100px">
        <el-form-item label="上级菜单" prop="parentId">
          <el-select
            v-model.trim="menuForm.parentId"
            clearable
            style="width: 100%"
            placeholder="请选择上级菜单(不选则为顶级菜单)"
          >
            <template v-for="item in tableData">
              <el-option
                :label="item.name"
                :value="item.id"
                :key="item.id"
              ></el-option>
              <template v-for="child in item.children">
                <el-option
                  :label="child.name"
                  :value="child.id"
                  :key="child.id"
                >
                  <span>{{ "- " + child.name }}</span>
                </el-option>
              </template>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="菜单名称" prop="name">
          <el-input v-model.trim="menuForm.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="菜单图标" prop="icon">
          <el-input v-model.trim="menuForm.icon" autocomplete="off" />
        </el-form-item>
        <el-form-item label="菜单URL" prop="path">
          <el-input v-model.trim="menuForm.path" autocomplete="off" />
        </el-form-item>
        <el-form-item label="菜单组件" prop="component">
          <el-input
            v-model.trim="menuForm.component"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="排序号" prop="sort" type="number">
          <el-input v-model="menuForm.sort" autocomplete="off" />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="fnCloseMenuDialog('menuForm')">取 消</el-button>
        <el-button v-if="!isEdit" type="primary" @click="fnAddMenu('menuForm')"
          >新 增</el-button
        >
        <el-button v-if="isEdit" type="primary" @click="fnEditMenu('menuForm')"
          >保 存</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import store from "../../store";
export default {
  name: "menu",
  data() {
    const validateName = (rule, value, callback) => {
      const message = this.dataValidate.validateNameChinese(value);
      if (message === "") {
        callback();
      } else {
        callback(new Error(message));
      }
    };
    return {
      tableData: [],
      menuDialogVisible: false,
      isEdit: false,
      menuForm: {
        parentId: "",
        name: "",
        icon: "",
        path: "",
        type: "",
        component: "",
        sort: "",
      },
      rules: {
        name: [
          { required: true, message: "请输入排序号", trigger: "blur" },
          { validator: validateName, trigger: "blur" },
        ],
        sort: [{ required: true, message: "请输入排序号", trigger: "blur" }],
      },
    };
  },
  methods: {
    getMenuList() {
      this.$axios({
        method: "post",
        url: "/menu/getMenuList",
      }).then((res) => {
        this.tableData = res.data.data;
      });
    },
    fnOpenAddMenuDialog() {
      this.isEdit = false;
      this.menuDialogVisible = true;
    },
    fnToEditMenu(id) {
      this.$axios({
        method: "post",
        url: "/menu/getMenuById",
        params: {
          id: id,
        },
      }).then((res) => {
        this.menuForm = res.data.data;
        this.isEdit = true;
        this.menuDialogVisible = true;
      });
    },
    fnChangeEnable(id, enable) {
      this.$axios({
        method: "post",
        url: "/menu/changeEnable",
        data: {
          id: id,
          enable: enable,
        },
      }).then((res) => {
        this.$message.success(res.data.msg);
        store.commit("setHasMenus", false);
        this.getMenuList();
      });
    },
    fnChangeHidden(id, hidden) {
      this.$axios({
        method: "post",
        url: "/menu/changeHidden",
        data: {
          id: id,
          hidden: hidden,
        },
      }).then((res) => {
        this.$message.success(res.data.msg);
        store.commit("setHasMenus", false);
        this.getMenuList();
      });
    },
    fnDeleteMenu(id) {
      this.$axios({
        method: "post",
        url: "/menu/deleteMenu",
        params: {
          id: id,
        },
      }).then((res) => {
        if (res.data.code === 200) {
          this.$message.success("删除成功");
          store.commit("setHasMenus", false);
          this.getMenuList();
        }
      });
    },
    fnCloseMenuDialog(formName) {
      this.$refs[formName].resetFields();
      Object.keys(this.menuForm).forEach((key) => (this.menuForm[key] = ""));
      this.menuDialogVisible = false;
    },
    fnAddMenu(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios({
            method: "post",
            url: "/menu/addMenu",
            data: this.menuForm,
          }).then(() => {
            this.$message.success("新增成功");
            this.menuDialogVisible = false;
            store.commit("setHasMenus", false);
            this.getMenuList();
          });
        } else {
          return false;
        }
      });
    },
    fnEditMenu(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios({
            method: "post",
            url: "/menu/editMenu",
            data: this.menuForm,
          }).then(() => {
            this.$message.success("修改成功");
            this.menuDialogVisible = false;
            store.commit("setHasMenus", false);
            this.getMenuList();
          });
        } else {
          return false;
        }
      });
    },
  },
  mounted() {
    this.getMenuList();
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped></style>
