package com.example.android.roomwordssample
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

enum class ActivityType {
    ADD_EXPENSE,
    ADD_WORD
}

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val newWordActivityRequestCode = 1
    private val addNewExpenseRequestCode = 2
    private val UpdateNewIncomeRequestCode = 3
    private val UpdateNewEXRequestCode = 4
    private val drawable = 5
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = WordListAdapter(
            onItemClick = { word ->
                val intent = if (word.activityType == ActivityType.ADD_EXPENSE) {
                    Intent(this@MainActivity, UpdateExpenseActivity::class.java)

                } else {
                    Intent(this@MainActivity, UpdateIncomeActivity::class.java)
                }
                intent.putExtra("word", word.word)
                intent.putExtra("cate", word.category)
                intent.putExtra("id", word.id)
                intent.putExtra("date", word.date)
                intent.putExtra("note", word.note)
                if (word.activityType==ActivityType.ADD_WORD){
                    startActivityForResult(intent, newWordActivityRequestCode)
                }
                else
                {
                    startActivityForResult(intent, addNewExpenseRequestCode)
                }

            }
        )


        recyclerView.adapter = adapter

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnavigationview)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Khtaa -> {
                    // Handle Khata item click
                    true
                }
                R.id.addd -> {
                    // Show bottom sheet dialog
                    showBottomSheetDialog()
                    true
                }
                else -> false
            }
        }.also {
            // Ensure "Khata" item is selected initially
            val khataMenuItem = bottomNavigationView.menu.findItem(R.id.Khtaa)
            khataMenuItem?.isChecked = true
        }


        wordViewModel.allWords.observe(this) { words ->
            words.let {
                adapter.submitList(it)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            data?.let { intentData ->
                val activityType = when (requestCode) {
                    newWordActivityRequestCode -> ActivityType.ADD_WORD
                    addNewExpenseRequestCode -> ActivityType.ADD_EXPENSE
                    UpdateNewIncomeRequestCode -> ActivityType.ADD_WORD
                    UpdateNewEXRequestCode -> ActivityType.ADD_EXPENSE
                    else -> ActivityType.ADD_EXPENSE
                }

                val word = Word(
                    id = intentData.getIntExtra(NewWordActivity.EXTRA_REPLY_ID, 0),
                    word = intentData.getStringExtra(NewWordActivity.EXTRA_REPLY_WORD).orEmpty(),
                    category = intentData.getStringExtra(NewWordActivity.EXTRA_REPLY_categ).orEmpty(),
                    date = intentData.getStringExtra(NewWordActivity.EXTRA_REPLY_EMP_ID).orEmpty(),
                    note = intentData.getStringExtra(NewWordActivity.EXTRA_REPLY_ADDRESS).orEmpty(),
                    activityType = activityType
                )

                wordViewModel.insert(word)
            }
        }

    }
    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
        bottomSheetDialog.setContentView(view)

        view.findViewById<ImageView>(R.id.AddIncome).setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
            bottomSheetDialog.dismiss()

        }

        view.findViewById<ImageView>(R.id.AddIncome2).setOnClickListener {
            val intent = Intent(this@MainActivity, addNewExpense::class.java)
            startActivityForResult(intent, addNewExpenseRequestCode)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
    private fun generateDrawableList(context: Context, size: Int): List<Drawable?> {

    val drawableList = mutableListOf<Drawable?>()
        val drawableResource = R.drawable.img
        val defaultDrawable = ContextCompat.getDrawable(context, drawableResource)
        repeat(size) {
            drawableList.add(defaultDrawable)
        }
        return drawableList
    }
}