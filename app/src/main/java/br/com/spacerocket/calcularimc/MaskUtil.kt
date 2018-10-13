package br.com.spacerocket.calcularimc

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView

object MaskUtil {
    const val CPF_MASK = "###.###.###-##"
    const val CNPJ_MASK = "##.###.###/####-##"
    const val BIRTH_MASK = "##/##/####"
    const val PHONE_MASK = "(##)#####-####"
    const val CRM_MASK = "########-#/##"
    const val CARD_NUMBER_MASK = "#### #### #### ####"
    const val CARD_DATE_MASK = "##/##"
    const val CARD_CVV_MASK = "###"
    const val ADDRESS_ZIP_CODE_MASK = "#####-###"

    fun unmask(s: String): String {
        return s.replace("[^0-9]*".toRegex(), "")
    }

    fun insert(editText: EditText, maskType: String, textView: TextView? = null, onFinished: ((text: String) -> Unit)? = null): TextWatcher {
        return object : TextWatcher {

            var isUpdating: Boolean = false
            var oldValue = ""

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val value = MaskUtil.unmask(s.toString())
                val mask = maskType

                var maskAux = ""
                if (isUpdating) {
                    oldValue = value
                    isUpdating = false
                    return
                }
                var i = 0
                for (m in mask.toCharArray()) {
                    if (m != '#' && value.length > oldValue.length || m != '#' && value.length < oldValue.length && value.length != i) {
                        maskAux += m
                        continue
                    }

                    try { maskAux += value[i]
                    } catch (e: Exception) { break }
                    i++
                }
                isUpdating = true
                textView?.text = maskAux
                editText.setText(maskAux)
                editText.setSelection(maskAux.length)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) { }
            override fun afterTextChanged(s: Editable) {
                if (s.length == getSize(maskType)) onFinished?.invoke(s.toString())
            }
        }
    }

    fun getSize(mask: String) = when (mask) {
        CPF_MASK -> 14
        CNPJ_MASK -> 18
        BIRTH_MASK -> 10
        PHONE_MASK -> 14
        CRM_MASK -> 13
        CARD_NUMBER_MASK -> 19
        CARD_DATE_MASK -> 5
        CARD_CVV_MASK -> 3
        ADDRESS_ZIP_CODE_MASK -> 9
        else -> 0
    }
}
