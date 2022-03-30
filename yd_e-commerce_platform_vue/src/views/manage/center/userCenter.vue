<template>
  <div class="app-container" style="margin-top: -20px">
    <el-row :gutter="20">
      <el-col
        :xs="24"
        :sm="24"
        :md="8"
        :lg="6"
        :xl="5"
        style="margin-bottom: 10px"
      >
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>个人信息</span>
          </div>
          <div>
            <div style="text-align: center">
              <el-upload
                :show-file-list="false"
                :on-success="handleSuccess"
                :on-error="handleError"
                :before-upload="beforeUpload"
                :on-exceed="handleExceed"
                :headers="headers"
                :action="this.$axios.defaults.baseURL + '/user/uploadAvatar'"
                class="avatar-uploader"
              >
                <img
                  :src="user.avatar"
                  alt=""
                  title="点击上传头像"
                  class="avatar"
                />
              </el-upload>
            </div>
            <ul class="user-info">
              <li>
                <div style="height: 100%">
                  <svg-icon icon-class="login" /> 登录账号
                  <div class="user-right">{{ user.username }}</div>
                </div>
              </li>
              <li>
                <svg-icon icon-class="user1" /> 用户昵称
                <div class="user-right">{{ user.nickname }}</div>
              </li>
              <li>
                <svg-icon icon-class="user1" /> 性别
                <div class="user-right">
                  {{
                    user.gender === "0"
                      ? "未知"
                      : user.gender === "1"
                      ? "男"
                      : "女"
                  }}
                </div>
              </li>
              <li>
                <svg-icon icon-class="phone" /> 手机号码
                <div class="user-right">{{ user.mobile }}</div>
              </li>
              <li>
                <svg-icon icon-class="email" /> 用户邮箱
                <div class="user-right">{{ user.email }}</div>
              </li>
              <li>
                <svg-icon icon-class="anq" /> 安全设置
                <div class="user-right">
                  <a @click="$refs.pass.dialog = true">修改密码</a>
                  &nbsp;
                  <a @click="$refs.email.dialog = true">修改邮箱</a>
                </div>
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="16" :lg="18" :xl="19">
        <!--    用户资料    -->
        <el-card class="box-card">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="用户资料" name="first">
              <el-form
                ref="form"
                :model="form"
                style="margin-top: 10px"
                size="small"
                label-width="65px"
              >
                <el-form-item
                  label="昵称"
                  prop="nickname"
                  :rules="{
                    required: true,
                    message: '请输入用户昵称',
                    trigger: 'blur',
                  }"
                >
                  <el-input
                    v-model.trim="form.nickname"
                    clearable
                    style="width: 35%"
                  />
                  <span style="color: #c0c0c0; margin-left: 10px"
                    >用户昵称不作为登录使用</span
                  >
                </el-form-item>
                <el-form-item
                  label="手机号"
                  prop="mobile"
                  :rules="[
                    {
                      required: true,
                      trigger: 'blur',
                      message: '请输入手机号',
                    },
                    { validator: checkMobile },
                  ]"
                >
                  <el-input
                    v-model.trim="form.mobile"
                    oninput="value=value.replace(/[^\d]/g,'')"
                    style="width: 35%"
                    maxLength="12"
                    clearable
                  />
                  <span style="color: #c0c0c0; margin-left: 10px"
                    >手机号码不能重复</span
                  >
                </el-form-item>
                <el-form-item label="性别">
                  <el-radio-group v-model="form.gender" style="width: 200px">
                    <el-radio label="0" style="display: none">未知</el-radio>
                    <el-radio label="1">男</el-radio>
                    <el-radio label="2">女</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item>
                  <el-button size="mini" type="primary" @click="saveUserInfo"
                    >保存配置</el-button
                  >
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <!--    操作日志    -->
            <el-tab-pane label="操作日志" name="second">
              <el-table v-loading="loading" :data="logData" style="width: 100%">
                <el-table-column prop="name" label="行为" align="center" />
                <el-table-column
                  prop="ip"
                  label="IP"
                  width="150"
                  align="center"
                />
                <el-table-column
                  prop="browser"
                  label="浏览器"
                  width="100"
                  align="center"
                />
                <el-table-column
                  prop="status"
                  label="请求状态"
                  width="100"
                  align="center"
                >
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.status === 200" type="success"
                      >成功</el-tag
                    >
                    <el-tag v-else type="danger">失败</el-tag>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="executeTime"
                  label="请求耗时"
                  align="center"
                  width="100"
                >
                  <template slot-scope="scope">
                    <el-tag v-if="scope.row.executeTime <= 300"
                      >{{ scope.row.executeTime }}ms</el-tag
                    >
                    <el-tag
                      v-else-if="scope.row.executeTime <= 1000"
                      type="warning"
                      >{{ scope.row.executeTime }}ms</el-tag
                    >
                    <el-tag v-else type="danger"
                      >{{ scope.row.executeTime }}ms</el-tag
                    >
                  </template>
                </el-table-column>
                <el-table-column align="center">
                  <template slot="header">
                    <div
                      style="display: inline-block; cursor: pointer"
                      @click="getUserLogList"
                    >
                      请求日期<i
                        class="el-icon-refresh"
                        style="margin-left: 10px"
                      />
                    </div>
                  </template>
                  <template slot-scope="scope">
                    <span>{{ scope.row.createTime }}</span>
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
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
    <update-pass ref="pass" />
    <update-email ref="email" :email="user.email" />
  </div>
