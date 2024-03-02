/**
 * FileName: VerifyCode
 * Author:   嘉平十七
 * Date:     2021/2/2 18:56
 * Description: 验证码
 */
package com.hunau.competition.utils;

import java.util.Random;

public class VerifyCodeUtils {

    /**
     * 生成字符数字验证码
     * @param length 自定义的长度
     * @return String类型的验证码
     */
    public static String getVerCode(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    public static void main(String[] args) {
        System.out.println(getVerCode(6));
    }
}