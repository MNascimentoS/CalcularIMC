package br.com.spacerocket.calcularimc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        initListeners()
    }

    private fun initUi() {
        weigthEDT.addTextChangedListener(MaskUtil.insert(weigthEDT, "##,#"))
        heightEDT.addTextChangedListener(MaskUtil.insert(heightEDT, "#,##"))
    }

    private fun initListeners() {
        calculateIMCBTN?.setOnClickListener {
            val weight = weigthEDT.text.toString().replace(",", ".")
            val height = heightEDT.text.toString().replace(",", ".")
            if (weight.isBlank() || height.isBlank()) {
                Toast
                        .makeText(this, "Os campos não ṕodem fica vazios", Toast.LENGTH_LONG)
                        .show()
                return@setOnClickListener
            }
            val result = (weight.toDouble() / (height.toDouble() * height.toDouble()))
            var resultString = String.format("%.2f", result)
            resultString = resultString.replace(".", ",")

            val resultIMCString = when {
                result < 16 -> "Magreza Grave"
                result > 16 && result < 17 -> "Magreza moderada"
                result > 17 && result < 18.5 -> "Magreza Leve"
                result > 18.5 && result < 25 -> "Saudável"
                result > 25 && result < 30 -> "Sobrepeso"
                result > 30 && result < 35 -> "Obesidade Grau I"
                result > 35 && result < 40 -> "Obesidade Grau II (Severa)"
                result > 40 -> "Obesidade Grau III (Mórbida)"
                else -> "Não foi possível calcular o resultado do IMC"
            }

            resultTXT.text = resultString
            imcResultTXT.text = resultIMCString
            textResyltOneTXT.visibility = View.VISIBLE
            imcResultTXT.visibility = View.VISIBLE
        }
    }
}
