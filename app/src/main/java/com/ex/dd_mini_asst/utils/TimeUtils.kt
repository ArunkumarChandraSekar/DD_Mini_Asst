package com.ex.dd_mini_asst.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

object TimeUtils {
    fun dateConvertTime(dateStr : String): String?{
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm+SS'Z'");
        return try {
            //Converting the input String to Date
            val tempDate = df.parse(dateStr);
            val timeSd = SimpleDateFormat("HH:mm a")
            "at ${timeSd.format(tempDate)}"
        } catch (pe: ParseException) {
            pe.printStackTrace()
            null
        }
    }

    fun remainingTime(dateStr : String): Long?{
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm+SS'Z'");
        return try {
            //Converting the input String to Date
            val tempDate = df.parse(dateStr);
            val cur = System.currentTimeMillis()
            if (cur< tempDate.time){
                tempDate.time - cur
            }else{
                null
            }

        } catch (pe: ParseException) {
            pe.printStackTrace()
            null
        }
    }
}