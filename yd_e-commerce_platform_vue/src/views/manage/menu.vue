<template>
  <div>
    <el-button
      type="primary"
      icon="el-icon-plus"
      size="mini"
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
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="菜单名称" header-align="center" />
      <el-table-column prop="icon" label="图标" align="center" width="60">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon" />
        </template>
      </el-table-column>
      <el-table-column prop="path" label="菜单URL" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.type === '1'"></span>
          <span v-if="scope.row.type === '2'">{{ scope.row.path }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="component" label="菜单组件" align="center" />
      <el-table-column prop="type" label="类型" width="80" align="center">
        <template slot-scope="scope">
          <el-tag size="small" v-if="scope.row.type === '1'">目录</el-tag>
          <el-tag size="small" v-if="scope.row.type === '2'">菜单</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序号" width="80" align="center" />
      <el-table-column prop="status" label="状态" align="center" width="150">
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
          <el-tag size="small" v-if="scope.row.hidden === '1'" type="danger"
            >隐藏</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        width="180"
        label="创建时间"
        align="center"
      />
      <el-table-column prop="action" align="center" width="250">
        <template slot="header">
          <div
            @click="getMenuList"
            style="display: inline-block; cursor: pointer"
          >
            操作<i class="el-icon-refresh" style="margin-left: 10px" />
          </div>
        </template>
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
            v-if="scope.row.hidden === '1'"
            @click="fnChangeHidden(scope.row.id, '0')"
            >显示</el-button
          >
          <el-button
            type="text"
            v-if="scope.row.hidden === '0'"
            @click="fnChangeHidden(scope.row.id, '1')"
            >隐藏</el-button
          >
          <el-popconfirm
            title="确定要删除该菜单吗？"
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
      :title="title"
      :visible.sync="menuDialogVisible"
      @close="fnCloseMenuDialog('menuForm')"
      width="540px"
    >
      <el-form :model="menuForm" ref="menuForm" label-width="100px">
        <el-form-item
          label="上级菜单"
          prop="parentId"
          :rules="[
            {
              required: true,
              message: '请选择上级菜单',
              trigger: 'blur',
            },
          ]"
        >
          <tree-select
            v-model.trim="menuForm.parentId"
            :options="treeMenu"
            :normalizer="normalizer"
            :value="value"
            style="width: 100%"
            :flatten-search-results="true"
            placeholder="请选择上级菜单"
          />
        </el-form-item>
        <el-form-item
          label="菜单名称"
          prop="name"
          :rules="{
            required: true,
            message: '请输入菜单名称',
            trigger: 'blur',
          }"
        >
          <el-input
            v-model.trim="menuForm.name"
            placeholder="请填写菜单名称"
            autocomplete="off"
            clearable
          />
        </el-form-item>
        <el-form-item label="菜单图标" prop="icon">
          <el-popover
            placement="bottom-start"
            trigger="click"
            width="400"
            @show="$refs['iconSelect'].reset()"
          >
            <icon-select ref="iconSelect" @selected="selected" />
            <el-input
              slot="reference"
              v-model="menuForm.icon"
              placeholder="点击选择图标"
              autocomplete="off"
              readonly
            >
              <svg-icon
                v-if="menuForm.icon"
                slot="prefix"
                :icon-class="menuForm.icon"
                class="el-input__icon"
                style="height: 32px; width: 16px"
              />
              <i v-else slot="prefix" class="el-icon-search el-input__icon" />
            </el-input>
          </el-popover>
        </el-form-item>
        <el-form-item label="菜单URL" prop="path">
          <el-input
            v-model.trim="menuForm.path"
            placeholder="为目录时不填写"
            autocomplete="off"
            clearable
          />
        </el-form-item>
        <el-form-item label="菜单组件" prop="component">
          <el-input
            v-model.trim="menuForm.component"
            autocomplete="off"
            clearable
            placeholder="为目录时不填写"
          ></el-input>
        </el-form-item>
        <el-form-item label="排序号" prop="sort">
          <el-input-number
            v-model="menuForm.sort"
            :min="1"
            :max="99"
            autocomplete="off"
          />
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
import iconSelect from "../../components/IconSelect";
import treeSelect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getMenuList } from "../../utils/routerUtil";
export default {
  components: {
    iconSelect,
    treeSelect,
  },
  data() {
    return {
      tableData: [],
      treeMenu: [],
      treeMenu2: [],
      value: ["0"],
      menuDialogVisible: false,
      isEdit: false,
      title: "",
      menuForm: {
        parentId: "0",
        name: "",
        icon: "",
        path: "",
        type: "",
        component: "",
        sort: "",
      },
      disabledId: 0,
    };
  },
  methods: {
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
        label: node.name,
        children: node.children,
      };
    },
    treeMenuHandle(tableData) {
      const treeMenu = [];
      const menu = { id: 0, name: "顶级类目", children: [] };
      menu.children = tableData;
      treeMenu.push(menu);
      return treeMenu;
    },
    // 选中图标
    selected(name) {
      this.menuForm.icon = name;
    },
    getMenuList() {
      this.$axios({
        method: "post",
        url: "/menu/getMenuList",
      }).then((res) => {
        let data;
        data = res.data.data;
        this.tableData = data;
        this.treeMenu = this.treeMenuHandle(data);
      });
    },
    fnOpenAddMenuDialog() {
      this.isEdit = false;
      this.title = "新增菜单";
      this.menuDialogVisible = true;
      this.menuForm.parentId = "0";
    },
    fnToEditMenu(id) {
      this.disabledId = id;
      this.$axios({
        method: "post",
        url: "/menu/getMenuById",
        params: {
          id: id,
        },
      }).then((res) => {
        this.menuForm = res.data.data;
        if (this.menuForm.component === "" || this.menuForm.component == null) {
          this.menuForm.path = "";
        }
        // 禁用当前id作为父id
        this.treeMenu2 = this.treeMenu.concat();
        this.treeMenu = this.treeMenu.map((item) => this.disableParentId(item));
        this.title = "编辑菜单";
        this.isEdit = true;
        this.menuDialogVisible = true;
      });
    },
    disableParentId(data) {
      const hasChild = data.children && data.children.length > 0;
      const isParentId = data.id === this.disabledId;
      return {
        id: data.id,
        name: data.name,
        children: hasChild
          ? data.children.map((i) => this.disableParentId(i))
          : [],
        isDisabled: isParentId,
      };
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
        // 刷新侧边菜单
        getMenuList();
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
      if (this.isEdit) {
        this.treeMenu = [];
        this.treeMenu = this.treeMenu2.concat();
      }
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
