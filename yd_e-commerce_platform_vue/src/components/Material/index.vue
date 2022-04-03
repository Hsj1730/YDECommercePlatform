<template>
  <div v-if="type === 'image'">
    <ul
      v-for="(item, index) in value"
      :key="index"
      class="el-upload-list el-upload-list--picture-card"
    >
      <li
        tabindex="0"
        class="el-upload-list__item is-ready"
        :style="'width: ' + width + 'px;height: ' + height + 'px'"
      >
        <div>
          <img
            :src="item.realUrl"
            alt=""
            class="el-upload-list__item-thumbnail"
          />
          <span class="el-upload-list__item-actions">
            <span
              v-if="index !== 0"
              class="el-upload-list__item-preview"
              @click="moveMaterial(index, 'up')"
            >
              <i class="el-icon-back" />
            </span>
            <span
              class="el-upload-list__item-preview"
              @click="zoomMaterial(index)"
            >
              <i class="el-icon-view" />
            </span>
            <span
              class="el-upload-list__item-delete"
              @click="deleteMaterial(index)"
            >
              <i class="el-icon-delete" />
            </span>
            <span
              v-if="index !== value.length - 1"
              class="el-upload-list__item-preview"
              @click="moveMaterial(index, 'down')"
            >
              <i class="el-icon-right" />
            </span>
          </span>
        </div>
      </li>
    </ul>
    <div
      v-if="num > value.length"
      tabindex="0"
      class="el-upload el-upload--picture-card"
      :style="
        'width: ' +
        width +
        'px;height: ' +
        height +
        'px;' +
        'line-height:' +
        height +
        'px;'
      "
      @click="toSelectMaterial"
    >
      <i class="el-icon-plus" />
    </div>

    <el-dialog append-to-body :visible.sync="dialogVisible" width="35%">
      <img :src="url" alt="" style="width: 100%" />
    </el-dialog>

    <el-dialog
      title="图片素材库"
      append-to-body
      :visible.sync="listDialogVisible"
      width="70%"
      top="5vh"
      style="padding-bottom: 5vh"
    >
      <el-container>
        <el-aside width="unset">
          <div style="margin-bottom: 10px">
            <el-button
              class="el-icon-plus"
              size="small"
              @click="materialGroupAdd()"
            >
              添加分组
            </el-button>
          </div>
          <el-tabs
            v-model="materialGroupObjId"
            v-loading="materialGroupLoading"
            tab-position="left"
            @tab-click="tabClick"
          >
            <el-tab-pane
              v-for="item in materialGroupList"
              :key="item.id"
              :name="item.id + ''"
            >
              <span slot="label"> {{ item.name }}</span>
            </el-tab-pane>
          </el-tabs>
        </el-aside>
        <el-main style="margin-top: -20px">
          <el-card>
            <div slot="header">
              <el-row>
                <el-col :span="12">
                  <span>{{ materialGroupObj.name }}</span>
                  <span v-if="materialGroupObj.id !== 0">
                    <el-button
                      size="small"
                      type="text"
                      class="el-icon-edit"
                      style="margin-left: 10px"
                      @click="materialGroupEdit(materialGroupObj)"
                      >重命名</el-button
                    >
                    <el-button
                      size="small"
                      type="text"
                      class="el-icon-delete"
                      style="margin-left: 10px; color: red"
                      @click="materialGroupDelete(materialGroupObj)"
                      >删除</el-button
                    >
                  </span>
                </el-col>
                <el-col :span="12" style="text-align: right">
                  <el-upload
                    :action="
                      this.$axios.defaults.baseURL + '/material/uploadMaterial'
                    "
                    :headers="headers"
                    :file-list="[]"
                    :before-upload="beforeUpload"
                    :on-success="handleSuccess"
                    :on-error="handleError"
                    multiple
                  >
                    <span style="margin-right: 10px; font-size: 14px">
                      可选照片数：<span style="color: red">{{
                        num - value.length - urls.length
                      }}</span>
                      张</span
                    >
                    <el-button size="small" type="primary">批量上传</el-button>
                  </el-upload>
                </el-col>
              </el-row>
            </div>
            <div :v-loading="tableLoading">
              <el-alert
                v-if="tableData.length <= 0"
                title="暂无数据"
                type="info"
                :closable="false"
                center
                show-icon
              />
              <el-row :gutter="5">
                <el-checkbox-group v-model="urls" :max="num - value.length">
                  <el-col
                    v-for="(item, index) in tableData"
                    :key="index"
                    :span="4"
                  >
                    <el-card :body-style="{ padding: '5px' }">
                      <el-image
                        style="width: 100%; height: 100px"
                        :src="item.realUrl"
                        fit="contain"
                        :preview-src-list="[item.realUrl]"
                        :z-index="999"
                      />
                      <div>
                        <el-checkbox class="material-name" :label="item">
                          选择
                        </el-checkbox>
                        <el-row>
                          <el-col :span="24" class="col-do">
                            <el-button
                              type="text"
                              size="medium"
                              @click="materialDel(item)"
                              >删除</el-button
                            >
                          </el-col>
                        </el-row>
                      </div>
                    </el-card>
                  </el-col>
                </el-checkbox-group>
              </el-row>
              <el-pagination
                :current-page.sync="page.currentPage"
                :page-sizes="[12, 24]"
                :page-size="page.pageSize"
                layout="total, sizes, prev, pager, next, jumper"
                :total="page.total"
                class="pagination"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
              />
            </div>
          </el-card>
        </el-main>
      </el-container>
      <span slot="footer" class="dialog-footer">
        <el-button @click="listDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="sureUrls">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "../../utils/auth";
