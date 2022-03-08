<template>
  <div class="login">
    <div>
      <el-row type="flex" class="row-bg" justify="center" align="center">
        <el-col :xl="6" :lg="7">
          <h2 style="color: #ffffff;font-size: 24px;padding: 0;margin-bottom: 10px">
            欢迎来到电子商务平台管理系统
          </h2>
          <ul style="margin: 0;color: #ffffff;font-size: 16px;line-height: 25px;padding-left: 20px">
            <li>Springboot2</li>
            <li>SpringSecurity</li>
            <li>MySQL8</li>
            <li>Mybatis</li>
            <li>MybatisPlus</li>
            <li>Jwt</li>
            <li>Redis</li>
            <li>Vue</li>
          </ul>
          <h3 style="color: #ffffff;">
            个人博客：http://hsjnb.com
          </h3>
        </el-col>
        <el-col :span="1">
          <el-divider direction="vertical"></el-divider>
        </el-col>
        <el-col :xl="6" :lg="7">
          <el-form :model="loginForm" :rules="rules" ref="loginForm" label-width="80px" label-position="center" class="login-from">
            <h3 class="title">
              {{this.settings.title}}
            </h3>
            <el-form-item prop="username" label="用户名" style="width: 360px;">
              <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="用户名"/>
            </el-form-item>
            <el-form-item prop="password" label="密码" style="width: 360px;">
              <el-input v-model="loginForm.password" type="password" maxlength="18" auto-complete="off" placeholder="密码"/>
            </el-form-item>
            <el-form-item prop="captcha" label="验证码" style="width: 360px;">
              <el-input v-model="loginForm.captcha" type="text" maxlength="5" style="width: 180px; float: left;"/>
              <el-image :src="captchaImg" class="captchaImg" @click="getCaptcha"/>
            </el-form-item>
            <el-form-item style="margin-right: 60px;">
              <el-button type="primary" @click="submitForm('loginForm')">登录</el-button>
              <el-button @click="resetForm('loginForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
    <div class="footer" v-if="this.settings.showFooter">
      <span v-html="this.settings.footerTxt"/>
      <span>&nbsp;&nbsp;</span>
      <a href="http://beian.miit.gov.cn/" target="_blank" style="color: white">{{ this.settings.caseNumber }}</a>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        username: '',
        password: '',
        captcha: '',
        key: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 18, message: '密码最小长度为6位', trigger: 'blur' }
        ],
        captcha: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { min: 5, max: 5, message: '验证码长度为5个字符', trigger: 'blur' }
        ]
      },
      captchaImg: null
    }
  },
  mounted () {
    this.getCaptcha()
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const password = this.rsa.encrypt(this.loginForm.password.trim())
          this.$axios({
            method: 'post',
            url: '/login',
            data: {
              username: this.loginForm.username.trim(),
              password: password,
              captcha: this.loginForm.captcha.trim(),
              key: this.loginForm.key
            }
          }).then(res => {
            if (res.data.code === 200) {
              const jwt = res.headers.authorization
              this.$store.commit('SET_TOKEN', jwt)
              this.$message({
                message: '登陆成功',
                type: 'success'
              })
              this.$router.push('/index')
            } else {
              this.getCaptcha()
            }
          })
        } else {
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    getCaptcha () {
      this.$axios.post('/getCaptcha').then(res => {
        this.loginForm.key = res.data.data.key
        this.captchaImg = res.data.data.captchaImg
        this.loginForm.captcha = ''
      })
    }
  }
}
</script>

<style scoped type="text/css">
  .el-row {
    width: 100vw;
    height: 90vh;
    overflow: auto;
    display: flex;
    align-items: center;
    text-align: center;
    justify-content: center;
    padding-top: 20px;
    /*background-image: url("../assets/images/bg.jpg");*/
    /*background-size: cover;*/
  }

  .login {
    width: 100vw;
    height: 100vh;
    overflow: auto;
    /*display: flex;*/
    align-items: center;
    text-align: center;
    justify-content: center;
    background-image: url("../assets/images/bg.jpg");
    background-size: cover;
  }
  .footer {
    width: 100vw;
    height: 10vh;
    overflow: auto;
    display: flex;
    align-items: center;
    text-align: center;
    justify-content: center;
    color: #fff;
    letter-spacing: 1px;
    font-family: Arial, serif;
  }

  .el-form{
    margin: 0 auto;
  }

  .login-from {
    border-radius: 6px;
    background: rgba(255,255,255,0.6);
    font-weight: bolder;
    width: 400px;
    padding: 25px 25px 5px 25px;
    margin-left: 60px;
  }

  .el-divider {
    height: 250px;
  }

  .captchaImg {
    float: left;
    width: 90px;
    margin-left: 8px;
    border-radius: 4px;
  }

  .title {
    margin: 0 auto 30px auto;
    text-align: center;
    color: #707070;
  }

</style>
