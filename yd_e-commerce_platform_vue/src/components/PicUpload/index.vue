<template>
  <div>
    <el-upload
      class="pic-uploader-component"
      :action="this.$axios.defaults.baseURL + '/goods/uploadAttrPic'"
      :headers="headers"
      :show-file-list="false"
      :on-success="handleUploadSuccess"
      :before-upload="beforeAvatarUpload"
    >
      <img v-if="value" :src="resourcesUrl + value" class="pic" />
      <i v-else class="el-icon-plus pic-uploader-icon" />
    </el-upload>
  </div>
</template>

<script>
import { getToken } from "../../utils/auth";
export default {
  name: "index",
  props: {
    value: {
      default: "",
      type: String,
    },
    index: {
      default: 0,
      type: Number,
    },
  },
  data() {
    return {
      resourcesUrl: "",
      headers: {
        Authorization: getToken(),
      },
    };
  },
  methods: {
    // 图片上传
    handleUploadSuccess(response) {
      const showChange = {
        index: this.index,
        pic: response.data.pic,
        link: response.data.link,
      };
      this.$emit("showChange", showChange);
    },
    // 限制图片上传大小
    beforeAvatarUpload(file) {
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
  },
};
</script>

<style lang="scss" scoped>
.pic-uploader-component .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  .pic-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 100%;
    height: 40px;
    //line-height: 178px;
    text-align: center;
  }
  .pic {
    width: 100%;
    height: 40px;
    display: block;
  }
}
.pic-uploader-component .el-upload:hover {
  border-color: #409eff;
}
</style>
