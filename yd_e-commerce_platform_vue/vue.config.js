"use strict";
const path = require("path");

function resolve(dir) {
  return path.join(__dirname, dir);
}

module.exports = {
  runtimeCompiler: true,

  // eslint-loader 是否在保存的时候检查
  lintOnSave: true,

  // 生产环境是否生成 sourceMap 文件
  productionSourceMap: false,

  devServer: {
    disableHostCheck: true,
    port: 9099,
    host: "127.0.0.1",
    https: false,
    open: true, //open 在devServer启动且第一次构建完成时，自动用我们的系统的默认浏览器去打开要开发的网页
    hotOnly: false, // hot 和 hotOnly 的区别是在某些模块不支持热更新的情况下，前者会自动刷新页面，后者不会刷新页面，而是在控制台输出热更新失败
  },

  chainWebpack(config) {
    // set svg-sprite-loader
    config.module.rule("svg").exclude.add(resolve("src/assets/icons")).end();
    config.module
      .rule("icons")
      .test(/\.svg$/)
      .include.add(resolve("src/assets/icons"))
      .end()
      .use("svg-sprite-loader")
      .loader("svg-sprite-loader")
      .options({
        symbolId: "icon-[name]",
      })
      .end();
  },

  publicPath: "/",
  outputDir: "dist",
};
