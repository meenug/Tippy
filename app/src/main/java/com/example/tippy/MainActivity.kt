// Calculates Tips
package com.example.tippy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INIT_TIP_PERCENT = 15
class MainActivity : AppCompatActivity() {
    private lateinit var editBaseAmount: EditText
    private lateinit var tv24Percent: TextView
    private lateinit var seekBarTip: SeekBar
    private lateinit var tvTipAmount: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var tvTipDescription: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editBaseAmount = findViewById(R.id.editBaseAmount)
        tv24Percent = findViewById(R.id.tv24percent)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        tvTipDescription = findViewById(R.id.tvTipDescription)

        seekBarTip.progress = INIT_TIP_PERCENT
        tv24Percent.text = "$INIT_TIP_PERCENT%"
        updateTipDescription(INIT_TIP_PERCENT)

        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG,  "onProgressChanged $p1" )
                tv24Percent.text = "$p1%"
                computeTipAmount()
                updateTipDescription(p1)



            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })
        editBaseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG,  "afterTextChanged $p0" )
                computeTipAmount()

            }
        })

    }

    private fun updateTipDescription(tipPercent: Int) {
        val tipDescription = when (tipPercent) {
            in 0..9 -> "\uD83D\uDC4E"
            in 10..14 -> "\uD83D\uDE46"
            in 15..19 -> "\uD83D\uDC4C"
            in 20..24 -> "\uD83D\uDE0A"
            else-> "\uD83E\uDEF6"
        }
        tvTipDescription.text = tipDescription
    }

    private fun computeTipAmount() {
        // 1. Get the value of base and tip %
        if (editBaseAmount.text.isEmpty()){
            tvTipAmount.text = ""
            tvTotalAmount.text = ""
            return
        }
        val baseAmount =  editBaseAmount.text.toString().toDouble()
        val tipPercent =  seekBarTip.progress

        // 2. Compute the tip and total
        val tipAmount = baseAmount * tipPercent/100
        val totalAmount = baseAmount + tipAmount

        // 3. Update UI
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotalAmount.text = "%.2f".format(totalAmount)
    }
}