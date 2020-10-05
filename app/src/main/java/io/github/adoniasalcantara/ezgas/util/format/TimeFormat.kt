package io.github.adoniasalcantara.ezgas.util.format

import android.content.Context
import io.github.adoniasalcantara.ezgas.R
import java.time.Duration
import java.time.OffsetDateTime

fun OffsetDateTime.formatToRelativeTimeFromNow(context: Context): String {
    val diff = Duration.between(this, OffsetDateTime.now())
    val seconds = diff.seconds

    return context.resources.run {
        when {
            // Just now (less than 1 minute)
            seconds < 60 -> getString(R.string.time_now)

            // Minutes (less than 1 hour)
            seconds < 3600 -> {
                val minutes = diff.toMinutes().toInt()
                getQuantityString(R.plurals.time_minutes, minutes, minutes)
            }

            // Hours (less than 1 day)
            seconds < 86400 -> {
                val hours = diff.toHours().toInt()
                getQuantityString(R.plurals.time_hours, hours, hours)
            }

            // Days (less than 15 days)
            seconds < 1296000 -> {
                val days = diff.toDays().toInt()
                getQuantityString(R.plurals.time_days, days, days)
            }

            // Infinity (15 days or more)
            else -> getString(R.string.time_infinity, 15)
        }
    }
}