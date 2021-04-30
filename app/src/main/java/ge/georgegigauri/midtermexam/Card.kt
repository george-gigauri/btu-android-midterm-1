package ge.georgegigauri.midtermexam

data class Card(
    var cardNumber: String,
    var cardHolder: String,
    var cardType: CardType,
    var expirationMonth: Int,
    var expirationYear: Int,
    var cvv: Int
) {
    val type = cardType.name
    val expiration = "$expirationMonth / ${expirationYear.toString().takeLast(2)}"
}
