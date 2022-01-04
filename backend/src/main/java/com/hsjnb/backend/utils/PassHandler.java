package com.hsjnb.backend.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author : Hsj1730
 * @version : 1.0
 * @date : Created in 2021/12/31 17:07
 * @description : 通用的密码处理类,用于生成密码和校验密码
 */

public class PassHandler {

    private static final String PUBLIC_KEY = "publicKey";

    private static final String PRIVATE_KEY = "privateKey";

    /**
     * 校验MD5密码是否一致
     * @param inputPass 用户传入的密码
     * @param salt 数据库保存的密码随机码
     * @param pass 数据库保存的密码
     * @return boolean
     */
    public static boolean checkMD5Pass(String inputPass , String salt , String pass) {
        String passMD5 = MD5Utils.md5(inputPass);
        return Objects.equals(MD5Utils.md5(passMD5 + salt), pass);
    }

    /**
     * 用于用户注册时产生一个密码
     * @param inputPass 输入的密码
     * @return PassInfo 返回一个密码对象，记得保存
     */
    public static PassMD5Info buildPassword(String inputPass) {
        // 产生一个随机码
        String salt = RandomStringUtils.randomAlphabetic(6);
        // 加密后的密码
        String encryptPassword = MD5Utils.md5(MD5Utils.md5(inputPass) + salt);
        //返回对象
        return new PassMD5Info(salt, encryptPassword);
    }

    /**
     * 用于用户登录时传递RSA加密的公钥给前端
     * @param passRSAInfos 数据库中的RSA列表
     * @return PassRSAInfo 返回一个RSA公钥对象
     */
    public static PassRSAInfo getRSAPublicKey(List<PassRSAInfo> passRSAInfos) {
        int min = 0;
        int max = passRSAInfos.size();
        if (max != 0) {
            return passRSAInfos.get(new Random().nextInt((max - min) + 1) + min);
        }
        return null;
    }

    /**
     * 用于用户登录时对前端密文进行解密
     * @param data 密文
     * @param privateKey 私钥
     * @return 明文
     */
    public static String decryptRSAPass(String data,String privateKey) {
        return RSAUtils.decryptDataOnJavaByPrivateKey(data,privateKey);
    }

    /**
     * 生成密钥对
     * @return 密钥对
     */
    public static Map<String,String> getKeyPair() {
        try {
            Map<String, Object> keyPair = RSAUtils.getKeyPair();
            Map<String, String> keyPairString = new HashMap<>();
            keyPairString.put(PUBLIC_KEY,RSAUtils.getPublicKey(keyPair));
            keyPairString.put(PRIVATE_KEY,RSAUtils.getPrivateKey(keyPair));
            return keyPairString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
