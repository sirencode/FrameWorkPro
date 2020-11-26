package com.syh.framework.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

/**
 * 作者：created by Bamboo on 2019-10-15
 * 邮箱：bzy601638015@126.com
 */
object StringUtilsKt {
    fun getSaleType(saleType: Int) = when (saleType) {
        0 -> "SS级"
        1 -> "S级"
        2 -> "A级"
        3 -> "B级"
        else -> "暂无"
    }

    fun formatDouble2(d: Float): String {
        val df = DecimalFormat("0.00")
        return df.format(d)
    }

    fun formatDouble2(s: String): String {
        return formatDouble2(s.toFloat())
    }

    fun dayMillisToString(t: Long): String {
        var millis = t
        val negative = millis < 0
        millis = Math.abs(millis)

        millis /= 1000
        val sec = (millis % 60).toInt()
        millis /= 60
        val min = (millis % 60).toInt()
        millis /= 60
        val hours = (millis % 24).toInt()
        millis /= 24
        val day = millis.toInt()

        val time: String
        val format = NumberFormat.getInstance(Locale.US) as DecimalFormat
        format.applyPattern("00")
        if (millis > 0)
            time =
                (if (negative) "-" else "") + day + "天" + format.format(hours.toLong()) + ":" + format.format(
                    min.toLong()
                ) + ":" + format.format(sec.toLong())
        else
            time =
                (if (negative) "-" else "") + format.format(hours.toLong()) + ":" + format.format(
                    min.toLong()
                ) + ":" + format.format(sec.toLong())
        return time
    }

    fun millisToString(t: Long, text: Boolean): String {
        var millis = t
        val negative = millis < 0
        millis = Math.abs(millis)

        millis /= 1000
        val sec = (millis % 60).toInt()
        millis /= 60
        val min = (millis % 60).toInt()
        millis /= 60
        val hours = millis.toInt()

        val time: String
        val format = NumberFormat.getInstance(Locale.US) as DecimalFormat
        format.applyPattern("00")
        if (text) {
            if (millis > 0)
                time =
                    (if (negative) "-" else "") + hours + "h" + format.format(min.toLong()) + "min"
            else if (min > 0)
                time = (if (negative) "-" else "") + min + "min"
            else
                time = (if (negative) "-" else "") + sec + "s"
        } else {
            if (millis > 0)
                time =
                    (if (negative) "-" else "") + hours + ":" + format.format(min.toLong()) + ":" + format.format(
                        sec.toLong()
                    )
            else
                time = (if (negative) "-" else "") + min + ":" + format.format(sec.toLong())
        }
        return time
    }

    fun build3NumColor(str: String): String {
        var result = str
        if (str.length == 4) {
            result = StringBuilder("#").append(str[1]).append(str[1])
                .append(str[2]).append(str[2])
                .append(str[3]).append(str[3]).toString()
        }
        return result
    }
}