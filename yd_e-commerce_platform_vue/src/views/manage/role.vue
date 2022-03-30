<template>
  <div>
    <el-form :inline="true" :model="searchForm">
      <el-form-item>
        <el-input
          v-model="searchForm.name"
          placeholder="请输入角色名称进行搜索"
          clearable
        >
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="getRoleList">搜索</el-button>
        <el-button type="primary" icon="el-icon-plus" @click="fnOpenRoleDialog"
          >新增角色</el-button
        >
      </el-form-item>
    </el-form>

    <el-table
      :data="tableData"
      tooltip-effect="dark"
      style="width: 100%"
    >
      <el-table-column
        prop="name"
        align="center"
        label="角色名称"
        width="200"
      />
      <el-table-column prop="code" label="角色编码" align="center">
        <template slot-scope="scope">
          <el-tag type="info">{{ scope.row.code }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="enable" width="100" label="状态" align="center">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.enable === '1'" type="success">启用</el-tag>
          <el-tag v-else-if="scope.row.enable === '0'" type="danger"
            >禁用</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        width="180"
        align="center"
        label="创建时间"
      />
      <el-table-column prop="action" align="center">
        <template slot="header">
          <div
            @click="getRoleList"
            style="display: inline-block; cursor: pointer"
          >
            操作<i class="el-icon-refresh" style="margin-left: 10px" />
          </div>
        </template>
        <template slot-scope="scope">
          <el-button type="text" @click="fnOpenMenuDialog(scope.row.id)"
            >分配菜单</el-button
          >
          <el-button type="text" @click="fnToEditRole(scope.row.id)"
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
          <el-popconfirm
            title="确定要删除吗？"
            style="margin-left: 10px"
            @confirm="deleteRole(scope.row.id)"
          >
            <el-button slot="reference" type="text">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="current"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      style="float: right; margin-top: 10px; padding-bottom: 10px"
    >
    </el-pagination>

    <!--添加角色-->
    <el-dialog
      :title="isEdit ? '修改角色' : '新增角色'"
      :visible.sync="roleDialogVisible"
      @close="fnCloseRoleDialog('roleForm')"
      width="35%"
    >
      <el-form :model="roleForm" ref="roleForm">
        <el-form-item
          label="角色名称："
          prop="name"
          label-width="100px"
          :rules="{
            required: true,
            message: '请输入角色名称',
            trigger: 'blur',
          }"
        >
          <el-input
            v-model.trim="roleForm.name"
            placeholder="请输入角色名称"
            clearable
          ></el-input>
        </el-form-item>
        <el-form-item
          label="角色编码："
          prop="code"
          label-width="100px"
          clearable
          :rules="{
            required: true,
            message: '请输入角色编码',
            trigger: 'blur',
          }"
        >
          <el-input
            v-model.trim="roleForm.code"
            placeholder="请输入角色编码"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="fnCloseRoleDialog('roleForm')">取 消</el-button>
        <el-button v-if="!isEdit" type="primary" @click="fnAddRole('roleForm')"
          >新 增</el-button
        >
        <el-button v-if="isEdit" type="primary" @click="fnEditRole('roleForm')"
          >编 辑</el-button
        >
      </span>
    </el-dialog>
    <!--分配菜单-->
    <el-dialog
      title="分配菜单"
      :visible.sync="menuDialogVisible"
      @close="fnCloseMenuDialog"
      width="35%"
    >
      <el-tree
        :data="menuTreeData"
        show-checkbox
        ref="menuTree"
        :default-expand-all="true"
        node-key="id"
        :expand-on-click-node="true"
        :props="defaultProps"
        :check-strictly="status"
      >
      </el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="fnCloseMenuDialog">取 消</el-button>
        <el-button type="primary" @click="fnAssignMenu">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getMenuList } from "../../utils/routerUtil";
