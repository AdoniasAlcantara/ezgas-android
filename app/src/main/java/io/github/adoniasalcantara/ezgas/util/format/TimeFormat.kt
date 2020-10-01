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
            seconds < 60 -> getString(R.string.timeNow)

            // Minutes (less than 1 hour)
            seconds < 3600 -> {
                val minutes = diff.toMinutes().toInt()
                getQuantityString(R.plurals.timeMinutes, minutes, minutes)
            }

            // Hours (less than 1 day)
            seconds < 86400 -> {
                val hours = diff.toHours().toInt()
                getQuantityString(R.plurals.timeHours, hours, hours)
            }

            // Days (less than 15 days)
            seconds < 1296000 -> {
                val days = diff.toDays().toInt()
                getQuantityString(R.plurals.timeDays, days, days)
            }

            // Infinity (15 days or more)
            else -> getQuantityString(R.plurals.timeDays, 15, 15)
        }
    }
}