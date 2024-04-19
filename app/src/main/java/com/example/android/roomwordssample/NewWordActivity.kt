package com.example.android.roomwordssample

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewWordActivity : AppCompatActivity() {
    private lateinit var editDate: EditText
    private lateinit var editCat: EditText
    private lateinit var editid: EditText
    private lateinit var editAmount: EditText
    private lateinit var btnDatePicker: EditText
    private lateinit var editNote: EditText
    private var bottomSheetDialog: BottomSheetDialog? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        btnDatePicker = findViewById(R.id.date)
        btnDatePicker.inputType = InputType.TYPE_NULL
        btnDatePicker.setOnClickListener {
            showDatePickerin()
        }

        supportActionBar!!.hide()


        editAmount = findViewById(R.id.enterAmountedit)
        editDate = findViewById(R.id.date)
        editCat = findViewById(R.id.Categoryaddinc)
        val newDrawable = resources.getDrawable(R.drawable.img_18)

        changeDrawableLeft(editCat, newDrawable, 52,52)

        editNote = findViewById(R.id.Note)
        editid = findViewById(R.id.EnterId)
        editCat.setOnClickListener {
            showBottomSheetDialog()
        }

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editCat.text) ||
                TextUtils.isEmpty(editAmount.text) ||
                TextUtils.isEmpty(editDate.text)
            ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val amount = editAmount.text.toString()
                val categ = editCat.text.toString()
                val id = editid.text.toString().toIntOrNull()
                val date = editDate.text.toString()
                val note = editNote.text.toString()
                val  image = newDrawable

                replyIntent.putExtra(EXTRA_REPLY_WORD, amount)
                replyIntent.putExtra(EXTRA_REPLY_categ, categ)
                replyIntent.putExtra(EXTRA_REPLY_ID, id)
                replyIntent.putExtra(EXTRA_REPLY_ADDRESS, note)
                replyIntent.putExtra(EXTRA_REPLY_EMP_ID, date)
//                replyIntent.putExtra("pic", newDrawable)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            Toast.makeText(this,"Fill all Details",Toast.LENGTH_LONG).show()
            finish()
//            bottomSheetDialog.dismiss()
        }
    }

    companion object {
        const val EXTRA_REPLY_WORD = "REPLY_WORD"
        const val EXTRA_REPLY_categ = "REPLY_categ"
        const val EXTRA_REPLY_ADDRESS = "REPLY_ADDRESS"
        const val EXTRA_REPLY_ID = "REPLY_ID"
        const val EXTRA_REPLY_EMP_ID = "REPLY_EMP_ID"
        const val EXTRA_REPLY_IMG = "REPLY_EMP_IMG"
    }

    private fun showDatePickerin() {
        val currentDate = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.DatePickerIncome,
            { datePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                Toast.makeText(this, "$formattedDate", Toast.LENGTH_SHORT).show()
                editDate.setText(formattedDate)
            },
            currentDate.get(Calendar.YEAR),
            currentDate.get(Calendar.MONTH),
            currentDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = currentDate.timeInMillis
        datePickerDialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showBottomSheetDialog() {
        bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.category_bottom_sheet, null)
        bottomSheetDialog!!.setContentView(view)

        view.findViewById<View>(R.id.CatBills)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.CatBills) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Deposit")
            bottomSheetDialog?.dismiss()
        }
        view.findViewById<View>(R.id.communicationImg)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.communicationImg) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Salary")
            bottomSheetDialog?.dismiss()
        }
        view.findViewById<View>(R.id.imvestImage)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.imvestImage) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Investment Return")
            bottomSheetDialog?.dismiss()
        }

        bottomSheetDialog!!.show()
    }

    fun changeDrawableLeft(editText: EditText, drawable: Drawable?, width: Int, height: Int) {
        drawable?.setBounds(0, 0, width, height)
        editText.setCompoundDrawables(drawable, null, null, null)
    }
    private fun resizeDrawable(drawable: Drawable?, width: Int, height: Int): Drawable? {
        if (drawable == null) return null
        val bitmap = Bitmap.createScaledBitmap((drawable as BitmapDrawable).bitmap, width, height, false)
        return BitmapDrawable(resources, bitmap)
    }
}