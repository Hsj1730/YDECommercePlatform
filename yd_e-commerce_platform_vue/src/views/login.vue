<template>
  <div class="login">
    <div>
      <el-row type="flex" class="row-bg" justify="center" align="center">
        <el-col :xl="6" :lg="7">
          <h2
            style="
              color: #ffffff;
              font-size: 24px;
              padding: 0;
              margin-bottom: 10px;
            "
          >
            {{ loginTxt.loginHeader }}
          </h2>
          <ul
            style="
              margin: 0;
              color: #ffffff;
              font-size: 16px;
              line-height: 25px;
              padding-left: 20px;
            "
          >
            <li v-for="item in loginTxt.loginBody" :key="item.id">
              {{ item.describe }}
            </li>
          </ul>
          <h3 style="color: #ffffff">
            {{ loginTxt.loginFooter }}
          </h3>
        </el-col>
        <el-col :span="1">
          <el-divider direction="vertical"></el-divider>
        </el-col>
        <el-col :xl="6" :lg="7">
          <el-form
            :model="loginForm"
            :rules="rules"
            ref="loginForm"
            label-width="80px"
            label-position="center"
            class="login-from"
          >
            <h3 class="title">
              {{ this.settings.title }}
            </h3>
            <el-form-item prop="username" label="用户名" style="width: 330px">
              <el-input
                v-model.trim="loginForm.username"
                type="text"
                maxlength="18"
                auto-complete="off"
              >
                <svg-icon
                  slot="prefix"
                  icon-class="user"
                  class="el-input__icon input-icon"
                />
              </el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码" style="width: 330px">
              <el-input
                v-model.trim="loginForm.password"
                type="password"
                maxlength="18"
                auto-complete="off"
              >
                <svg-icon
                  slot="prefix"
                  icon-class="password"
                  class="el-input__icon input-icon"
                />
              </el-input>
            </el-form-item>
            <el-form-item prop="captcha" label="验证码" style="width: 330px">
              <el-input
                v-model.trim="loginForm.captcha"
                type="text"
                maxlength="5"
                style="width: 151px; float: left"
              >
                <svg-icon
                  slot="prefix"
                  icon-class="validCode"
                  class="el-input__icon input-icon"
                />
              </el-input>
              <el-image
                :src="captchaImg"
                class="captchaImg"
                @click="getCaptcha"
              />
            </el-form-item>
            <el-checkbox
              v-model="loginForm.rememberMe"
              style="margin: 0 0 15px 5px; font-weight: bolder"
            >
              记住我
            </el-checkbox>
            <el-form-item style="margin-right: 60px">
              <el-button type="primary" @click="submitForm('loginForm')"
                >登录</el-button
              >
              <el-button @click="resetForm('loginForm')">重置</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
    </div>
    <div class="footer" v-if="this.settings.showFooter">
      <span v-html="this.settings.footerTxt" />
      <span>&nbsp;&nbsp;</span>
      <a href="http://beian.miit.gov.cn/" target="_blank">{{
        this.settings.caseNumber
      }}</a>
    </div>
  </div>
</template>

