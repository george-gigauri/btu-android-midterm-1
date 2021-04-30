package ge.georgegigauri.midtermexam

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import ge.georgegigauri.midtermexam.Consts.Preferences.CARD_CVV_CODE
import ge.georgegigauri.midtermexam.Consts.Preferences.CARD_EXPIRATION_MONTH
import ge.georgegigauri.midtermexam.Consts.Preferences.CARD_EXPIRATION_YEAR
import ge.georgegigauri.midtermexam.Consts.Preferences.CARD_HOLDER
import ge.georgegigauri.midtermexam.Consts.Preferences.CARD_NUMBER
import ge.georgegigauri.midtermexam.Consts.Preferences.CARD_TYPE
import ge.georgegigauri.midtermexam.Consts.Preferences.PREFERENCES_NAME

fun Activity.saveCard(
    card: Card
) {
    val sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    editor.putString(CARD_NUMBER, card.cardNumber)
    editor.putString(CARD_TYPE, card.type)
    editor.putString(CARD_HOLDER, card.cardHolder)
    editor.putInt(CARD_EXPIRATION_MONTH, card.expirationMonth)
    editor.putInt(CARD_EXPIRATION_YEAR, card.expirationYear)
    editor.putInt(CARD_CVV_CODE, card.cvv)

    editor.apply()
}

fun Activity.getCard(): Card {
    val sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
    val number: String = sharedPreferences.getString(CARD_NUMBER, "") ?: ""
    val cardHolder: String = sharedPreferences.getString(CARD_HOLDER, "") ?: ""
    val type: String = sharedPreferences.getString(CARD_TYPE, "") ?: ""
    val expirationMonth: Int = sharedPreferences.getInt(CARD_EXPIRATION_MONTH, 0)
    val expirationYear: Int = sharedPreferences.getInt(CARD_EXPIRATION_YEAR, 0)
    val cvv: Int = sharedPreferences.getInt(CARD_CVV_CODE, 0)
    return Card(
        number,
        cardHolder,
        CardType.valueOf(type),
        expirationMonth,
        expirationYear,
        cvv
    )
}

fun Activity.cardExists(): Boolean {
    val sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE)
    val number: String = sharedPreferences.getString(CARD_NUMBER, "") ?: ""
    val type: String = sharedPreferences.getString(CARD_TYPE, "") ?: ""
    val expirationMonth: Int = sharedPreferences.getInt(CARD_EXPIRATION_MONTH, 0)
    val expirationYear: Int = sharedPreferences.getInt(CARD_EXPIRATION_YEAR, 0)
    val cvv: Int = sharedPreferences.getInt(CARD_CVV_CODE, 0)

    return number != "" && type != "" && expirationMonth != 0 && expirationYear != 0 && cvv != 0
}

fun formatCardNumber(cardNumber: String): String {
    val cn = cardNumber.replace(" ", "")
    var result = ""

    cn.forEachIndexed { index, c ->
        result += if (index % 4 == 0) " $c" else c
    }

    return result.trim()
}

fun getCardType(cardNumber: String): CardType =
    when {
        cardNumber[0] == '5' -> CardType.MASTERCARD
        cardNumber[0] == '4' -> CardType.VISA
        else -> CardType.UNKNOWN
    }

fun getCardTypeIcon(cardType: CardType): Int? =
    if (cardType == CardType.VISA) R.drawable.visa else if (cardType == CardType.MASTERCARD) R.drawable.mc else null

fun ImageView.setCardTypeIcon(cardType: CardType) {
    val icon = getCardTypeIcon(cardType)
    if (icon != null) {
        isVisible = true
        setImageResource(icon)
    } else {
        isVisible = false
    }
}

fun String.isValidCardNumber(): Boolean =
    this.replace(" ", "").length == 16

fun Context.toast(msg: String) {
    Toast.makeText(this, msg.trim(), Toast.LENGTH_SHORT).show()
}

