package com.example.calculator

//import kotlinx.android.synthetic.main.activity_main.*
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

private const val SPO = "PEndingOperation"
private const val SO = "Operand1"
private const val SOS = "operand1_store"

class MainActivity : AppCompatActivity() {
    private lateinit var result: EditText
    private lateinit var newnumber: EditText //kotlin ko kehta hai ki non null variable
    private val displayOperation by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operation) }

    //variables
    private var o1: Double? = null
    private var po = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        result = findViewById(R.id.result)
        newnumber = findViewById((R.id.newnumber))

        //buttons
        val b0: Button = findViewById(R.id.button0)
        val b1: Button = findViewById(R.id.button1)
        val b2: Button = findViewById(R.id.button2)
        val b3: Button = findViewById(R.id.button3)
        val b4: Button = findViewById(R.id.button4)
        val b5: Button = findViewById(R.id.button5)
        val b6: Button = findViewById(R.id.button6)
        val b7: Button = findViewById(R.id.button7)
        val b8: Button = findViewById(R.id.button8)
        val b9: Button = findViewById(R.id.button9)
        val bdot: Button = findViewById(R.id.buttondot)
        val bneg: Button = findViewById(R.id.buttonneg)
        val bclear: Button = findViewById(R.id.bbuttonclear)

        //operations

        val bequal: Button =
            findViewById(R.id.buttonequal)//button kyuki kotlin ko pta chale ki kya object chiaye
        val badd: Button = findViewById(R.id.buttonadd)
        val bminus: Button = findViewById(R.id.buttonsub)
        val bmul: Button = findViewById(R.id.buttonmultiply)
        val bdiv: Button = findViewById(R.id.buttondivide)

        val listner = View.OnClickListener { v ->
            val b = v as Button
            newnumber.append(b.text)
        }
        b0.setOnClickListener(listner)
        b1.setOnClickListener(listner)
        b2.setOnClickListener(listner)
        b3.setOnClickListener(listner)
        b4.setOnClickListener(listner)
        b5.setOnClickListener(listner)
        b6.setOnClickListener(listner)
        b7.setOnClickListener(listner)
        b8.setOnClickListener(listner)
        b9.setOnClickListener(listner)
        bdot.setOnClickListener(listner)


        val oplistner = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newnumber.text.toString().toDouble()
                performoperation(value, op)
            } catch (e: NumberFormatException) {
                newnumber.setText("")
            }
            po = op
            displayOperation.text = po
        }
        bequal.setOnClickListener((oplistner))
        badd.setOnClickListener((oplistner))
        bminus.setOnClickListener((oplistner))
        bmul.setOnClickListener((oplistner))
        bdiv.setOnClickListener((oplistner))


        bneg.setOnClickListener { view ->
            val value = newnumber.text.toString()
            newnumber.setText("-")
            if (value.isEmpty()) {
                newnumber.setText("-")
            } else {
                try {
                    var doubleValue = value.toDouble()
                    doubleValue *= (-1)
                    newnumber.setText(doubleValue.toString())
                } catch (e: java.lang.NumberFormatException) {
                    newnumber.setText("-")
                }
            }

        }
        bclear.setOnClickListener {
            newnumber.setText("")
            result.setText("")
            o1=null

        }
    }

    private fun performoperation(value: Double, operation: String) {
        if (o1 == null) {
            o1 = value
        } else {

            if (po == "=") {
                po = operation
            }

            when (po) {
                "=" -> o1 = value
                "/" -> if (value == 0.0) {
                    o1 = Double.NaN
                } else o1 = o1!! / value
                "+" -> o1 = o1!! + value
                "-" -> o1 = o1!! - value
                "*" -> o1 = o1!! * value

            }
        }
        result.setText(o1.toString())
        newnumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (o1 != null) {
            outState.putDouble(SO, o1!!)
            outState.putBoolean(SOS, true)
        }
        outState.putString(SPO, po)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.getBoolean(SOS, false)) {
            o1 = savedInstanceState.getDouble(SO)

        } else {
            o1 = null
        }
        o1 = savedInstanceState.getDouble(SO)
        po = savedInstanceState.getString(SPO).toString()
        displayOperation.text = po


    }
}