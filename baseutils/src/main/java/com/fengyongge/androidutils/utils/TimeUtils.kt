package com.fengyongge.androidutils.utils

import java.text.SimpleDateFormat
import java.util.*
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class TimeUtils {

    companion object {

        fun formatDateLongToString(
            timeLongVal: Long?,
            descFormat: String?
        ): String? {
            val date = Date(timeLongVal!!)
            val df = SimpleDateFormat(descFormat)
            return df.format(date)
        }


        /**
         * 时间戳转成小时和分钟
         */
        fun convertMinAndSec(time: Int): String? {
            var timeStr: String? = null
            var hour = 0
            var minute = 0
            var second = 0
            if (time <= 0) {
                return "0"
            } else {
                minute = time / 60
                if (minute < 60) {
                    second = time % 60
                    timeStr =
                        unitFormat(
                            minute
                        ) + ":" + unitFormat(
                            second
                        )
                } else {
                    hour = minute / 60
                    minute %= 60
                    second = time - hour * 3600 - minute * 60
                    timeStr =
                        unitFormat(
                            hour
                        ) + ":" + unitFormat(
                            minute
                        ) + ":" + unitFormat(
                            second
                        )
                }
            }
            return timeStr
        }


        fun unitFormat(i: Int): String? {
            var retStr: String? = null
            retStr = if (i in 0..9) "0$i" else "" + i
            return retStr
        }

    }

}