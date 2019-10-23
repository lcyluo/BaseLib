package com.lcy.base.core.utils;

import android.text.Html;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * <p/>
 * Created by lcy on 15/7/21.
 * email:lcyzxin@gmail.com
 * version 1.0
 */
public class RegularUtil {

    /**
     * 正则表达式:验证用户名(不包含中文和特殊字符)
     */
//    public static final String REGEX_USERNAME = ".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]";
    public static final String REGEX_USERNAME = "^(?!\\d+$)[\\da-zA-Z]*$";


    /**
     * 判断电话号码
     *
     * @param mobile 电话号码
     */
    public static boolean isMobileNO(String mobile) {
        Pattern p = Pattern.compile("^(1[3-9])\\d{9}$");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 判断邮箱
     *
     * @param email 邮箱
     */
    public static boolean isEmail(String email) {

        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static boolean isHttpUrl(String url) {

        String str = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(url);
        return m.matches();
    }

    /**
     * 判断是否全是数字
     *
     * @param str tag
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }


    public static String getStrFormat(String str) {
        String regEx = "\\[.*?\\]|\\(.*?\\)";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        return mat.replaceAll("");
    }

    public static String replaceNumber(String str) {
        String regEx = "(\\d+)";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        return mat.replaceAll("");
    }

    public static boolean isUserName(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 获取链接的后缀名
     *
     * @return
     */
    public static String parseSuffix(String url) {
        Pattern pattern = Pattern.compile("\\S*[?]\\S*");
        Matcher matcher = pattern.matcher(url);
        String[] spUrl = url.split("/");
        int len = spUrl.length;
        String endUrl = spUrl[len - 1];

        if (matcher.find()) {
            String[] spEndUrl = endUrl.split("\\?");
            String[] spEndUrl2 = spEndUrl[spEndUrl.length - 1].split("\\.");
            return spEndUrl2[spEndUrl2.length - 1];
        }
        String[] spEndUrl = endUrl.split("\\.");
        return spEndUrl[spEndUrl.length - 1];
    }

    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 定义script的正则表达式
     */
    private static final String REGEX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    /**
     * 定义style的正则表达式
     */
    private static final String REGEX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /**
     * 定义HTML标签的正则表达式
     */
    private static final String REGEX_HTML = "<[^>]+>";
    /**
     * 定义空格回车换行符
     */
    private static final String REGEX_SPACE = "\\s*|\t|\r|\n";

    public static String delHTMLTag(String htmlStr) {
        if (TextUtils.isEmpty(htmlStr)) return "";
        // 过滤script标签
        Pattern p_script = Pattern.compile(REGEX_SCRIPT, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");
        // 过滤style标签
        Pattern p_style = Pattern.compile(REGEX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");
        // 过滤html标签
        Pattern p_html = Pattern.compile(REGEX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");
        // 过滤空格回车标签
        Pattern p_space = Pattern.compile(REGEX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");
        return htmlStr.trim(); // 返回文本字符串
    }

    public static CharSequence formatHtml(String content) {
        if (TextUtils.isEmpty(content)) return "";
        return Html.fromHtml(content);
    }


    public static String getFormatPhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 格式化手机号1xx xxxx xxxx格式
     *
     * @param phone 手机号
     */
    public static String getSpacePhone(String phone) {
        if (TextUtils.isEmpty(phone)) return "";
        return phone.replaceAll("(1\\w{2})(\\w{4})(\\w{4})", "$1 $2 $3");
    }

    /**
     * 手机号格式化
     *
     * @param phone   手机号
     * @param replace 替换格式
     */
    public static String formatPhone(String phone, String replace) {
        if (TextUtils.isEmpty(phone)) return "";
        if (phone.length() != 11) return phone;
        return phone.replaceAll("\\B(?=(?:\\d{4})+$)", replace);
    }


    public static String clearAllSpace(String str) {
        if (TextUtils.isEmpty(str)) return "";
        return str.replaceAll("\\s+", "");
    }


    public static void setSpacePhoneText(String content, EditText editText) {
        int length = content.length();
        if (length == 4) {
            if (content.substring(3).equals(" ")) {
                content = content.substring(0, 3);
                editText.setText(content);
                editText.setSelection(content.length());
            } else { // +
                content = content.substring(0, 3) + " " + content.substring(3);
                editText.setText(content);
                editText.setSelection(content.length());
            }
        } else if (length == 9) {
            if (content.substring(8).equals(" ")) {
                content = content.substring(0, 8);
                editText.setText(content);
                editText.setSelection(content.length());
            } else {
                content = content.substring(0, 8) + " " + content.substring(8);
                editText.setText(content);
                editText.setSelection(content.length());
            }
        }
    }

    /**
     * 替换多个换行符为一个换行符
     *
     * @param content 被替换的内容
     * @return 替换结果
     */
    public static String formatSpaceStr(String content) {
        String result = "";
        if (!TextUtils.isEmpty(content)) {
            result = content.replaceAll("\n\n+", "\n").replaceAll("^\n+", "");
        }
        return result;
    }

    /**
     * 获取坡度值
     *
     * @param value 原字符串
     * @return 坡度值字符串
     */
    public static String getSlope(String value) {
        Pattern p = Pattern.compile("(?<=^@S=[-|+])\\d+(?=mm#)");
        Matcher m = p.matcher(value);
        if (m.find()) {
            return m.group();
        }
        return null;
    }
}
