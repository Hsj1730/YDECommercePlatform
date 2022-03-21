<template>
  <div>
    <el-form :inline="true" :model="searchForm">
      <el-form-item>
        <el-input
          v-model="searchForm.nickname"
          placeholder="请输入昵称进行搜索"
          clearable
        >
        </el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" @click="getUserList">搜索</el-button>
        <el-button
          type="primary"
          icon="el-icon-plus"
          @click="fnOpenAddUserDialog"
          >新增用户</el-button
        >
      </el-form-item>
    </el-form>
    <el-table
      :data="tableData"
      size="small"
      tooltip-effect="dark"
      style="width: 100%"
      border
      stripe
    >
      <el-table-column prop="avatar" width="50" label="头像">
        <template slot-scope="scope">
          <el-avatar size="small" :src="scope.row.avatar"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column label="性别" prop="gender" align="center">
        <template slot-scope="scope">
          <span>{{
            scope.row.gender === "0"
              ? "未知"
              : scope.raw.gender === "1"
              ? "男"
              : "女"
          }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="mobile" label="手机号" />
      <el-table-column prop="email" label="邮箱" />
      <el-table-column prop="roleName" label="角色名称">
        <template slot-scope="scope">
          <el-tag
            style="margin-left: 1%"
            size="small"
            type="info"
            v-for="(item, index) in scope.row.roleName"
            :key="index"
            >{{ item }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column prop="effective" label="是否禁用">
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
        </template>
      </el-table-column>
      <el-table-column prop="createDate" width="180" label="创建时间">
      </el-table-column>
      <el-table-column prop="action" label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="fnOpenRoleDialog(scope.row.id)"
            >分配角色</el-button
          >
          <el-button type="text" @click="fnResetPassword(scope.row.id)"
            >重置密码</el-button
          >
          <el-button
            type="text"
            v-if="scope.row.enable === '0'"
            @click="fnChangeEffective(scope.row.id, '1')"
            >启用</el-button
          >
          <el-button
            type="text"
            v-if="scope.row.enable === '1'"
            @click="fnChangeEffective(scope.row.id, '0')"
            >禁用</el-button
          >
          <el-popconfirm
            title="确定要删除吗？"
            style="margin-left: 10px"
            @confirm="fnDeleteUser(scope.row.id)"
          >
            <el-button slot="reference" type="text">删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="current"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      style="float: right; margin-top: 10px"
    >
    </el-pagination>
    <!--添加用户-->
    <el-dialog
      title="添加用户"
      :visible.sync="userDialogVisible"
      @close="fnCloseUserDialog('addUserForm')"
      width="35%"
    >
      <el-form :model="addUserForm" ref="addUserForm">
        <el-form-item
          label="用户名："
          prop="username"
          label-width="80px"
          :rules="{ required: true, message: '请输入用户名', trigger: 'blur' }"
        >
          <el-input
            v-model.trim="addUserForm.username"
            placeholder="请输入用户名（登录账号）"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="昵称："
          prop="nickname"
          label-width="80px"
          :rules="{ required: true, message: '请输入昵称', trigger: 'blur' }"
        >
          <el-input
            v-model.trim="addUserForm.nickname"
            placeholder="请输入昵称"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码：" prop="password" label-width="80px">
          <el-input
            v-model.trim="addUserForm.password"
            placeholder="默认密码为123456"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="fnCloseUserDialog('addUserForm')">取 消</el-button>
        <el-button type="primary" @click="fnAddUser('addUserForm')"
          >新 增</el-button
        >
      </span>
    </el-dialog>

    <!--分配角色-->
    <el-dialog title="分配角色" :visible.sync="roleDialogVisible" width="30%">
      <el-checkbox-group v-model="checkedRoles">
        <el-checkbox
          v-for="item in roles"
          :label="item.roleId"
          :key="item.roleId"
          >{{ item.name }}</el-checkbox
        >
      </el-checkbox-group>
      <span slot="footer" class="dialog-footer">
        <el-button @click="fnCloseRoleDialog">取 消</el-button>
        <el-button type="primary" @click="fnAssignRoles">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import compile from "../../utils/secret.js";
export default {
  name: "user",
  data() {
    return {
      searchForm: {
        nickname: "",
      },
      tableData: [],
      current: 1,
      pageSize: 10,
      total: 0,
      addUserForm: {
        username: "",
        password: "",
        nickname: "",
      },
      userDialogVisible: false,
      roleDialogVisible: false,
      roles: [],
      checkedRoles: [],
      roleUserId: "",
    };
  },
  mounted() {
    this.getUserList();
  },
  methods: {
    getUserList() {
      this.$axios({
        method: "post",
        url: "/user/getUserList",
        data: {
          pageNum: this.current,
          pageSize: this.pageSize,
          nickname: this.searchForm.nickname,
        },
      }).then((res) => {
        if (res.data.code === 200) {
          this.total = res.data.data.total;
          this.tableData = res.data.data.list;
        }
      });
    },
    fnDeleteUser(id) {
      if (id === this.$store.state.userId) {
        this.$message.warning("无法删除自己本身");
        return;
      }
      this.$axios({
        method: "post",
        url: "/user/deleteUser",
        data: {
          id: id,
        },
      }).then((res) => {
        if (res.data.code === 200) {
          this.$message.success("删除成功");
          this.getUserList();
        }
      });
    },
  },
};
</script>

<style scoped></style>
