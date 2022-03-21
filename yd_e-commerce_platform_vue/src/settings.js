export default {
  /**
   * 是否是服务器
   */
  isServer: false,

  /**
   * 密码级别1,2,3,4,5
   */
  level: 1,

  /**
   * 网站后台服务端本地地址
   */
  serverLocalAddress: "http://127.0.0.1:8099",

  /**
   * 网站后台服务端服务器地址
   */
  serverServerAddress: "http://hsjnb.com:9090",

  /**
   * 网站标题
   */
  title: "YD(亿点)电子商务平台管理系统",

  /**
   * 是否显示设置的底部信息
   */
  showFooter: true,

  /**
   * 底部文字，支持html语法
   */
  footerTxt: "YDE-Commerce-Platform(亿点电子商务平台)",

  /**
   * 备案号
   */
  caseNumber: "渝ICP备19015593号",

  /**
   * 记住密码状态下的密码在Cookie中存储的天数
   */
  passCookieExpires: 7,

  /**
   * 记住密码状态下的Token在Cookie中存储的天数
   */
  tokenCookieExpires: 1,

  /**
   * 头部跳转路径
   */
  headerLink: [
    {
      id: "1",
      describe: "GitHub",
      url: "https://github.com/Hsj1730/",
    },
    {
      id: "2",
      describe: "Gitee",
      url: "https://gitee.com/hsj1730/",
    },
    {
      id: "3",
      describe: "Blog",
      url: "http://hsjnb.com/",
    },
  ],

  /**
   * 登录页内容
   */
  loginTxt: {
    loginHeader: "欢迎来到电子商务平台管理系统",
    loginBody: [
      {
        id: "1",
        describe: "Springboot2",
      },
      {
        id: "2",
        describe: "SpringSecurity",
      },
      {
        id: "3",
        describe: "MySQL8",
      },
      {
        id: "4",
        describe: "Mybatis",
      },
      {
        id: "5",
        describe: "MybatisPlus",
      },
      {
        id: "6",
        describe: "Jwt",
      },
      {
        id: "7",
        describe: "Redis",
      },
      {
        id: "8",
        describe: "Vue",
      },
      {
        id: "9",
        describe: "Element-UI",
      },
    ],
    loginFooter: "个人博客：http://hsjnb.com",
  },
};
