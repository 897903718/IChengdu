/**
 * Xun.Zhang
 */
package chanlytech.ichengdu.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具
 */
public class ValidateUtils {
    /**
     * 是否是电话号码
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNumber(String phoneNum) {
        String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phoneNum);
        return m.find();
    }

    /**
     * 是否是邮箱
     * @param mail
     * @return
     */
    public static boolean isMail(String mail){
        String regExp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mail);
        return m.find();
    }

}
