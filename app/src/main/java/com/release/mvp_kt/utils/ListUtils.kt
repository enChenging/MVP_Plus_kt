package com.release.mvp_kt.utils

import android.text.TextUtils
import java.util.*

/**
 * List Utils
 *
 * @author [Trinea](http://www.trinea.cn) 2011-7-22
 */
object ListUtils {


    /** default join separator  */
    const val DEFAULT_JOIN_SEPARATOR = ","

    /**
     * get size of list
     *
     * <pre>
     * getSize(null)   =   0;
     * getSize({})     =   0;
     * getSize({1})    =   1;
    </pre> *
     *
     * @param <V>
     * @param sourceList
     * @return if list is null or empty, return 0, else return [List.size].
    </V> */
    fun <V> getSize(sourceList: List<V>?): Int {
        return sourceList?.size ?: 0
    }

    /**
     * is null or its size is 0
     *
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1})    =   false;
    </pre> *
     *
     * @param <V>
     * @param sourceList
     * @return if list is null or its size is 0, return true, else return false.
    </V> */
    fun <V> isEmpty(sourceList: List<V>?): Boolean {
        return sourceList == null || sourceList.size == 0
    }

    /**
     * join list to string
     *
     * <pre>
     * join(null, '#')     =   "";
     * join({}, '#')       =   "";
     * join({a,b,c}, ' ')  =   "abc";
     * join({a,b,c}, '#')  =   "a#b#c";
    </pre> *
     *
     * @param list
     * @param separator
     * @return join list to string. if list is empty, return ""
     */
    fun join(list: List<String>, separator: Char): String {
        return join(list, String(charArrayOf(separator)))
    }

    /**
     * join list to string. if separator is null, use [.DEFAULT_JOIN_SEPARATOR]
     *
     * <pre>
     * join(null, "#")     =   "";
     * join({}, "#$")      =   "";
     * join({a,b,c}, null) =   "a,b,c";
     * join({a,b,c}, "")   =   "abc";
     * join({a,b,c}, "#")  =   "a#b#c";
     * join({a,b,c}, "#$") =   "a#$b#$c";
    </pre> *
     *
     * @param list
     * @param separator
     * @return join list to string with separator. if list is empty, return ""
     */
    @JvmOverloads
    fun join(list: List<String>?, separator: String = DEFAULT_JOIN_SEPARATOR): String {
        return if (list == null) "" else TextUtils.join(separator, list)
    }

    /**
     * add distinct entry to list
     *
     * @param <V>
     * @param sourceList
     * @param entry
     * @return if entry already exist in sourceList, return false, else add it and return true.
    </V> */
    fun <V> addDistinctEntry(sourceList: MutableList<V>?, entry: V): Boolean {
        return if (sourceList != null && !sourceList.contains(entry)) sourceList.add(entry) else false
    }

    /**
     * add all distinct entry to list1 from list2
     *
     * @param <V>
     * @param sourceList
     * @param entryList
     * @return the count of entries be added
    </V> */
    fun <V> addDistinctList(sourceList: MutableList<V>?, entryList: List<V>): Int {
        if (sourceList == null || isEmpty(entryList)) {
            return 0
        }

        val sourceCount = sourceList.size
        for (entry in entryList) {
            if (!sourceList.contains(entry)) {
                sourceList.add(entry)
            }
        }
        return sourceList.size - sourceCount
    }

    /**
     * remove duplicate entries in list
     *
     * @param <V>
     * @param sourceList
     * @return the count of entries be removed
    </V> */
    fun <V> distinctList(sourceList: MutableList<V>): Int {
        if (isEmpty(sourceList)) {
            return 0
        }

        val sourceCount = sourceList.size
        var sourceListSize = sourceList.size
        for (i in 0 until sourceListSize) {
            var j = i + 1
            while (j < sourceListSize) {
                if (sourceList[i] == sourceList[j]) {
                    sourceList.removeAt(j)
                    sourceListSize = sourceList.size
                    j--
                }
                j++
            }
        }
        return sourceCount - sourceList.size
    }

    /**
     * add not null entry to list
     *
     * @param sourceList
     * @param value
     * @return
     *  * if sourceList is null, return false
     *  * if value is null, return false
     *  * return [List.add]
     *
     */
    fun <V> addListNotNullValue(sourceList: MutableList<V>?, value: V?): Boolean {
        return if (sourceList != null && value != null) sourceList.add(value) else false
    }

    /**
     * invert list
     *
     * @param <V>
     * @param sourceList
     * @return
    </V> */
    fun <V> invertList(sourceList: List<V>): List<V>? {
        if (isEmpty(sourceList)) {
            return sourceList
        }

        val invertList = ArrayList<V>(sourceList.size)
        for (i in sourceList.indices.reversed()) {
            invertList.add(sourceList[i])
        }
        return invertList
    }
}