export default {
  name: "role",
  data() {
    return {
      searchForm: {
        name: "",
      },
      tableData: [],
      current: 1,
      pageSize: 10,
      total: 0,
      roleDialogVisible: false,
      roleForm: {
        id: 0,
        name: "",
        code: "",
      },
      isEdit: false,
      menuDialogVisible: false,
      menuTreeData: [],
      defaultProps: {
        children: "children",
        label: "name",
      },
      roleId: 0,
      status: false,
    };
  },
  methods: {
    getRoleList() {
      this.$axios({
        method: "post",
        url: "/role/getRoleList",
        data: {
          pageNum: this.current,
          pageSize: this.pageSize,
          name: this.searchForm.name,
        },
      }).then((res) => {
        this.tableData = res.data.data.list;
        this.total = res.data.data.total;
      });
    },
    fnOpenRoleDialog() {
      this.roleDialogVisible = true;
      this.isEdit = false;
    },
    fnOpenMenuDialog(roleId) {
      this.roleId = roleId;
      this.status = true;
      this.$axios({
        method: "post",
        url: "/role/getMenuTree",
        params: {
          id: roleId,
        },
      }).then((res) => {
        this.menuDialogVisible = true;
        this.$nextTick(() => {
          this.menuTreeData = res.data.data.menuTree;
          this.$refs.menuTree.setCheckedKeys(res.data.data.menuIds);
          this.$nextTick(() => {
            this.status = false;
          });
        });
      });
    },
    fnToEditRole(roleId) {
      this.$axios({
        method: "post",
        url: "/role/getRoleById",
        params: {
          id: roleId,
        },
      }).then((res) => {
        this.roleForm = res.data.data;
        this.isEdit = true;
        this.roleDialogVisible = true;
      });
    },
    fnChangeEnable(id, enable) {
      this.$axios({
        method: "post",
        url: "/role/changeEnable",
        data: {
          id: id,
          enable: enable,
        },
      }).then((res) => {
        if (res.data.code === 200) {
          this.$message.success(res.data.msg);
          this.getRoleList();
          getMenuList();
        }
      });
    },
    deleteRole(roleId) {
      this.$axios({
        method: "post",
        url: "/role/deleteRole",
        params: {
          id: roleId,
        },
      }).then((res) => {
        if (res.data.code === 200) {
          this.$message.success("删除成功");
          this.getRoleList();
          getMenuList();
        }
      });
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getRoleList();
    },
    handleCurrentChange(val) {
      this.current = val;
      this.getRoleList();
    },
    fnCloseRoleDialog(formName) {
      this.$refs[formName].resetFields();
      Object.keys(this.roleForm).forEach((key) => (this.roleForm[key] = ""));
      this.roleDialogVisible = false;
    },
    fnAddRole(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios({
            method: "post",
            url: "/role/addRole",
            data: this.roleForm,
          }).then(() => {
            this.$message.success("新增成功");
            this.roleDialogVisible = false;
            this.getRoleList();
          });
        } else {
          return false;
        }
      });
    },
    fnEditRole(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios({
            method: "post",
            url: "/role/editRole",
            data: this.roleForm,
          }).then(() => {
            this.$message.success("编辑成功");
            this.roleDialogVisible = false;
            this.getRoleList();
          });
        } else {
          return false;
        }
      });
    },
    fnCloseMenuDialog() {
      this.roleId = 0;
      this.$refs.menuTree.setCheckedKeys([]);
      this.menuDialogVisible = false;
    },
    fnAssignMenu() {
      this.$axios({
        method: "post",
        url: "/role/saveRoleMenu",
        data: {
          roleId: this.roleId,
          menuIds: [].concat(
            this.$refs.menuTree.getHalfCheckedKeys(),
            this.$refs.menuTree.getCheckedKeys()
          ),
        },
      }).then((res) => {
        if (res.data.data === 0) {
          this.$message.error("分配失败！");
        } else {
          this.$message.success("分配成功");
          this.roleId = 0;
          this.$refs.menuTree.setCheckedKeys([]);
          this.menuDialogVisible = false;
          // 刷新侧边菜单
          getMenuList()
        }
      });
    },
  },
  mounted() {
    this.getRoleList();
  },
};
</script>

<style scoped></style>
