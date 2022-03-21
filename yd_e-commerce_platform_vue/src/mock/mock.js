const Mock = require("mockjs");

const Random = Mock.Random;

const Result = {
  code: 200,
  msg: "操作成功",
  data: null,
};

Mock.mock("/getCaptcha", "post", () => {
  Result.data = {
    key: Random.string(32),
    captchaImg: Random.dataImage("122x43", "p7n5w1"),
  };
  return Result;
});

Mock.mock("/login", "post", () => {
  // 无法在header中传入数jwt

  // Result.code = 400
  // Result.msg = '验证码错误'
  return Result;
});

Mock.mock("/manage/getUserInfo", "post", () => {
  Result.data = {
    id: "1",
    nickname: "超级管理员",
    avatar:
      "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif",
  };
  return Result;
});
