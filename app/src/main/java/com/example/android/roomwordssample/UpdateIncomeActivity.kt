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

class UpdateIncomeActivity : AppCompatActivity() {
    private lateinit var editDate : EditText
    private lateinit var editCat : EditText
    private lateinit var editid : EditText
    private lateinit var editAmount : EditText
    private lateinit var btnDatePicker: EditText
    private lateinit var deleteBtn: Button
    private lateinit var saveBtn: Button
    private lateinit var editNote : EditText
    private var bottomSheetDialog: BottomSheetDialog? = null

    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_income)

        supportActionBar!!.hide()

        btnDatePicker = findViewById(R.id.UpdateInDate)
        btnDatePicker.inputType = InputType.TYPE_NULL
        btnDatePicker.setOnClickListener {
            updateDatePicker()
        }

        editAmount = findViewById(R.id.UpdateIncomeAmount)
        editDate = findViewById(R.id.UpdateInDate)
        editCat = findViewById(R.id.UpdateIncCategory)
        val newDrawable = resources.getDrawable(R.drawable.img_11)

        changeDrawableLeft(editCat, newDrawable, 52,52)
        editNote = findViewById(R.id.updateInNote)
        editid = findViewById(R.id.incomeupdateid)
        editCat.setOnClickListener{
            UpInshowBottomSheetDialog()
        }


        val existingWord = intent.getStringExtra("word")
        val existingCate = intent.getStringExtra("cate")
        val existingDate = intent.getStringExtra("date")
        val existingId = intent.getIntExtra("id", 0)
        val existingNote = intent.getStringExtra("note")
         editCat.setText(existingCate)
        //        editAgeView.setText(existingAge.let { it })
         editAmount.setText(existingWord)
         editid.setText(existingId.toString())
        editNote.setText(existingNote)
        editDate.setText(existingDate)


        saveBtn = findViewById(R.id.Button_save)

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

        deleteBtn = findViewById(R.id.Button_delete)
        deleteBtn.setOnClickListener {
            deleteIncome()
        }


    }
    private fun setupSaveButton() {
        saveBtn.setOnClickListener {
            val updatedWord = editAmount.text.toString()
            val updatedCate = editCat.text.toString()
            val updatedDate = editDate.text.toString()
            val updatedId = editid.text.toString().toIntOrNull()
            val updatedNote = editNote.text.toString()

            if (updatedId != null) {
                val replyIntent = Intent()
                replyIntent.putExtra("updatedWord", updatedWord)
                replyIntent.putExtra("updatedCate", updatedCate)
                replyIntent.putExtra("updatedDate", updatedDate)
                replyIntent.putExtra("updatedId", updatedId)
                replyIntent.putExtra("updatedNote", updatedNote)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            } else {
                Toast.makeText(this, "Invalid income ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun deleteIncome() {
        val updatedWord = editAmount.text.toString()

            wordViewModel.deleteWord(updatedWord)
            Log.d("DeleteIncome", "Word deleted") // Add this line for logging
            startActivity(Intent(this, MainActivity::class.java))
            finish()
    }




    private fun updateDatePicker() {
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
    private fun UpInshowBottomSheetDialog() {
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