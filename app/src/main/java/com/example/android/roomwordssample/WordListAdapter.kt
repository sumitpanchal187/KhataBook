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
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class WordListAdapter(
    private val onItemClick: (Word) -> Unit
) : ListAdapter<Word, WordListAdapter.WordViewHolder>(WORDS_COMPARATOR) {

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

            val drawable = ContextCompat.getDrawable(itemView.context, R.drawable.img)
            imageView.setImageDrawable(drawable)

            val textColor = if (word.activityType == ActivityType.ADD_EXPENSE) {
                Color.RED
            } else {
                Color.GREEN
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
