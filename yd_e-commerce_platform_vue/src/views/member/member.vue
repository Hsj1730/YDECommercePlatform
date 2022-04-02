<template>
  <div class="app-container">
    <div class="head-container" style="margin-top: -20px">
      <el-input
        v-model="query.value"
        clearable
        placeholder="输入搜索内容"
        style="width: 200px"
        class="filter-item"
        @keyup.enter.native="toQuery"
      />
      <el-select
        v-model="query.type"
        clearable
        placeholder="类型"
        class="filter-item"
        style="width: 150px; padding-left: 10px"
      >
        <el-option
          v-for="item in queryTypeOptions"
          :key="item.key"
          :label="item.display_name"
          :value="item.key"
        />
      </el-select>
      <el-button
        style="margin-left: 10px"
        class="filter-item"
        size="mini"
        type="success"
        icon="el-icon-search"
        @click="toQuery"
        >搜索</el-button
      >
      <el-button
        type="danger"
        class="filter-item"
        size="mini"
        icon="el-icon-refresh"
        @click="reset"
        >重置</el-button
      >
    </div>
    <el-table
      v-loading="loading"
      :data="tableData"
      size="small"
      style="width: 100%"
    >
      <el-table-column prop="userId" align="center" width="60" label="用户id" />
      <el-table-column prop="username" align="center" label="用户名" />
      <el-table-column
        ref="table"
        align="center"
        width="150"
        prop="userImage"
        label="用户头像"
      >
        <template slot-scope="scope">
          <a :href="scope.row.userImage" style="color: #42b983" target="_blank"
            ><el-avatar :src="scope.row.userImage"></el-avatar
          ></a>
        </template>
      </el-table-column>
      <el-table-column prop="phone" align="center" label="手机号码" />
      <el-table-column prop="nowMoney" align="center" label="用户余额" />
      <el-table-column prop="enable" label="状态" width="80" align="center">
        <template slot-scope="scope">
          <div @click="onEnable(scope.row.userId, scope.row.enable)">
            <el-tag v-if="scope.row.enable === '1'" style="cursor: pointer"
              >正常</el-tag
            >
            <el-tag v-else style="cursor: pointer" type="'info'">禁用</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="payCount" align="center" label="购买次数" />
      <el-table-column prop="createTime" align="center" label="创建日期" />
      <el-table-column align="center">
        <template slot="header">
          <div style="display: inline-block; cursor: pointer" @click="toQuery">
            操作<i class="el-icon-refresh" style="margin-left: 10px" />
          </div>
        </template>
        <template slot-scope="scope">
          <el-button
            slot="reference"
            type="danger"
            size="mini"
            icon="el-icon-edit"
            @click="edit(scope.row.id)"
          />
          <el-dropdown
            size="mini"
            style="margin-left: 10px"
            split-button
            type="primary"
            trigger="click"
          >
            操作
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>
                <el-button
                  size="mini"
                  type="primary"
                  @click="moneyModify(scope.row.userId)"
                  >修改余额</el-button
                >
              </el-dropdown-item>
              <el-dropdown-item>
                <el-popover :ref="scope.row.id" placement="top" width="180">
                  <p>确定删除本条数据吗？</p>
                  <div style="text-align: right; margin: 0">
                    <el-button
                      size="mini"
                      type="text"
                      @click="$refs[scope.row.id].doClose()"
                      >取消</el-button
                    >
                    <el-button
                      :loading="delLoading"
                      type="primary"
                      size="mini"
                      @click="subDelete(scope.row.id)"
                      >确定</el-button
                    >
                  </div>
                  <el-button
                    slot="reference"
                    type="danger"
                    icon="el-icon-delete"
                    size="mini"
                    >删除</el-button
                  >
                </el-popover>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page.current"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="page.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="page.total"
      style="float: right; margin-top: 10px; padding-bottom: 10px"
    />
    <money-modify ref="moneyModify" />
  </div>
</template>

<script>
import moneyModify from "./part/moneyModify";
export default {
  name: "member",
  components: { moneyModify },
  data() {
    return {
      query: {
        value: "",
        type: "username",
      },
      queryTypeOptions: [
        { key: "username", display_name: "用户名" },
        { key: "phone", display_name: "手机号" },
      ],
      loading: false,
      delLoading: false,
      isEdit: false,
      tableData: [],
      page: {
        current: 1,
        pageSize: 10,
        total: 0,
      },
    };
  },
  created() {
    this.init();
  },
  methods: {
    onEnable(userId, enable) {
      this.$confirm(
        `确定进行[ ${enable === "1" ? "禁用" : "启用"} ]操作?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.$axios({
          method: "post",
          url: "/member/setMemberStatus/" + userId,
          params: {
            enable: enable === "1" ? "0" : "1",
          },
        }).then(() => {
          this.$message({
            message: "操作成功",
            type: "success",
            duration: 1000,
            onClose: () => {
              this.toQuery();
            },
          });
        });
      });
    },
    moneyModify(id) {
      this.$refs.moneyModify.queryBalance(id);
      this.$refs.moneyModify.dialog = true;
    },
    init() {
      this.page = {
        current: 1,
        pageSize: 10,
        total: 0,
      };
      this.toQuery();
    },
    reset() {
      this.query = {
        value: "",
        type: "username",
      };
      this.init();
    },
    toQuery() {
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/member/getMemberList",
        data: {
          query: this.query,
          pageNum: this.page.current,
          pageSize: this.page.pageSize,
        },
      }).then((res) => {
        this.loading = false;
        this.tableData = res.data.data.list;
        this.page.total = res.data.data.total;
        this.page.current = res.data.data.pageNum;
        this.page.pageSize = res.data.data.pageSize;
      });
    },
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.toQuery();
    },
    handleCurrentChange(val) {
      this.page.current = val;
      this.toQuery();
    },
  },
};
</script>

<style scoped></style>
