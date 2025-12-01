package com.brainstorm.unopad.ui

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DAY_MONTH_YEAR = "dd MMMM yyyy"

fun Long.toReadableTime(): String {
    val format = SimpleDateFormat(DAY_MONTH_YEAR, Locale.getDefault())
    return format.format(Date(this))
}

@Suppress("NOTHING_TO_INLINE")
inline fun Long.toRelativeTime(): CharSequence {
    return DateUtils.getRelativeTimeSpanString(this)
}

@Suppress("NOTHING_TO_INLINE")
inline fun currentRelativeTime() = System.currentTimeMillis().toRelativeTime()

@Suppress("NOTHING_TO_INLINE")
inline fun currentReadableTime() = System.currentTimeMillis().toReadableTime()
