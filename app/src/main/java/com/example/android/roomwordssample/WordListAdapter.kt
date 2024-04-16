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
        holder.bind(current, onItemClick)
    }


    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.priceId)
        private val ageTextView: TextView = itemView.findViewById(R.id.categoryId)
        private val addressTextView: TextView = itemView.findViewById(R.id.descriptionId)
        private val idTextView: TextView = itemView.findViewById(R.id.mainId)
        private val empidTextView: TextView = itemView.findViewById(R.id.dateId)
        private val imageView: ImageView = itemView.findViewById(R.id.imageview)

        fun bind(
            word: Word,
            onItemClick: (Word) -> Unit
            // Receive item click listener
        ) {
            wordItemView.text = "₹ ${word.word}"
            ageTextView.text = "${word.category}"
            addressTextView.text = "${word.note}"
            idTextView.text = "${word.id}"
            empidTextView.text = "${word.date}"

            if (ageTextView.text=="Salary"){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img_7)
                imageView.setImageDrawable(drawable)
            }
            if (ageTextView.text=="Deposit"){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img_6)
                imageView.setImageDrawable(drawable)
            }
            if (ageTextView.text=="Investment Return"||ageTextView.text=="Debt Payment"){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img_8)
                imageView.setImageDrawable(drawable)
            }
            if (ageTextView.text=="Bills"){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img_12)
                imageView.setImageDrawable(drawable)
            }
            if (ageTextView.text=="Communication"){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img_13)
                imageView.setImageDrawable(drawable)
            }
            if (ageTextView.text=="Dining Out"){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img_9)
                imageView.setImageDrawable(drawable)
            }
            if (ageTextView.text=="Education"){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img_10)
                imageView.setImageDrawable(drawable)
            }
            if (ageTextView.text=="Health"){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img_11)
                imageView.setImageDrawable(drawable)
            }
            if (ageTextView.text=="Food"){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img_14)
                imageView.setImageDrawable(drawable)
            }
            if (ageTextView.text=="Transport"){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img_15)
                imageView.setImageDrawable(drawable)
            }
            if (ageTextView.text=="Gifts"){
                val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img_16)
                imageView.setImageDrawable(drawable)
            }

            val textColor = if (word.activityType == ActivityType.ADD_EXPENSE) {
                Color.RED
            } else {
                Color.rgb(0,164,0)
            }
            wordItemView.setTextColor(textColor)
            itemView.setOnClickListener { onItemClick(word) }
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