<script>
import compile from "../utils/secret.js";
import { setToken } from "../utils/auth";
export default {
  name: "login",
  data() {
    const validateUserName = (rule, value, callback) => {
      if (value === "" || value === null) {
        callback(new Error("请输入用户名"));
      } else if (value.length < 5) {
        callback(new Error("用户名最小长度为5位"));
      } else if (value.length > 18) {
        callback(new Error("用户名最大长度为18位"));
      } else {
        const message = this.dataValidate.validateUserNameChinese(value);
        if (message === "") {
          callback();
        } else {
          callback(new Error(message));
        }
      }
    };
    const validatePassword = (rule, value, callback) => {
      if (value === "" || value === null) {
        callback(new Error("请输入密码"));
      } else if (value.length < 6) {
        callback(new Error("密码最小长度为6位"));
      } else if (value.length > 18) {
        callback(new Error("密码最大长度为18位"));
      } else {
        const message = this.dataValidate.validatePass(
          value,
          this.settings.level
        );
        if (message === "") {
          callback();
        } else {
          callback(new Error(message));
        }
      }
    };
    const validateCaptcha = (rule, value, callback) => {
      if (value === "" || value === null) {
        callback(new Error("请输入验证码"));
      } else if (value.length !== 5) {
        callback(new Error("验证码长度为5个字符"));
      } else {
        callback();
      }
    };
    return {
      loginForm: {
        username: "",
        password: "",
        captcha: "",
        key: "",
        rememberMe: false,
      },
      rules: {
        username: [{ validator: validateUserName, trigger: "blur" }],
        password: [{ validator: validatePassword, trigger: "blur" }],
        captcha: [{ validator: validateCaptcha, trigger: "blur" }],
      },
      captchaImg: null,
      loginTxt: this.settings.loginTxt,
    };
  },
  mounted() {
    this.getCaptcha();
    this.getCookie();
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const user = {
            username: this.loginForm.username,
            password: this.loginForm.password,
            captcha: this.loginForm.captcha,
            rememberMe: this.loginForm.rememberMe,
            key: this.loginForm.key,
          };
          if (user.rememberMe) {
            const rememberAccount = {};
            rememberAccount.username = compile.Encrypt(user.username);
            rememberAccount.password = compile.Encrypt(user.password);
            rememberAccount.rememberMe = compile.Encrypt(user.rememberMe);
            this.Cookies.set(
              "rememberAccount",
              compile.Encrypt(JSON.stringify(rememberAccount)),
              { expires: this.settings.passCookieExpires }
            );
          } else {
            this.Cookies.remove("rememberAccount");
          }
          user.password = this.rsa.encrypt(user.password);
          this.$axios({
            method: "post",
            url: "/login",
            data: user,
          })
            .then((res) => {
              if (res.data.code === 200) {
                const jwt = res.headers["authorization"];
                const rememberMe = user.rememberMe;
                setToken(jwt, rememberMe);
                this.$store.commit("SET_TOKEN", jwt);
                this.$message({
                  message: "登陆成功",
                  type: "success",
                });
                this.$router.push("/");
              } else {
                this.getCaptcha();
              }
            })
            .catch(() => {
              this.getCaptcha();
            });
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    getCaptcha() {
      this.$axios.post("/getCaptcha").then((res) => {
        this.loginForm.key = res.data.data.key;
        this.captchaImg = res.data.data.captchaImg;
        this.loginForm.captcha = "";
      });
    },
    getCookie() {
      let username = this.loginForm.username;
      let password = this.loginForm.password;
      let rememberMe = this.loginForm.rememberMe;
      let rememberAccount =
        this.Cookies.get("rememberAccount") === undefined
          ? ""
          : this.Cookies.get("rememberAccount");
      if (rememberAccount !== "") {
        rememberAccount = JSON.parse(compile.Decrypt(rememberAccount));
        username = compile.Decrypt(rememberAccount.username);
        password = compile.Decrypt(rememberAccount.password);
        rememberMe = Boolean(compile.Decrypt(rememberAccount.rememberMe));
      }
      this.loginForm = {
        username: username,
        password: password,
        rememberMe: rememberMe,
        captcha: "",
      };
    },
  },
};
</script>

<style scoped type="text/css">
.el-row {
  width: 100vw;
  height: 95vh;
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
  height: 5vh;
  overflow: auto;
  display: flex;
  align-items: center;
  text-align: center;
  justify-content: center;
  color: #ffffff;
  font-size: 14px;
  letter-spacing: 1px;
  font-family: Arial, serif;
}

.el-form {
  margin: 0 auto;
}

.login-from {
  border-radius: 6px;
  background: rgba(255, 255, 255, 0.6);
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
