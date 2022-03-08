package com.hsjnb.backend.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

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
 * @date : Created in 2022/01/16 12:50
 * @description : 占位符工具类
 */

public class PlaceholderUtils {

    // 标识忽略
    private static final int ignoreMark = 0;

    // 标识已读取到'$'字符
    private static final int startMark = 1;

    // 标识已读取到'{'或'}'字符
    private static final int readMark = 2;

    public static List<String> getPlaceholderList(String context) {
        char[] contextChar = context.toCharArray();
        StringBuilder contextSoFar = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        // 存储占位符 位置信息集合
        List<String> placeholderList = new ArrayList<>();
        // 当前标识
        int modeMark = ignoreMark;
        for (char c : contextChar) {
            contextSoFar.append(c);
            switch (c) {
                case '$': {
                    modeMark = startMark;
                } break;
                case '{': {
                    if (modeMark == startMark) {
                        modeMark = readMark;
                    } else {
                        if (modeMark == readMark) {
                            modeMark = ignoreMark;
                            sb = new StringBuilder();
                        }
                    }
                } break;
                case '}': {
                    if (modeMark == readMark) {
                        modeMark = ignoreMark;
                        String str = sb.toString();
                        if (StringUtils.isNotEmpty(str)) {
                            placeholderList.add(str);
                            contextSoFar = new StringBuilder();
                        }
                        sb = new StringBuilder();
                    } else if (modeMark == startMark) {
                        modeMark = ignoreMark;
                        sb = new StringBuilder();
                    }
                } break;
                default: {
                    if (modeMark == readMark) {
                        sb.append(c);
                    } else if (modeMark == startMark) {
                        modeMark = ignoreMark;
                        sb = new StringBuilder();
                    }
                }
            }
        }
        return placeholderList;
    }

}
