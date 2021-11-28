package com.example.boardgamesguide.feature_boardgames.presentation.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.boardgamesguide.R
import com.example.boardgamesguide.databinding.LayoutSearchItemBinding


import com.example.boardgamesguide.feature_boardgames.domain.model.Game

private const val ROUNDED_CORNERS = 16

class SearchGamesAdapter(private val listener: BoardGameOnClickListener) :
    ListAdapter<Game, SearchGamesAdapter.MainViewHolder>(ItemDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutSearchItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false,
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.apply {
            val item = getItem(position)
            item?.let { holder.bindGame(it) }
        }
    }

    inner class MainViewHolder(
        val binding: LayoutSearchItemBinding,
        private val listener: BoardGameOnClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindGame(item: Game?) = with(binding) {
            tvName.text = "${item?.name} (${item?.year_published})"
            tvRating.text = String.format("%.1f", item?.average_user_rating)
            Glide.with(ivGameImage).load(item?.image_url).apply(
                RequestOptions()
                    .placeholder(R.drawable.noimage)
                    .error(R.drawable.ic_search)
                    .transform(RoundedCorners(ROUNDED_CORNERS))
            ).into(ivGameImage)
            initButtonsListeners(item)
        }

        private fun initButtonsListeners(game: Game?) {
            binding.searchGamesItem.setOnClickListener {
                game?.let { listener.onClick(it) }
            }
        }
    }

    class ItemDiffUtil : DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }
}