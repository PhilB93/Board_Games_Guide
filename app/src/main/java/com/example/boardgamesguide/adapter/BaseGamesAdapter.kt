package com.example.boardgamesguide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.boardgamesguide.R
import com.example.boardgamesguide.databinding.LayoutItemBinding
import com.example.boardgamesguide.domain.model.Game
import com.example.boardgamesguide.util.Constants.Companion.ROUNDED_CORNERS


class BaseGamesAdapter(private val listener: BoardGameOnClickListener) :
    ListAdapter<Game, BaseGamesAdapter.MainViewHolder>(ItemDiffUtil()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(
            LayoutItemBinding.inflate(
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
        val binding: LayoutItemBinding,
        private val listener: BoardGameOnClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindGame(item: Game?) = with(binding) {
            tvName.text = item?.name
            tvDescription.text = item?.description_preview
            Glide.with(ivGameImage).load(item?.image_url).apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_search)
                    .error(R.drawable.ic_search)
                    .transform(RoundedCorners(ROUNDED_CORNERS))
            ).into(ivGameImage)
            initButtonsListeners(item)
        }

        private fun initButtonsListeners(game: Game?) {
            binding.gameItem.setOnClickListener {
                game?.let { it -> listener.onClick(it) }
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