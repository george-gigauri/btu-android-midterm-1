package ge.georgegigauri.midtermexam

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private lateinit var cardMain: CardView
    private lateinit var cardNumber: TextView
    private lateinit var cardTypeImage: ImageView
    private lateinit var expiration: TextView
    private lateinit var cardHolderName: TextView
    private lateinit var btnAddCard: TextView
    private lateinit var emptyText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        if (cardExists()) {
            val card = getCard()

            cardNumber.text = formatCardNumber(card.cardNumber)
            cardHolderName.text = card.cardHolder
            cardTypeImage.setImageResource(
                when (card.cardType) {
                    CardType.MASTERCARD -> R.drawable.mc
                    else -> R.drawable.visa
                }
            )

            cardHolderName.text = card.cardHolder
            expiration.text = card.expiration
        }

        btnAddCard.setOnClickListener { openCardAddActivity() }
        cardMain.setOnClickListener { startActivity(Intent(this, ViewCardActivity::class.java)) }
    }

    private fun init() {
        cardMain = findViewById(R.id.cardMain)
        cardNumber = findViewById(R.id.tvCardNumber)
        cardTypeImage = findViewById(R.id.ivCardType)
        cardHolderName = findViewById(R.id.tvCardHolder)
        expiration = findViewById(R.id.tvExpiration)
        btnAddCard = findViewById(R.id.tvAddCard)
        emptyText = findViewById(R.id.tvEmpty)

        cardMain.isVisible = cardExists()
        emptyText.isVisible = !cardExists()
    }

    private fun openCardAddActivity() {
        val intent = Intent(this, AddCardActivity::class.java)
        startActivity(intent)
    }
}