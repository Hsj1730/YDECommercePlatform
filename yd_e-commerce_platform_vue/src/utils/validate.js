export default {
  // 验证用户名,用户名不能存在中文
  validateUserNameChinese(username) {
    const pattern = /^[^一-龥]+$/;
    return !pattern.test(username) ? "用户名不能存在中文字符" : "";
  },
  validateNameChinese(username) {
    const pattern = /^[^一-龥]+$/;
    return !pattern.test(username) ? "菜单名称不能存在中文字符" : "";
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
};
