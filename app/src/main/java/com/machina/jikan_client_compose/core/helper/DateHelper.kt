package com.machina.jikan_client_compose.core.helper

import android.icu.util.Calendar

object DateHelper {

	fun parseDayIntToDayString(dayInInt: Int): String {
		return when (dayInInt) {
			Calendar.MONDAY -> "monday"
			Calendar.TUESDAY -> "tuesday"
			Calendar.WEDNESDAY -> "wednesday"
			Calendar.THURSDAY -> "thursday"
			Calendar.FRIDAY -> "friday"
			Calendar.SATURDAY -> "saturday"
			Calendar.SUNDAY -> "sunday"
			else -> "other"
		}
	}

	fun getTodayDayNameInString(): String {
		return parseDayIntToDayString(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
	}
}