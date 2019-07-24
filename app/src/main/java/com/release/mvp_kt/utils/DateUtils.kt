package com.release.mvp_kt.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import com.orhanobut.logger.Logger

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object DateUtils {


    val DATE_DEFAULT_FORMAT = "yyyy-MM-dd"
    // 默认时间格式
    val DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"
    val TIME_DEFAULT_FORMAT = "HH:mm:ss"

    private var dateTimeFormat: DateFormat? = null
    private var gregorianCalendar: Calendar? = null
    private var dateFormat: DateFormat? = null
    private var timeFormat: DateFormat? = null

    /**
     * 格式化取当前时间
     *
     * @return
     */
    val thisDateTime: String
        get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */

    val currentDate: String
        get() {
            val currentTime = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return formatter.format(currentTime)
        }


    /**
     * 格式化取当前时间
     *
     * @return
     */
    val cuttentDateTime: String
        get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(System.currentTimeMillis())

    /**
     * 获取当前日期(yyyy-MM-dd)
     *
     * @param
     * @return
     */
    val nowDate: Date?
        get() = DateUtils.getDateFormat(dateFormat!!.format(Date()))

    /**
     * 获取当前月的第一天
     *
     * @return date
     */
    val firstDayOfMonth: Date
        get() {
            gregorianCalendar!!.time = Date()
            gregorianCalendar!!.set(Calendar.DAY_OF_MONTH, 1)
            return gregorianCalendar!!.time
        }

    /**
     * 获取当前月的最后一天
     *
     * @return
     */
    val lastDayOfMonth: Date
        get() {
            gregorianCalendar!!.time = Date()
            gregorianCalendar!!.set(Calendar.DAY_OF_MONTH, 1)
            gregorianCalendar!!.add(Calendar.MONTH, 1)
            gregorianCalendar!!.add(Calendar.DAY_OF_MONTH, -1)
            return gregorianCalendar!!.time
        }

    /**
     * 获取当前年
     *
     * @return
     */
    val nowYear: Int
        get() {
            val d = Calendar.getInstance()
            return d.get(Calendar.YEAR)
        }

    /**
     * 获取当前月份
     *
     * @return
     */
    val nowMonth: Int
        get() {
            val d = Calendar.getInstance()
            return d.get(Calendar.MONTH) + 1
        }

    /**
     * 获取当月天数
     *
     * @return
     */
    val nowMonthDay: Int
        get() {
            val d = Calendar.getInstance()
            return d.getActualMaximum(Calendar.DATE)
        }

    init {
        dateFormat = SimpleDateFormat(DATE_DEFAULT_FORMAT)
        dateTimeFormat = SimpleDateFormat(DATETIME_DEFAULT_FORMAT)
        timeFormat = SimpleDateFormat(TIME_DEFAULT_FORMAT)
        gregorianCalendar = GregorianCalendar()
    }

    /**
     * 服务端给的时间，经常会以.0结尾，所以去除之
     *
     * @param datetime
     * @return
     */
    fun RemoveLastZero(datetime: String): String {
        if (TextUtils.isEmpty(datetime))
            return ""

        return if (datetime.length > 19)
            datetime.substring(0, 19)
        else
            datetime
    }

    /**
     * 将传入时间添加秒钟数
     *
     * @param date 时间
     * @param sec  秒数，正数为添加秒，负数是减少秒
     * @return
     */
    fun addSec(date: String, sec: Int): String {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val reminTime = sdf.parse(date).time + 1000 * sec
            return sdf.format(reminTime)
        } catch (e: Exception) {
            return ""
        }

    }

    fun parseTime(date: String): String {
        val c = Calendar.getInstance()
        c.timeInMillis = java.lang.Long.parseLong(date)
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        return sdf.format(c.time)

    }


    /**
     * 获取时间差 格式：5天5小时5分前
     *
     * @param time2
     * @return
     */
    fun getDateDifference(time2: String): String {

        var days: Long = 0
        var hours: Long = 0
        var minutes: Long = 0
        var seconds: Long = 0
        val currentTime = Date()
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time1 = df.format(currentTime)
        try {
            val d1 = df.parse(time1)
            val d2 = df.parse(time2)
            val diff = d1.time - d2.time

            days = diff / (1000 * 60 * 60 * 24)
            hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60)
            minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60)
            seconds = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return if (days == 0L && hours != 0L)
            hours.toString() + "小时" + minutes + "分" + seconds + "秒前"
        else if (days == 0L && hours == 0L && minutes != 0L)
            minutes.toString() + "分" + seconds + "秒前"
        else if (days == 0L && hours == 0L && minutes == 0L)
            seconds.toString() + "秒前"
        else
            days.toString() + "天" + hours + "小时" + minutes + "分" + seconds + "秒前"
    }

    /**
     * 时差秒
     *
     * @param time2
     * @return
     */
    fun getDateDifferenceSeconds(time2: String): Long {

        val days: Long = 0
        val hours: Long = 0
        val minutes: Long = 0
        var seconds: Long = 0
        val currentTime = Date()
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val time1 = df.format(currentTime)
        try {
            val d1 = df.parse(time1)
            val d2 = df.parse(time2)
            val diff = d1.time - d2.time

            seconds = diff / 1000

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return seconds
    }

    /**
     * 获取时间差 格式：6秒
     *
     * @param eTime
     * @param sTime
     * @return
     */
    fun getDateDifferenceSecond(eTime: String, sTime: String): Long {

        var seconds: Long = 0

        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")
        try {
            if (TextUtils.isEmpty(eTime) || TextUtils.isEmpty(sTime)) return 0
            val d1 = df.parse(eTime)
            val d2 = df.parse(sTime)
            val diff = d1.time - d2.time
            seconds = diff / 1000

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return seconds
    }

    /**
     * 获取时间差 格式：6天
     *
     * @param eTime
     * @param sTime
     * @return
     */
    fun getDateDifference(eTime: String, sTime: String): Long {

        var days: Long = 0

        val currentTime = Date()
        val df = SimpleDateFormat("yyyy-MM-dd")
        try {
            val d1 = df.parse(eTime)
            val current = df.format(currentTime)
            val d2 = df.parse(current)
            val diff = d1.time - d2.time

            days = diff / (1000 * 60 * 60 * 24)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return days
    }

    /**
     * 获取时间差1分
     *
     * @param eTime
     * @param sTime
     * @return
     */
    fun isCloseEnough(eTime: Long, sTime: Long): Boolean {

        if (eTime == 0L || sTime == 0L) return true
        val diff = eTime - sTime
        val seconds = (diff / (1000 * 60)).toDouble()

        return if (seconds >= 1f)
            false
        else
            true
    }

    /**
     * 将毫秒转为yyyy-MM-dd HH:mm:ss的时间
     *
     * @param time
     * @return
     */
    fun getStringDate(time: Long): String {
        val date = Date()
        date.time = time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return formatter.format(date)
    }


    /**
     * 将毫秒转为yyyy-MM-dd HH:mm的时间
     *
     * @param time
     * @return
     */
    fun getStringDate2(time: Long): String {
        val date = Date()
        date.time = time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return formatter.format(date)
    }

    /**
     * 将毫秒转为mm:ss的时间
     *
     * @param time
     * @return
     */
    fun getCountDownDate(time: Long): String {
        val date = Date()
        date.time = time
        val formatter = SimpleDateFormat("mm:ss")
        return formatter.format(date)
    }

    /**
     * 将毫秒转为yyyy-MM-dd的时间
     *
     * @param time
     * @return
     */
    fun getSimpleDate(time: Long): String {
        val date = Date()
        date.time = time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(date)
    }

    fun getSimpleDateMillion(time: String): Long {
        try {
            return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).time
        } catch (e: ParseException) {
            e.printStackTrace()
            return 0
        }

    }


    /**
     * 判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return date2-date1 天
     */
    fun days(date1: String?, date2: String?): Int {

        if (date1 == null || date2 == null) {
            return 0
        }
        val d1 = parseDate(date1, "yyyy-MM-dd")
        val d2 = parseDate(date2, "yyyy-MM-dd")
        val days = ((d2 - d1) / (1000 * 3600 * 24)).toInt()

        return Math.abs(days)
    }

    fun parseDate(date: String, mate: String): Long {
        val format = SimpleDateFormat(mate)
        try {
            return format.parse(date).time
        } catch (e: ParseException) {
            throw RuntimeException(e.message)
        }

    }

    /**
     * 判断两个时间的间隔
     *
     * @param befdate
     * @param aftdate
     * @return date2-date1 天
     */

    fun Months(befdate: String?, aftdate: String?): Int {
        if (befdate == null || aftdate == null) {
            return 0
        }
        val sdf = SimpleDateFormat("yyyy-MM")
        val bef = Calendar.getInstance()
        val aft = Calendar.getInstance()
        try {
            bef.time = sdf.parse(befdate)
            aft.time = sdf.parse(aftdate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH)
        val month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12
        return Math.abs(month + result)
    }


    //-------------------------------------------------------------------------------------------------

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的时间，与当前时间相比，时间差转换为口头上的术语，如几天几小时几分几�?
     *
     * @return
     */
    fun convert_between(datetime: String): String {
        try {
            val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime).time
            return convert_between(((time - System.currentTimeMillis()) / 1000).toInt().toLong())
        } catch (e: ParseException) {
            e.printStackTrace()
            return "未知"
        }

    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的时间，两个时间相比，时间差转换为口头上的术语，如几天几小时几分几秒
     *
     * @return
     */
    fun convert_between(starttime: String, endtime: String): String {
        try {
            val ttime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(starttime).time
            val etime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endtime).time
            return convert_between(((etime - ttime) / 1000).toInt().toLong())
        } catch (e: ParseException) {
            e.printStackTrace()
            return "未知"
        }

    }

    /**
     * 将时长秒，转换为口头上的术语，如几天几小时几分几�? 1天：86400s 1时：3600s 1分：60s
     *
     * @param sec 相差的间隔，单位为秒
     * @return
     */
    fun convert_between(sec: Long): String {
        if (sec < 0)
            return "时间超了"
        val buf = StringBuffer()
        if (sec >= 86400) {
            val day = (sec / 86400).toInt()
            val hour = (sec % 86400 / 3600).toInt()
            val min = (sec % 86400 % 3600 / 60).toInt()
            val second = (sec % 86400 % 3600 % 60).toInt()
            buf.append(day).append("�?").append(hour).append("小时").append(min).append("�?").append(second).append("�?")
        } else if (sec > 3600) {
            val hour = (sec / 3600).toInt()
            val min = (sec % 3600 / 60).toInt()
            val second = (sec % 3600 % 60).toInt()
            buf.append(hour).append("小时").append(min).append("�?").append(second).append("�?")
        } else if (sec > 60) {
            val min = (sec / 60).toInt()
            val second = (sec % 60).toInt()
            buf.append(min).append("�?").append(second).append("�?")
        } else {
            buf.append(sec).append("�?")
        }

        return buf.toString()
    }

    /**
     * 将时长秒，转换为几分几秒，�?�用于�?�话时长之类的，�?2'30''
     *
     * @param sec
     * @return
     */
    fun convert_between_len(sec: Long): String {
        if (sec < 0)
            return sec.toString()

        val buf = StringBuffer()
        if (sec > 60) {
            val min = (sec / 60).toInt()
            val second = (sec % 60).toInt()
            buf.append(min).append("'").append(second).append("''")
        } else {
            buf.append(sec).append("''")
        }

        return buf.toString()
    }

    //-------------------------------------------------------------------------------------------------

    /**
     * 将EEE MMM dd HH:mm:ss zzz yyyy格式的时间，同当前时间相比，格式化为：xx分钟前，xx小时前和日期
     *
     * @param datetime
     * @return
     */
    fun convert_before_timezone(datetime: String): String {
        Logger.v("info", datetime)
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH)
        dateFormat.isLenient = false
        var created: Date? = null
        try {
            created = dateFormat.parse(datetime)
        } catch (e: Exception) {
            return ""
        }

        return convert_before(created!!.time)
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的时间，同当前时间比对，格式化为：xx分钟前，xx小时前和日期
     *
     * @param datetime �?比对的时�?
     * @return
     */
    fun convert_before(datetime: String): String {
        if (TextUtils.isEmpty(datetime)) {
            return ""
        }

        try {
            val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime).time
            return convert_before(time)
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }

    }

    /**
     * 将对比后的时间，格式化为：xx分钟前，xx小时前和日期
     *
     * @param time �?比对的时�?
     * @return
     */
    fun convert_before(time: Long): String {
        if (time < 0)
            return time.toString()

        val difftime = ((System.currentTimeMillis() - time) / 1000).toInt()
        if (difftime < 86400 && difftime > 0) {
            if (difftime < 3600) {
                val min = difftime / 60
                return if (min == 0)
                    "刚刚"
                else
                    (difftime / 60).toString() + "分钟�?"
            } else {
                return (difftime / 3600).toString() + "小时�?"
            }
        } else {
            val now = Calendar.getInstance()
            val c = Calendar.getInstance()
            c.timeInMillis = time
            if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                && c.get(Calendar.DATE) == now.get(Calendar.DATE)
            ) {
                return SimpleDateFormat("HH:mm").format(c.time)
            }
            return if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                && c.get(Calendar.DATE) == now.get(Calendar.DATE) - 1
            ) {
                SimpleDateFormat("昨天 HH:mm").format(c.time)
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                && c.get(Calendar.DATE) == now.get(Calendar.DATE) - 2
            ) {
                SimpleDateFormat("前天 HH:mm").format(c.time)
            } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                SimpleDateFormat("M月d�? HH:mm").format(c.time)
            } else {
                SimpleDateFormat("yy年M月d�?").format(c.time)
            }
        }
    }

    /**
     * 指定的时间，在时间条件范围内的，返回true，不在该时间范围内，返回false
     *
     * @param sDate     �?始日期，yyyy-MM-dd hh:mm:ss
     * @param eDate     结束时间，yyyy-MM-dd hh:mm:ss
     * @param checkTime �?查时间，yyyy-MM-dd hh:mm:ss
     * @return
     */
    fun timeCompare(sDate: String, eDate: String, checkTime: String): Boolean {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val sTime = sdf.parse(sDate).time
            val eTime = sdf.parse(eDate).time
            val sec = sdf.parse(checkTime).time
            return if (sec > sTime && sec < eTime)
                true
            else
                false
        } catch (e: Exception) {
            return false
        }

    }

    /**
     * 当前时间，在时间条件范围内的，返回true，不在该时间范围内，返回false
     *
     * @param sDate �?始日期，hh:mm
     * @param eDate 结束时间，hh:mm
     * @return
     */
    fun timeCompa(sDate: String, eDate: String): Boolean {
        try {
            val sec = System.currentTimeMillis()
            val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm")
            val df = SimpleDateFormat("yyyy-MM-dd")
            val sTime = sdf.parse(df.format(sec) + " " + sDate).time
            val eTime = sdf.parse(df.format(sec) + " " + eDate).time
            return if (sec > sTime && sec < eTime)
                true
            else
                false
        } catch (e: Exception) {
            return false
        }

    }

    /**
     * 判断两个时间的大�?
     *
     * @param sDate �?始日期，yyyy-MM-dd hh:mm:ss
     * @param eDate 结束时间，yyyy-MM-dd hh:mm:ss
     * @return
     */
    fun timeCompare(sDate: String, eDate: String): Boolean {
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val sTime = sdf.parse(sDate).time
            val eTime = sdf.parse(eDate).time
            return if (sTime > eTime)
                true
            else
                false
        } catch (e: Exception) {
            return false
        }

    }

    fun formatDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
        return sdf.format(Date())
    }

    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     * @return
     */
    fun formatDate(date: String, format: String): Date? {
        try {
            return SimpleDateFormat(format).parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 日期格式化yyyy-MM-dd
     *
     * @param date
     * @return
     */
    fun getDateFormat(date: Date): String {
        return dateFormat!!.format(date)
    }

    /**
     * 日期格式化yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    fun getDateTimeFormat(date: Date): String {
        return dateTimeFormat!!.format(date)
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return HH:mm:ss
     */
    fun getTimeFormat(date: Date): String {
        return timeFormat!!.format(date)
    }

    /**
     * 日期格式化
     *
     * @param date
     * @param formatStr 格式类型
     * @return
     */
    fun getDateFormat(date: Date, formatStr: String): String? {
        return if (!TextUtils.isEmpty(formatStr)) {
            SimpleDateFormat(formatStr).format(date)
        } else null
    }

    /**
     * 日期格式化
     *
     * @param date
     * @return
     */
    fun getDateFormat(date: String): Date? {
        try {
            return dateFormat!!.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 时间格式化
     *
     * @param date
     * @return
     */
    fun getDateTimeFormat(date: String): Date? {
        try {
            return dateTimeFormat!!.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 获取指定月的第一天
     *
     * @param date
     * @return
     */
    fun getFirstDayOfMonth(date: Date): Date {
        gregorianCalendar!!.time = date
        gregorianCalendar!!.set(Calendar.DAY_OF_MONTH, 1)
        return gregorianCalendar!!.time
    }

    /**
     * 获取指定月的最后一天
     *
     * @param date
     * @return
     */
    fun getLastDayOfMonth(date: Date): Date {
        gregorianCalendar!!.time = date
        gregorianCalendar!!.set(Calendar.DAY_OF_MONTH, 1)
        gregorianCalendar!!.add(Calendar.MONTH, 1)
        gregorianCalendar!!.add(Calendar.DAY_OF_MONTH, -1)
        return gregorianCalendar!!.time
    }

    /**
     * 获取日期前一天
     *
     * @param date
     * @return
     */
    fun getDayBefore(date: Date): Date {
        gregorianCalendar!!.time = date
        val day = gregorianCalendar!!.get(Calendar.DATE)
        gregorianCalendar!!.set(Calendar.DATE, day - 1)
        return gregorianCalendar!!.time
    }

    /**
     * 获取日期后一天
     *
     * @param date
     * @return
     */
    fun getDayAfter(date: Date): Date {
        gregorianCalendar!!.time = date
        val day = gregorianCalendar!!.get(Calendar.DATE)
        gregorianCalendar!!.set(Calendar.DATE, day + 1)
        return gregorianCalendar!!.time
    }

    /**
     * 获取时间段的每一天
     *
     * @param startDate 开始日期
     * @param endDate 结算日期
     * @return 日期列表
     */
    fun getEveryDay(startDate: Date?, endDate: Date?): List<Date>? {
        var startDate = startDate
        var endDate = endDate
        if (startDate == null || endDate == null) {
            return null
        }
        // 格式化日期(yy-MM-dd)
        startDate = DateUtils.getDateFormat(DateUtils.getDateFormat(startDate))
        endDate = DateUtils.getDateFormat(DateUtils.getDateFormat(endDate))
        val dates = ArrayList<Date>()
        gregorianCalendar!!.time = startDate
        dates.add(gregorianCalendar!!.time)
        while (gregorianCalendar!!.time.compareTo(endDate) < 0) {
            // 加1天
            gregorianCalendar!!.add(Calendar.DAY_OF_MONTH, 1)
            dates.add(gregorianCalendar!!.time)
        }
        return dates
    }

    /**
     * 获取提前多少个月
     *
     * @param monty
     * @return
     */
    fun getFirstMonth(monty: Int): Date {
        val c = Calendar.getInstance()
        c.add(Calendar.MONTH, -monty)
        return c.time
    }

    //根据日期获得周几
    fun getWeek(pTime: String): String {
        var Week = ""
        val format = SimpleDateFormat("yyyy-MM-dd")
        val c = Calendar.getInstance()
        try {
            c.time = format.parse(pTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五"
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六"
        }
        return "星期$Week"
    }
}
