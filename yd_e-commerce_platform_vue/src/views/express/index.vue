<template>
  <div class="app-container">
    <div class="head-container" style="margin-top: -20px">
      <el-button
        class="filter-item"
        size="mini"
        type="primary"
        icon="el-icon-plus"
        @click="add"
        >新增</el-button
      >
    </div>
    <el-table
      v-loading="loading"
      :data="tableData"
      size="small"
      style="width: 100%"
    >
      <el-table-column prop="code" align="center" label="快递公司编号" />
      <el-table-column prop="name" align="center" label="快递公司名称" />
      <el-table-column prop="enable" align="center" label="状态">
        <template slot-scope="scope">
          <div @click="onEnable(scope.row.id, scope.row.enable)">
            <el-tag v-if="scope.row.enable === '1'" style="cursor: pointer"
              >启用</el-tag
            >
            <el-tag v-else style="cursor: pointer" type="info">禁用</el-tag>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="sort" align="center" label="排序" />
      <el-table-column prop="createTime" align="center" label="创建时间" />
      <el-table-column align="center">
        <template slot="header">
          <div style="display: inline-block; cursor: pointer" @click="toQuery">
            操作<i class="el-icon-refresh" style="margin-left: 10px" />
          </div>
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            icon="el-icon-edit"
            @click="edit(scope.row.id)"
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
                @click="toDelete(scope.row.id)"
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
    <express-info ref="expressInfo" :is-edit="isEdit" />
  </div>
</template>

<script>
import expressInfo from "./part/expressInfo";
export default {
  components: {
    expressInfo,
  },
  name: "express",
  data() {
    return {
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
    edit(id) {
      this.$axios({
        method: "post",
        url: "/express/getExpressInfo/" + id,
      }).then((res) => {
        this.isEdit = true;
        this.$refs.expressInfo.dialog = true;
        this.$refs.expressInfo.form = res.data.data;
      });
    },
    onEnable(id, enable) {
      console.log(id);
      console.log(enable);
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
          url:
            "/express/setExpressEnable/" +
            id +
            "/" +
            (enable === "1" ? "0" : "1"),
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
    toDelete(id) {
      this.$axios({
        method: "post",
        url: "/express/deleteExpress/" + id,
      }).then((res) => {
        if (res.data.code === 200) {
          this.$message.success("删除成功");
          this.toQuery();
        }
      });
    },
    add() {
      this.isEdit = false;
      this.$refs.expressInfo.dialog = true;
    },
    init() {
      this.page = {
        current: 1,
        pageSize: 10,
        total: 0,
      };
      this.toQuery();
    },
    toQuery() {
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/express/getExpressList",
        data: {
          pageNum: this.page.current,
          pageSize: this.page.pageSize,
        },
      })
        .then((res) => {
          this.loading = false;
          this.tableData = res.data.data.list;
          this.page.total = res.data.data.total;
          this.page.current = res.data.data.pageNum;
          this.page.pageSize = res.data.data.pageSize;
        })
        .catch(() => {
          this.loading = false;
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
