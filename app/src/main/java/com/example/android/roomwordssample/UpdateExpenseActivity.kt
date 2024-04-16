package com.example.android.roomwordssample

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.android.roomwordssample.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpdateExpenseActivity : AppCompatActivity() {
    private lateinit var editDate : EditText
    private lateinit var editCat : EditText
    private lateinit var editid : EditText
    private lateinit var editAmount : EditText
    private lateinit var deleteBtn: Button
    private lateinit var btnDatePicker: EditText
    private lateinit var saveBtn: Button
    private lateinit var editNote : EditText
    private var bottomSheetDialog: BottomSheetDialog? = null

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_expense)
        supportActionBar!!.hide()

        btnDatePicker = findViewById(R.id.UpdateExDate)
        btnDatePicker.inputType = InputType.TYPE_NULL
        btnDatePicker.setOnClickListener {
            updateDatePicker()
        }

        editAmount = findViewById(R.id.UpdateExAmount)
        editDate = findViewById(R.id.UpdateExDate)
        editCat = findViewById(R.id.UpdateexCategory)

        val newDrawable = resources.getDrawable(R.drawable.img_11)

        changeDrawableLeft(editCat, newDrawable, 52,52)
        editNote = findViewById(R.id.updateExNote)
        editid = findViewById(R.id.updateid)
        editCat.setOnClickListener{
            UpshowBottomSheetDialog()
        }


        val existingWord = intent.getStringExtra("word")
        val existingCate = intent.getStringExtra("cate")
        val existingDate = intent.getStringExtra("date")
        val existingId = intent.getIntExtra("id", 0)
        val existingNote = intent.getStringExtra("note")
        editCat.setText(existingCate)
        editAmount.setText(existingWord)
        editid.setText(existingId.toString())
        editNote.setText(existingNote)
        editDate.setText(existingDate)
        deleteBtn = findViewById(R.id.updateExButton_delete)
        deleteBtn.setOnClickListener {
            deleteIncome()
        }


        saveBtn = findViewById(R.id.updateExButton_save)

        saveBtn.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editCat.text) ||
                TextUtils.isEmpty(editAmount.text) ||
                TextUtils.isEmpty(editDate.text)
            ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val word = editAmount.text.toString()
                val categ = editCat.text.toString()
                val id = editid.text.toString().toIntOrNull()
                val empid = editDate.text.toString()
                val address = editNote.text.toString()

                replyIntent.putExtra(NewWordActivity.EXTRA_REPLY_WORD, word)
                replyIntent.putExtra(NewWordActivity.EXTRA_REPLY_categ, categ)
                replyIntent.putExtra(NewWordActivity.EXTRA_REPLY_ID, id)
                replyIntent.putExtra(NewWordActivity.EXTRA_REPLY_ADDRESS, address)
                replyIntent.putExtra(NewWordActivity.EXTRA_REPLY_EMP_ID, empid)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun deleteIncome() {
        val updatedWord = editAmount.text.toString()

        wordViewModel.deleteWord(updatedWord)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    private fun updateDatePicker() {
        val currentDate = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            R.style.DatePickerExp,
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
    private fun UpshowBottomSheetDialog() {
        bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.category_expense, null)
        bottomSheetDialog!!.setContentView(view)

        view.findViewById<View>(R.id.CatBills)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.CatBills) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Bills")
            bottomSheetDialog?.dismiss()
        }
        view.findViewById<View>(R.id.communicationImg)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.communicationImg) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Communication")
            bottomSheetDialog?.dismiss()
        }

        view.findViewById<View>(R.id.imvestImage)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.imvestImage) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Debt Payment")
            bottomSheetDialog?.dismiss()
        }
        view.findViewById<View>(R.id.diningout)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.diningout) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Dining Out")
            bottomSheetDialog?.dismiss()
        }
        view.findViewById<View>(R.id.education)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.education) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Education")
            bottomSheetDialog?.dismiss()
        }
        view.findViewById<View>(R.id.enterImage)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.enterImage) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Health")
            bottomSheetDialog?.dismiss()
        }
        view.findViewById<View>(R.id.tarvel)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.tarvel) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Transport")
            bottomSheetDialog?.dismiss()
        }
        view.findViewById<View>(R.id.food)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.food) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Food")
            bottomSheetDialog?.dismiss()
        }
        view.findViewById<View>(R.id.gifts)?.setOnClickListener {
            val drawable = (view.findViewById<ImageView>(R.id.gifts) as ImageView).drawable
            val scaledDrawable = resizeDrawable(drawable, 22,22)
            editCat.setCompoundDrawablesWithIntrinsicBounds(scaledDrawable, null, null, null)
            editCat.setText("Gifts")
            bottomSheetDialog?.dismiss()
        }

        bottomSheetDialog!!.show()
    }
    private fun resizeDrawable(drawable: Drawable?, width: Int, height: Int): Drawable? {
        if (drawable == null) return null
        val bitmap = Bitmap.createScaledBitmap((drawable as BitmapDrawable).bitmap, width, height, false)
        return BitmapDrawable(resources, bitmap)
    }
    fun changeDrawableLeft(editText: EditText, drawable: Drawable?, width: Int, height: Int) {
        drawable?.setBounds(0, 0, width, height)
        editText.setCompoundDrawables(drawable, null, null, null)
    }
}