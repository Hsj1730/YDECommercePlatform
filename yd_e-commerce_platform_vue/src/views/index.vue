<template :style="{ height: `${height}px` }">
  <div
    class="dashboard-editor-container"
    style="padding: 20px 20px 210px 20px; background: #f2f2f2; margin: 0 -15px"
  >
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <h3>项目介绍</h3>
        <div
          style="
            font-size: 15px;
            text-indent: 2em;
            padding: 0;
            margin-top: 20px;
          "
        >
          基于Spring Boot+Spring Security+JWT+Vue前后端分离的电子商务管理系统
          &emsp;(&nbsp;一口一口慢慢品尝哦~ &nbsp;)
        </div>
      </div>
      <h3>技术</h3>
      <ol>
        <li>开发语言：Java</li>
        <li>数据库：Mysql + Redis</li>
        <li>前端框架：Vue + Axios</li>
        <li>前端组件：Element</li>
        <li>后端框架：Spring Boot</li>
        <li>权限：Spring Security</li>
      </ol>
      <div class="text-right">
        <el-button type="primary" @click.native="jumpUrl('code-github')"
          >项目地址1</el-button
        >
        <el-button type="primary" @click.native="jumpUrl('code-gitee')"
          >项目地址2</el-button
        >
        <el-button type="success" @click.native="jumpUrl('code-blog')"
          >个人博客</el-button
        >
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      height: document.documentElement.offsetHeight,
    };
  },
  methods: {
    jumpUrl(type) {
      switch (type) {
        case "code-github":
          window.open("https://github.com/Hsj1730/YDECommercePlatform");
          break;
        case "code-gitee":
          window.open("https://gitee.com/hsj1730/YDECommercePlatform");
          break;
        case "code-blog":
          window.open("http://hsjnb.com");
          break;
        case "demo":
          window.open("https://gitee.com/zhengqingya/java-workspace");
          break;
      }
    },
  },
};
</script>

<style scoped type="text/css">
.dashboard-editor-container {
  background-color: #e3e3e3;
  padding: 50px 60px 0;
}
</style>
