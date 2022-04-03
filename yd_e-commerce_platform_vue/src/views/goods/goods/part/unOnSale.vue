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
    <!--表格渲染-->
    <el-table
      v-loading="loading"
      :data="tableData"
      size="small"
      style="width: 100%"
    >
      <el-table-column prop="id" align="center" width="80" label="商品id" />
      <el-table-column
        ref="table"
        align="center"
        width="150"
        prop="image"
        label="商品图片"
      >
        <template slot-scope="scope">
          <a :href="scope.row.image" style="color: #42b983" target="_blank"
            ><img :src="scope.row.image" alt="点击打开" class="el-avatar"
          /></a>
        </template>
      </el-table-column>
      <el-table-column prop="storeName" align="center" label="商品名称" />
      <el-table-column
        prop="barCode"
        align="center"
        width="100"
        label="商品编码"
      />
      <el-table-column
        prop="cateName"
        align="center"
        width="100"
        label="分类名称"
      />
      <el-table-column
        prop="price"
        align="center"
        width="100"
        label="商品价格"
        sortable
      />
      <el-table-column
        prop="sales"
        align="center"
        width="100"
        label="销量"
        sortable
      />
      <el-table-column
        prop="stock"
        align="center"
        width="100"
        label="库存"
        sortable
      />
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <div @click="onSale(scope.row.id, scope.row.isShow)">
            <el-tag v-if="scope.row.isShow === '1'" style="cursor: pointer"
              >已上架</el-tag
            >
            <el-tag v-else style="cursor: pointer" type="info">已下架</el-tag>
          </div>
        </template>
      </el-table-column>
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
    >
    </el-pagination>
    <goods-info ref="goodsInfoForm" :is-edit="isEdit" />
  </div>
</template>

<script>
import goodsInfo from "./goodsInfo";
export default {
  name: "unOnSale",
  components: { goodsInfo },
  data() {
    return {
      query: {
        value: "",
        type: "storeName",
      },
      queryTypeOptions: [
        { key: "storeName", display_name: "商品名称" },
        { key: "cateName", display_name: "分类名称" },
        { key: "barCode", display_name: "商品编码" },
      ],
      loading: false,
      delLoading: false,
      isEdit: true,
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
        type: "storeName",
      };
      this.init();
    },
    toQuery() {
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/goods/getGoodsList",
        data: {
          query: this.query,
          pageNum: this.page.current,
          pageSize: this.page.pageSize,
          isShow: "0",
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
    edit(id) {
      this.isEdit = true;
      this.$refs.goodsInfoForm.dialog = true;
      this.$refs.goodsInfoForm.getCateTree();
      this.$axios({
        method: "post",
        url: "/goods/getGoodsInfo/" + id,
      }).then((res) => {
        this.$refs.goodsInfoForm.form = res.data.data;
      });
    },
    onSale(id, status) {
      this.$confirm(
        `确定进行[ ${status === "1" ? "下架" : "上架"} ]操作?`,
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.$axios({
          method: "post",
          url: "/goods/setGoodsIsShow/" + id,
          params: {
            status: status === "1" ? "0" : "1",
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
  },
};
</script>

<style scoped></style>
