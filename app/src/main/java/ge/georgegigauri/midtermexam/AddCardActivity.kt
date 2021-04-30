package ge.georgegigauri.midtermexam

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged

class AddCardActivity : AppCompatActivity() {

    private lateinit var tvCardNumber: TextView
    private lateinit var tvExpiration: TextView
    private lateinit var tvCardHolder: TextView
    private lateinit var ivCardType: ImageView

    private lateinit var etCardNumber: EditText
    private lateinit var etCardHolder: EditText
    private lateinit var etExpiration: EditText
    private lateinit var etCVV: EditText

    private lateinit var btnAddCard: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        init()
        editTextListeners()

        btnAddCard.setOnClickListener { saveCard() }
    }

    private fun init() {
        tvCardNumber = findViewById(R.id.tvCardNumber)
        tvExpiration = findViewById(R.id.tvExpiration)
        tvCardHolder = findViewById(R.id.tvCardHolder)
        ivCardType = findViewById(R.id.ivCardType)

        etCardNumber = findViewById(R.id.etCardNumber)
        etCardHolder = findViewById(R.id.etCardHolder)
        etExpiration = findViewById(R.id.etExpiration)
        etCVV = findViewById(R.id.etCVV)

        btnAddCard = findViewById(R.id.tvAddCard)

        if (cardExists()) {
            val card = getCard()
            tvCardNumber.text = card.cardNumber
            tvCardHolder.text = card.cardHolder
            tvExpiration.text = card.expiration
            ivCardType.setCardTypeIcon(card.cardType)
        }
    }

    private fun editTextListeners() {
        etCardNumber.doAfterTextChanged {
            val txt = it.toString()
            val formattedText = formatCardNumber(txt)

            if (txt.isNotEmpty()) {
                val cardType = getCardType(txt)
                ivCardType.setCardTypeIcon(cardType)
            }

            if (it != null) {
                var i = 4
                while (i < it.length) {
                    if (it[i] != ' ') {
                        it.insert(i, " ")
                    }
                    i += 5
                }

                tvCardNumber.text = formattedText
            }
        }

        etCardHolder.doOnTextChanged { text, start, before, count ->
            tvCardHolder.text = text
        }

        etExpiration.doAfterTextChanged {
            if (it != null) {
                var i = 2
                while (i < it.length) {
                    if (it[i] != '/') {
                        it.insert(i, "/")
                    }
                    i += 4
                }
            }

            tvExpiration.text = it
        }
    }

    private fun saveCard() {
        if (!validate())
            return


        val card = Card(
            etCardNumber.text.toString(),
            etCardHolder.text.toString(),
            getCardType(etCardNumber.text.toString()),
            etExpiration.text.toString().split("/")[0].toInt(),
            etExpiration.text.toString().split("/")[1].toInt(),
            etCVV.text.toString().toInt()
        )

        saveCard(card)

        Toast.makeText(this, "ბარათი წარმატები დაემატა!", Toast.LENGTH_SHORT).show()
        startActivity(
            Intent(
                this,
                MainActivity::class.java
            )
        )
        finish()
    }

    private fun validate(): Boolean {
        if (!etCardNumber.text.toString().isValidCardNumber()) {
            toast("ბარათის ნომრის არასწორი ფორმატი!")
            return false
        }

        if (etCardHolder.text.toString().isEmpty()) {
            toast("შეიტანეთ მომხმარებლის სახელი!")
            return false
        }

        if (etExpiration.text.toString().isEmpty()) {
            toast("შეიტანეთ მოქმედების ვადა!")
            return false
        }

        if (etExpiration.text.toString().split("/")[0].toInt() > 12) {
            toast("მოქმედების ვადა არასწორია!")
            return false
        }

        if (etExpiration.text.toString().split("/")[1].toInt() < 22) {
            toast("ბარათი ვადაგასულია!")
            return false
        }

        return true
    }
}