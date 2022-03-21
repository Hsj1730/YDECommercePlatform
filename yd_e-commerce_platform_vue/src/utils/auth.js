import Cookies from "js-cookie";
import Config from "../settings";

const TokenKey = "Admin-Token";

export function getToken() {
  return Cookies.get(TokenKey);
}

// 设置浏览器token
export function setToken(token, rememberMe) {
  if (rememberMe) {
    return Cookies.set(TokenKey, token, { expires: Config.tokenCookieExpires });
  } else {
    return Cookies.set(TokenKey, token);
  }
}

export function removeToken() {
  return Cookies.remove(TokenKey);
}
