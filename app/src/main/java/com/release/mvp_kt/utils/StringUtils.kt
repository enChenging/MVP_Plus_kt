@file:Suppress("NAME_SHADOWING", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.release.mvp_kt.utils

import android.text.TextUtils
import java.io.File
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.util.*
import java.util.regex.Pattern

/**
 * Created by long on 2016/8/29.
 * 字符串工具
 */
object StringUtils {


    /**
     * 字符串拼接,线程安全
     */
    fun buffer(vararg array: String): String {
        val s = StringBuffer()
        for (str in array) {
            s.append(str)
        }
        return s.toString()
    }

    /**
     * 字符串拼接,线程不安全,效率高
     */
    fun builder(vararg array: String): String {
        val s = StringBuilder()
        for (str in array) {
            s.append(str)
        }
        return s.toString()
    }


    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return `true`: null或全空格<br></br> `false`: 不为null且不全空格
     */
    fun isSpace(s: String?): Boolean {
        return s == null || s.trim { it <= ' ' }.length == 0
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return `true`: 相等<br></br>`false`: 不相等
     */
    fun equals(a: CharSequence, b: CharSequence): Boolean {
        if (a === b) return true
        if (a.length == b.length) {
            if (a is String && b is String) {
                return a == b
            } else {
                for (i in 0 until a.length) {
                    if (a[i] != b[i]) return false
                }
                return true
            }
        }
        return false
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return `true`: 相等<br></br>`false`: 不相等
     */
    fun equalsIgnoreCase(a: String, b: String?): Boolean {
        return a === b || b != null && a.length == b.length && a.regionMatches(0, b, 0, b.length, ignoreCase = true)
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    fun null2Length0(s: String?): String {
        return s ?: ""
    }


    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    fun upperFirstLetter(s: String): String? {
        return if (isEmpty(s) || !Character.isLowerCase(s[0])) s else (s[0].toInt() - 32).toChar().toString() + s.substring(
            1
        )
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    fun lowerFirstLetter(s: String): String? {
        return if (isEmpty(s) || !Character.isUpperCase(s[0])) s else (s[0].toInt() + 32).toChar().toString() + s.substring(
            1
        )
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    fun reverse(s: String): String {
        val len = length(s)
        if (len <= 1) return s
        val mid = len shr 1
        val chars = s.toCharArray()
        var c: Char
        for (i in 0 until mid) {
            c = chars[i]
            chars[i] = chars[len - i - 1]
            chars[len - i - 1] = c
        }
        return String(chars)
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    fun toDBC(s: String): String? {
        if (isEmpty(s)) return s
        val chars = s.toCharArray()
        var i = 0
        val len = chars.size
        while (i < len) {
            if (chars[i].toInt() == 12288) {
                chars[i] = ' '
            } else if (65281 <= chars[i].toInt() && chars[i].toInt() <= 65374) {
                chars[i] = (chars[i].toInt() - 65248).toChar()
            } else {
                chars[i] = chars[i]
            }
            i++
        }
        return String(chars)
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    fun toSBC(s: String): String? {
        if (isEmpty(s)) return s
        val chars = s.toCharArray()
        var i = 0
        val len = chars.size
        while (i < len) {
            if (chars[i] == ' ') {
                chars[i] = 12288.toChar()
            } else if (33 <= chars[i].toInt() && chars[i].toInt() <= 126) {
                chars[i] = (chars[i].toInt() + 65248).toChar()
            } else {
                chars[i] = chars[i]
            }
            i++
        }
        return String(chars)
    }

    /**
     * 计算图片要显示的高度
     *
     * @param pixel 原始分辨率
     * @param width 要显示的宽度
     * @return
     */
    fun calcPhotoHeight(pixel: String, width: Int): Int {
        var height = -1
        val index = pixel.indexOf("*")
        if (index != -1) {
            try {
                val widthPixel = Integer.parseInt(pixel.substring(0, index))
                val heightPixel = Integer.parseInt(pixel.substring(index + 1))
                height = (heightPixel * (width * 1.0f / widthPixel)).toInt()
            } catch (e: NumberFormatException) {
                return -1
            }

        }

        return height
    }

    /**
     * 裁剪新闻的 Source 数据
     *
     * @param source
     * @return
     */
    fun clipNewsSource(source: String): String {
        if (TextUtils.isEmpty(source)) {
            return source
        }
        val i = source.indexOf("-")
        return if (i != -1) {
            source.substring(0, i)
        } else source
    }

    /**
     * 裁剪图集ID
     *
     * @param photoId
     * @return
     */
    fun clipPhotoSetId(photoId: String): String {
        if (TextUtils.isEmpty(photoId)) {
            return photoId
        }
        val i = photoId.indexOf("|")
        if (i >= 4) {
            val result = photoId.replace('|', '/')
            return result.substring(i - 4)
        }
        return ""
    }

    /**
     * 转换文件路径
     *
     * @param filePath 原路径
     * @return
     */
    fun replaceFilePath(filePath: String): String {
        var filePath = filePath
        if (TextUtils.isEmpty(filePath)) {
            return filePath
        }
        if (filePath.startsWith("/sdcard") && SDCardUtils.rootPath != null) {
            filePath = filePath.replaceFirst("/sdcard".toRegex(), SDCardUtils.rootPath!!)
            filePath = filePath.replace(":", File.separator)
        }
        return filePath
    }

    /**
     * is null or its length is 0 or it is made by space
     *
     *
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
    </pre> *
     *
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    fun isBlank(str: String?): Boolean {
        return str == null || str.trim { it <= ' ' }.length == 0
    }

    /**
     * is null or its length is 0
     *
     *
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
    </pre> *
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    fun isEmpty(str: CharSequence?): Boolean {
        return str == null || str.length == 0
    }

    /**
     * 判断给定的字符串是否不为null且不为空
     *
     * @param string 给定的字符串
     */
    fun isNotEmpty(string: String): Boolean {
        return !isEmpty(string)
    }

    /**
     * get length of CharSequence
     *
     *
     * <pre>
     * length(null) = 0;
     * length(\"\") = 0;
     * length(\"abc\") = 3;
    </pre> *
     *
     * @param str
     * @return if str is null or empty, return 0, else return [CharSequence.length].
     */
    fun length(str: CharSequence?): Int {
        return str?.length ?: 0
    }

    /**
     * null Object to empty string
     *
     *
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
    </pre> *
     *
     * @param str
     * @return
     */
    fun nullStrToEmpty(str: Any?): String {
        return if (str == null) "" else str as? String ?: str.toString()
    }

    /**
     * capitalize first letter
     *
     *
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
    </pre> *
     *
     * @param str
     * @return
     */
    fun capitalizeFirstLetter(str: String): String? {
        if (isEmpty(str)) {
            return str
        }

        val c = str[0]
        return if (!Character.isLetter(c) || Character.isUpperCase(c))
            str
        else
            StringBuilder(str.length)
                .append(Character.toUpperCase(c)).append(str.substring(1)).toString()
    }

    /**
     * encoded in utf-8
     *
     *
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
    </pre> *
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException if an error occurs
     */
    fun utf8Encode(str: String): String? {
        if (!isEmpty(str) && str.toByteArray().size != str.length) {
            try {
                return URLEncoder.encode(str, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                throw RuntimeException("UnsupportedEncodingException occurred. ", e)
            }

        }
        return str
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     *
     * @param str
     * @param defultReturn
     * @return
     */
    fun utf8Encode(str: String, defultReturn: String): String? {
        if (!isEmpty(str) && str.toByteArray().size != str.length) {
            try {
                return URLEncoder.encode(str, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                return defultReturn
            }

        }
        return str
    }

    /**
     * get innerHtml from href
     *
     *
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
    </pre> *
     *
     * @param href
     * @return
     *  * if href is null, return ""
     *  * if not match regx, return source
     *  * return the last string that match regx
     *
     */
    fun getHrefInnerHtml(href: String): String {
        if (isEmpty(href)) {
            return ""
        }

        val hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*"
        val hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE)
        val hrefMatcher = hrefPattern.matcher(href)
        return if (hrefMatcher.matches()) {
            hrefMatcher.group(1)
        } else href
    }

    /**
     * process special char in html
     *
     *
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
    </pre> *
     *
     * @param source
     * @return
     */
    fun htmlEscapeCharsToString(source: String): String? {
        return if (StringUtils.isEmpty(source))
            source
        else
            source.replace("&lt;".toRegex(), "<").replace("&gt;".toRegex(), ">")
                .replace("&amp;".toRegex(), "&").replace("&quot;".toRegex(), "\"")
    }

    /**
     * transform half width char to full width char
     *
     *
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth("") = "";
     * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
    </pre> *
     *
     * @param s
     * @return
     */
    fun fullWidthToHalfWidth(s: String): String? {
        if (isEmpty(s)) {
            return s
        }

        val source = s.toCharArray()
        for (i in source.indices) {
            if (source[i].toInt() == 12288) {
                source[i] = ' '
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i].toInt() >= 65281 && source[i].toInt() <= 65374) {
                source[i] = (source[i].toInt() - 65248).toChar()
            } else {
                source[i] = source[i]
            }
        }
        return String(source)
    }

    /**
     * transform full width char to half width char
     *
     *
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth("") = "";
     * halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
    </pre> *
     *
     * @param s
     * @return
     */
    fun halfWidthToFullWidth(s: String): String? {
        if (isEmpty(s)) {
            return s
        }

        val source = s.toCharArray()
        for (i in source.indices) {
            if (source[i] == ' ') {
                source[i] = 12288.toChar()
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i].toInt() >= 33 && source[i].toInt() <= 126) {
                source[i] = (source[i].toInt() + 65248).toChar()
            } else {
                source[i] = source[i]
            }
        }
        return String(source)
    }

    /**
     * 格式化字符串
     *
     * @param msg  格式数据
     * @param args 参数
     * @return 格式化字符串
     */
    fun formatString(msg: String, vararg args: Any): String {
        return String.format(Locale.ENGLISH, msg, *args)
    }

    /**
     * 转换安装人数
     *
     * @param hotCount 安装人数
     * @return
     */
    fun convertHotCount(hotCount: Int): String {
        val tenThousand = 10000
        val hundredMillion = 10000 * 10000

        if (hotCount >= hundredMillion) {
            return String.format(Locale.CHINA, "%.1f亿人在用", hotCount.toFloat() / hundredMillion)
        } else if (hotCount >= tenThousand) {
            val f = hotCount.toFloat() / tenThousand
            return String.format(Locale.CHINA, if (f > 100) "%.0f万人在用" else "%.1f万人在用", f)
        } else {
            return String.format(Locale.CHINA, "%d人在用", hotCount)
        }
    }

    fun convertSpeed(size: Long): String {
        val kb: Long = 1024
        val mb = kb * 1024
        val gb = mb * 1024

        if (size >= gb) {
            return String.format("%.1f G", size.toFloat() / gb)
        } else if (size >= mb) {
            val f = size.toFloat() / mb
            return String.format(if (f > 100) "%.0f M" else "%.1f M", f)
        } else if (size >= kb) {
            val f = size.toFloat() / kb
            return String.format(if (f > 100) "%.0f K" else "%.1f K", f)
        } else
            return String.format("%d B", size)
    }

    fun convertStorageNoB(size: Long): String {
        val kb: Long = 1024
        val mb = kb * 1024
        val gb = mb * 1024

        if (size >= gb) {
            return String.format("%.1fGB", size.toFloat() / gb)
        } else if (size >= mb) {
            val f = size.toFloat() / mb
            return String.format(if (f > 100) "%.0fMB" else "%.1fMB", f)
        } else if (size >= kb) {
            val f = size.toFloat() / kb
            return String.format(if (f > 100) "%.0fKB" else "%.1fKB", f)
        } else
            return String.format("%dB", size)
    }

    /**
     * 从url中截取文件名
     * @param url
     * @return
     */
    fun clipFileName(url: String): String? {
        val index = url.lastIndexOf("/")
        return if (index != -1) {
            url.substring(index + 1)
        } else null
    }
}
