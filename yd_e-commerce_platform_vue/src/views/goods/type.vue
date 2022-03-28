<template>
  <div class="app-container" style="margin-top: -20px">
    <div class="head-container" style="">
      <el-button
        type="primary"
        icon="el-icon-plus"
        @click="fnOpenAddMenuDialog"
        style="margin-bottom: 10px"
        >新增菜单</el-button
      >
      <!--表格渲染-->
      <el-table
        :data="tableData"
        size="small"
        tooltip-effect="dark"
        style="width: 100%"
        row-key="id"
        border
        stripe
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      >
        <el-table-column label="名称" align="center" prop="cateName" />
        <el-table-column prop="pic" align="center" label="图片">
          <template slot-scope="scope">
            <el-avatar :src="scope.row.avatar"></el-avatar>
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="isShow">
          <template slot-scope="scope">
            <div>
              <el-tag v-if="scope.row.isShow === 1">显示</el-tag>
              <el-tag v-else :type="'info'">隐藏</el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="排序" prop="sort" align="center" sortable />
        <el-table-column prop="createTime" label="创建时间" align="center" />
        <el-table-column label="操作" align="center" fixed="right">
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
    </div>
  </div>
</template>

<script>
export default {
  name: "type",
  data() {
    return {
      tableData: [],
      treeType: [],
      typeDialogVisible: false,
      isEdit: false,
    };
  },
  mounted() {
    this.getGoodsTypeList();
  },
  methods: {
    getGoodsTypeList() {
      this.$axios({
        method: "post",
        url: "/goods/getGoodsTypeList",
      }).then((res) => {
        let data;
        data = res.data.data;
        this.tableData = data;
        this.treeType = this.treeMenuHandle(data);
      });
    },
    treeMenuHandle(tableData) {
      const treeType = [];
      const type = { id: 0, name: "顶级类目", children: [] };
      type.children = tableData;
      treeType.push(type);
      return treeType;
    },
  },
};
</script>

<style scoped></style>
