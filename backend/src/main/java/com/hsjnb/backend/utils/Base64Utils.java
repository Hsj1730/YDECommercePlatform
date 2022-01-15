package com.hsjnb.backend.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.*;

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
 * @date : Created in 2022/01/15 12:25
 * @description : BASE64编码解码工具包
 */

public class Base64Utils {

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * BASE64字符串解码为二进制数据
     * @param base64 编码字符串
     * @return 解码后的二进制数据
     */
    public static byte[] decode(String base64) {
        return Base64.decodeBase64(base64.getBytes());
    }

    /**
     * 二进制数据编码为BASE64字符串
     * @param bytes 二进制数据
     * @return BASE64字符串
     */
    public static String encode(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * 将文件编码为BASE64字符串
     * 大文件慎用，可能会导致内存溢出
     * @param filePath 文件路径
     * @return BASE64字符串
     */
    public static String encodeFile(String filePath) throws IOException {
        byte[] bytes = fileToByte(filePath);
        return encode(bytes);
    }

    /**
     * BASE64字符串转回文件
     * @param filePath 文件路径
     * @param base64 编码字符串
     */
    public static void decodeToFile(String filePath, String base64) throws IOException {
        byte[] bytes = decode(base64);
        byteArrayToFile(bytes, filePath);
    }

    /**
     * 文件转换为二进制数组
     * @param filePath 文件路径
     * @return 二进制数组
     */
    public static byte[] fileToByte(String filePath) throws IOException {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache,0,nRead);
                out.flush();
            }
            data = out.toByteArray();
            out.close();
            in.close();
        }
        return data;
    }

    /**
     * 二进制数据写文件
     * @param bytes 二进制数据
     * @param filePath 文件生成目录
     */
    public static void byteArrayToFile(byte[] bytes, String filePath) throws IOException {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            if (destFile.getParentFile().mkdirs()) {
                System.out.println("父文件创建成功");
            }
        }
        if (destFile.createNewFile()) {
            System.out.println("文件创建成功");
        }
        OutputStream out = new FileOutputStream(destFile);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead;
        if ((nRead = in.read(cache)) != -1) {
            out.write(cache, 0, nRead);
            out.flush();
        }
        out.close();
        in.close();
    }

}
