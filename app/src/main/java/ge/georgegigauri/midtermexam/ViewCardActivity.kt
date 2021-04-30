package ge.georgegigauri.midtermexam

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ViewCardActivity : AppCompatActivity() {
    private lateinit var tvCardNumber: TextView
    private lateinit var tvExpiration: TextView
    private lateinit var tvCardHolder: TextView
    private lateinit var ivCardType: ImageView

    private lateinit var btnAddCard: Button
    private lateinit var btnCheckout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_card)

        init()

        btnAddCard.setOnClickListener { startActivity(Intent(this, AddCardActivity::class.java)) }
        btnCheckout.setOnClickListener { startActivity(Intent(this, SuccessActivity::class.java)) }
    }

    private fun init() {
        tvCardNumber = findViewById(R.id.txtCardNum)
        tvExpiration = findViewById(R.id.txtExp)
        tvCardHolder = findViewById(R.id.txtNameholder)
        ivCardType = findViewById(R.id.cardType)
        btnAddCard = findViewById(R.id.button_add_card)
        btnCheckout = findViewById(R.id.button_pay_now)

        val card = getCard()
        tvCardNumber.text = card.cardNumber
        tvCardHolder.text = card.cardHolder
        tvExpiration.text = card.expiration
        ivCardType.setCardTypeIcon(card.cardType)
    }
}