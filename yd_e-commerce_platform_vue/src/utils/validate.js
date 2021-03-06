export default {
  // 验证用户名,用户名不能存在中文
  validateUserNameChinese(username) {
    const pattern = /^[^一-龥]+$/;
    return !pattern.test(username) ? "用户名不能存在中文字符" : "";
  },
  // 验证密码
  validatePass(pass, level) {
    let result = "";
    switch (level) {
      case 1:
        result = this.validatePass_1(pass);
        break;
      case 2:
        result = this.validatePass_2(pass);
        break;
      case 3:
        result = this.validatePass_3(pass);
        break;
      case 4:
        result = this.validatePass_4(pass);
        break;
      case 5:
        result = this.validatePass_5(pass);
        break;
      default:
        result = this.validatePass_1(pass);
        break;
    }
    return result;
  },
  // 纯数字密码
  validatePass_1(pass) {
    const pattern = /^[0-9]{6,}$/;
    return !pattern.test(pass) ? "密码必须为纯数字" : "";
  },
  // 纯字母密码
  validatePass_2(pass) {
    const pattern = /^[a-zA-Z]{6,}$/;
    return !pattern.test(pass) ? "密码必须为英文字母" : "";
  },
  // 数字+字母密码
  validatePass_3(pass) {
    const pattern = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,}$/;
    return !pattern.test(pass) ? "密码必须包含英文字母和数字" : "";
  },
  // 数字+大小写字母+特殊字符任意三种密码
  validatePass_4(pass) {
    const pattern =
      /^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z._~!@#$^&*%?]+$)(?![a-z0-9]+$)(?![a-z._~!@#$^&*%?]+$)(?![0-9._~!@#$^&*%?]+$)[a-zA-Z0-9._~!@#$^&*%?]{6,}$/;
    return !pattern.test(pass)
      ? "密码必须包含大小写字母、数字和特殊字符中任意3种"
      : "";
  },
  // 数字+大小写字母+特殊字符全包含密码
  validatePass_5(pass) {
    const pattern =
      /^(?=.*[A-Za-z])(?=.*d)(?=.*[._~!@#$^&*%?])[A-Za-zd._~!@#$^&*%?]{6,}$/;
    return !pattern.test(pass) ? "密码必须包含大小写字母、数字和特殊字符" : "";
  },

  validateMobile(mobile) {
    const reg =
      /^((13[0-9])|(14[579])|(15[0-35-9])|(166)|(17[35-8])|(18[0-9])|(19[8,9]))\d{8}$/;
    const regHK = /^([5689])\d{7}$/;
    return reg.test(mobile) || regHK.test(mobile);
  },
  validEmail(email) {
    const reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    return reg.test(email);
  },
  validChinese(str) {
    const reg = /^[\u4e00-\u9fa5]*$/;
    return reg.test(str);
  },
  validCardId(str) {
    // 一代
    const reg1 = /^[1-9]\d{7}(?:0\d|10|11|12)(?:0[1-9]|[1-2][\d]|30|31)\d{3}$/;
    // 二代
    const reg2 =
      /^[1-9]\d{5}(?:18|19|20)\d{2}(?:0[1-9]|10|11|12)(?:0[1-9]|[1-2]\d|30|31)\d{3}[\dXx]$/;
    return reg1.test(str) || reg2.test(str);
  },
};