</template>

<script>
import store from "../../../store";
import updatePass from "./part/updatePass";
import updateEmail from "./part/updateEmail";
import { getToken } from "../../../utils/auth";
export default {
  name: "userCenter",
  components: {
    updatePass,
    updateEmail,
  },
  data() {
    return {
      user: {},
      activeName: "first",
      form: {},
      headers: {
        Authorization: getToken(),
      },
      current: 1,
      pageSize: 10,
      total: 0,
      logData: [],
      loading: false,
    };
  },
  mounted() {
    this.getUserInfo();
  },
  methods: {
    handleExceed() {
      this.$message({ type: "error", message: "最多支持1个附件上传" });
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
        this.$message.error("最大上传" + FIVE_M / 1024 / 1024 + "M");
        return false;
      }
      return true;
    },
    getUserLogList() {
      this.loading = true;
      this.$axios({
        method: "post",
        url: "/log/getUserLogList",
        data: {
          userId: this.user.id,
          pageNum: this.current,
          pageSize: this.pageSize,
        },
      }).then((res) => {
        this.loading = false;
        this.logData = res.data.data.list;
        this.total = res.data.data.total;
      });
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.getUserLogList();
    },
    handleCurrentChange(val) {
      this.current = val;
      this.getUserLogList();
    },
    handleClick(tab) {
      if (tab.name === "second") {
        this.getUserLogList();
      }
    },
    saveUserInfo() {
      if (this.$refs["form"]) {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            this.$axios({
              method: "post",
              url: "/user/saveUserInfo",
              data: this.form,
            }).then(() => {
              this.$message.success("保存成功");
              this.getUserInfo();
            });
          }
        });
      }
    },
    handleSuccess() {
      this.$message.success("头像修改成功");
      this.getUserInfo();
    },
    // 监听上传失败
    handleError(e) {
      const msg = JSON.parse(e.message);
      this.$message.error(msg.message);
    },
    getUserInfo() {
      this.$axios({
        method: "post",
        url: "/user/getUserDetailedInfo",
      }).then((res) => {
        this.user = res.data.data;
        this.form = {
          id: this.user.id,
          nickname: this.user.nickname,
          mobile: this.user.mobile,
          gender: this.user.gender,
        };
        store.commit("setUserInfo", this.user);
      });
    },
    checkMobile(rule, value, callback) {
      if (!this.dataValidate.validateMobile(value)) {
        return callback(new Error("请输入正确的11位手机号码"));
      }
      if (value !== this.user.mobile) {
        this.$axios({
          method: "post",
          url: "/user/checkMobile",
          params: {
            mobile: value,
          },
        }).then((res) => {
          if (res.data.data !== 0) {
            return callback(new Error("该手机号已存在"));
          }
          return callback();
        });
      }
      return callback();
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss">
.avatar-uploader-icon {
  font-size: 28px;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
.user-info {
  padding-left: 0;
  list-style: none;
  li {
    border-bottom: 1px solid #f0f3f4;
    padding: 11px 0;
    font-size: 13px;
  }
  .user-right {
    float: right;
    a {
      color: #317ef3;
    }
  }
}
</style>