export default {
  name: "materialList",
  props: {
    // 素材数据
    value: {
      type: Array,
      default() {
        return [];
      },
    },
    // 素材类型
    type: {
      type: String,
    },
    // 素材限制数量，默认5个
    num: {
      type: Number,
      default() {
        return 5;
      },
    },
    // 宽度
    width: {
      type: Number,
      default() {
        return 150;
      },
    },
    // 高度
    height: {
      type: Number,
      default() {
        return 150;
      },
    },
  },
  data() {
    return {
      headers: {
        Authorization: getToken(),
      },
      dialogVisible: false,
      listDialogVisible: false,
      materialGroupLoading: false,
      url: "",
      urls: [],
      tableData: [],
      materialGroupList: [],
      materialGroupObj: {},
      materialGroupObjId: "0",
      page: {
        total: 0, // 总页数
        current: 1, // 当前页数
        pageSize: 12, // 每页显示多少条
      },
      groupId: 0,
      resultNumber: 0,
      tableLoading: false,
    };
  },
  methods: {
    sureUrls() {
      this.urls.forEach((item) => {
        this.$set(this.value, this.value.length, item);
      });
      this.urls = [];
      this.listDialogVisible = false;
    },
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.getMaterialPageList();
    },
    handleCurrentChange(val) {
      this.page.current = val;
      this.getMaterialPageList();
    },
    materialDel(item) {
      const that = this;
      this.$confirm("是否确认删除该素材？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$axios({
          method: "post",
          url: "/material/deleteMaterial",
          data: {
            id: item.id,
            url: item.url,
          },
        }).then(() => {
          that.getMaterialPageList();
        });
      });
    },
    handleSuccess(response, file, fileList) {
      const that = this;
      this.$axios({
        method: "post",
        url: "/material/addMaterial",
        data: {
          groupId: this.groupId,
          name: file.name,
          url: response.data.data,
          type: "1",
        },
      }).then(() => {
        this.resultNumber++;
        if (fileList.length === this.resultNumber) {
          that.getMaterialPageList();
          this.resultNumber = 0;
        }
      });
    },
    beforeUpload(file) {
      // 判断文件类型
      let fileName = file.name;
      let reg = /^.+(.png|.jpg|.jpeg|.bmp|.gif)$/;
      if (!reg.test(fileName)) {
        this.$message.error("上传图片只能是 JPG、JPEG、PNG、GIF 格式!");
        return false;
      }
      let fileSize = file.size;
      const FIVE_M = this.settings.fileSize;
      // 判断文件大小
      if (fileSize > FIVE_M) {
        this.$message.error(
          "单张图片最大上传不能超过" + FIVE_M / 1024 / 1024 + "M"
        );
        return false;
      }
      return true;
    },
    // 监听上传失败
    handleError(e) {
      const msg = JSON.parse(e.message);
      this.$message.error(msg.message);
    },
    moveMaterial(index, type) {
      if (type === "up") {
        const tempOption = this.value[index - 1];
        this.$set(this.value, index - 1, this.value[index]);
        this.$set(this.value, index, tempOption);
      } else if (type === "down") {
        const tempOption = this.value[index + 1];
        this.$set(this.value, index + 1, this.value[index]);
        this.$set(this.value, index, tempOption);
      }
    },
    zoomMaterial(index) {
      this.dialogVisible = true;
      this.url = this.value[index].realUrl;
    },
    deleteMaterial(index) {
      const that = this;
      this.$confirm("是否确认删除？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(function () {
        that.value.splice(index, 1);
        that.urls.splice(index, 1);
      });
    },
    toSelectMaterial() {
      this.listDialogVisible = true;
      if (this.tableData.length <= 0) {
        this.getMaterialGroupInfo();
      }
    },
    getMaterialGroupInfo() {
      this.materialGroupLoading = true;
      this.$axios({
        method: "post",
        url: "/materialGroup/getMaterialGroupList",
      })
        .then((res) => {
          this.materialGroupLoading = false;
          const materialGroupList = res.data.data;
          materialGroupList.unshift({
            id: 0,
            name: "全部分组",
          });
          this.materialGroupList = materialGroupList;
          this.tabClick({ index: 0 });
        })
        .catch(() => {
          this.materialGroupLoading = false;
        });
    },
    tabClick(tab) {
      this.urls = [];
      const index = Number(tab.index);
      const materialGroupObj = this.materialGroupList[index];
      materialGroupObj.index = index;
      this.materialGroupObj = materialGroupObj;
      this.materialGroupObjId = materialGroupObj.id + "";
      this.page.current = 1;
      this.page.total = 0;
      if (materialGroupObj.id !== 0) {
        this.groupId = materialGroupObj.id;
      } else {
        this.groupId = 0;
      }
      this.getMaterialPageList();
    },
    getMaterialPageList() {
      this.tableLoading = true;
      this.$axios({
        method: "post",
        url: "/material/getMaterialList",
        data: {
          groupId: this.groupId,
          pageNum: this.page.current,
          pageSize: this.page.pageSize,
        },
      })
        .then((res) => {
          const tableData = res.data.data.list;
          this.page.total = res.data.data.total;
          this.page.current = res.data.data.pageNum;
          this.page.pageSize = res.data.data.pageSize;
          this.tableData = tableData;
          this.tableLoading = false;
        })
        .catch(() => {
          this.tableLoading = false;
        });
    },
    materialGroupAdd() {
      const that = this;
      this.$prompt("请输入分组名", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputType: "text",
        inputValidator: (value) => {
          if (!value.trim()) {
            return "分组名不能为空";
          }
        },
        beforeClose: (action, instance, done) => {
          // 取消回车确认事件
          if (action === "confirm") {
            instance.$refs["confirm"].$el.onclick = (function (e) {
              e = e || window.event;
              if (e.detail !== 0) {
                done();
              }
            })();
          } else {
            done();
          }
        },
      }).then(({ value }) => {
        this.$axios({
          method: "post",
          url: "/materialGroup/addMaterialGroup",
          params: {
            groupName: value.trim(),
          },
        }).then(function () {
          that.getMaterialGroupInfo();
        });
      });
    },
    materialGroupEdit(materialGroupObj) {
      const that = this;
      this.$prompt("请输入分组名", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputValue: materialGroupObj.name,
        inputType: "text",
        inputValidator: (value) => {
          if (!value.trim()) {
            return "分组名不能为空";
          }
        },
        beforeClose: (action, instance, done) => {
          // 取消回车确认事件
          if (action === "confirm") {
            instance.$refs["confirm"].$el.onclick = (function (e) {
              e = e || window.event;
              if (e.detail !== 0) {
                done();
              }
            })();
          } else {
            done();
          }
        },
      }).then(({ value }) => {
        this.$axios({
          method: "post",
          url: "/materialGroup/updateMaterialGroup",
          data: {
            id: materialGroupObj.id,
            name: value.trim(),
          },
        }).then(function () {
          materialGroupObj.name = value;
          that.$set(
            that.materialGroupList,
            materialGroupObj.index,
            materialGroupObj
          );
        });
      });
    },
    materialGroupDelete(materialGroupObj) {
      const that = this;
      this.$confirm(
        "是否确定删除该分组？（该操作会同时删除该分组下得所有素材，且不可逆）",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.$axios({
          method: "post",
          url: "/materialGroup/deleteMaterialGroup",
          params: {
            id: materialGroupObj.id,
          },
        }).then(function () {
          that.$delete(that.materialGroupList, materialGroupObj.index);
          that.tabClick({ index: 0 });
        });
      });
    },
  },
};
</script>

<style scoped>
/deep/ .el-icon-circle-close {
  color: red;
}
.material-name {
  padding: 8px 0;
}
.col-do {
  text-align: center;
}
.button-do {
  padding: unset !important;
  font-size: 12px;
}
</style>
