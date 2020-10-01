package io.github.adoniasalcantara.ezgas.util.format

import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan
import androidx.core.text.set
import java.math.BigDecimal
import java.text.NumberFormat

private val currencySizeSpan = RelativeSizeSpan(0.5f)
private val currencySuperscriptSpan = SuperscriptSpan()
private val currencyFormat = NumberFormat.getNumberInstance().apply {
    maximumFractionDigits = 2
    minimumFractionDigits = 2
}

fun BigDecimal.formatToBRL(): CharSequence = "R$" + currencyFormat.format(this)

fun BigDecimal.formatToBRLSuperscript(): CharSequence {
    val brl = this.formatToBRL()
    val range = 0..2

    return SpannableString(brl).also {
        it[range] = currencySizeSpan
        it[range] = currencySuperscriptSpan
    }
}