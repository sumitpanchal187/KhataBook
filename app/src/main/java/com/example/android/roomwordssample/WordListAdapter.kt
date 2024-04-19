package com.example.android.roomwordssample

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Flow

class WordListAdapter(
    private val onItemClick: (Word) -> Unit
) : ListAdapter<Word, WordListAdapter.WordViewHolder>(WORDS_COMPARATOR) {
    private var totalIncome: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        val previous = if (position > 0) getItem(position - 1) else null
        val next = if (position < itemCount - 1) getItem(position + 1) else null

        holder.bind(current, previous,next, onItemClick)
        val wordAsInt = current.word.toIntOrNull()
        if (wordAsInt == null) {
            println("Error parsing ${current.word} to Int")
        } else {
            totalIncome += wordAsInt
        }

        if (next != null && current.date == next.date) {
            holder.setDividerVisibility(View.VISIBLE)
        } else {
            holder.setDividerVisibility(View.GONE)
        }
    }


    fun getTotalIncome(): Int {
        return totalIncome
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.priceId)
        private val ageTextView: TextView = itemView.findViewById(R.id.categoryId)
        private val addressTextView: TextView = itemView.findViewById(R.id.descriptionId)
        private val idTextView: TextView = itemView.findViewById(R.id.mainId)
        private val empidTextView: TextView = itemView.findViewById(R.id.dateId)
        private val imageView: ImageView = itemView.findViewById(R.id.imageview)
        private val dividerView: View = itemView.findViewById(R.id.divider)

        fun bind(
            word: Word,
            previousWord: Word?,
            nextWord: Word?,
            onItemClick: (Word) -> Unit

        ) {
            wordItemView.text = "₹ ${word.word}"
            ageTextView.text = "${word.category}"
            addressTextView.text = "${word.note}"
            idTextView.text = "${word.id}"
            empidTextView.text = "${word.date}"

            if (previousWord != null && previousWord.date == word.date) {
                empidTextView.visibility = View.GONE
            } else {
                empidTextView.visibility = View.VISIBLE
            }


            val drawableId = when (ageTextView.text) {
                "Salary" -> R.drawable.img_7
                "Deposit" -> R.drawable.img_6
                "Investment Return" -> R.drawable.img_8
                 "Debt Payment" -> R.drawable.img_19
                "Bills" -> R.drawable.img_12
                "Communication" -> R.drawable.img_13
                "Dining Out" -> R.drawable.img_9
                "Education" -> R.drawable.img_10
                "Health" -> R.drawable.img_11
                "Food" -> R.drawable.img_14
                "Transport" -> R.drawable.img_15
                "Gifts" -> R.drawable.img_16
                else -> R.drawable.img
            }
            val drawable = ContextCompat.getDrawable(itemView.context, drawableId)
            imageView.setImageDrawable(drawable)

            val textColor = if (word.activityType == ActivityType.ADD_EXPENSE) {
                Color.RED
            } else {
                Color.rgb(0,164,0)
            }
            wordItemView.setTextColor(textColor)
            itemView.setOnClickListener { onItemClick(word) }
        }

        fun setDividerVisibility(visibility: Int) {
            dividerView.visibility = visibility
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem === newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
                return oldItem.word == newItem.word
            }
        }
    }


}

