const Mock = require('mockjs')

const Random = Mock.Random

const Result = {
  code: 200,
  msg: '操作成功',
  data: null
}

Mock.mock('/getCaptcha', 'post', () => {
  Result.data = {
    key: Random.string(32),
    captchaImg: Random.dataImage('122x43', 'p7n5w')
  }
  return Result
})

Mock.mock('/login', 'post', () => {
  // 无法在header中传入数jwt

  Result.code = 400
  Result.msg = '验证码错误'
  return Result
})
