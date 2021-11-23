package com.example.boardgamesguide.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.boardgamesguide.databinding.LayoutItemBinding
import com.example.boardgamesguide.domain.model.Game
import com.example.boardgamesguide.domain.model.GameItems


class BaseGamesAdapter: ListAdapter<Game, BaseGamesAdapter.MainViewHolder>(ItemDiffUtil()) {

    class MainViewHolder(val binding: LayoutItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onViewAttachedToWindow(holder: MainViewHolder) {
        holder.itemView.setOnClickListener {

        }
    }



    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.apply {
            val item =  getItem(position)
            holder.binding.tvName.text = item.name
            holder.binding.tvDescription.text = item.description_preview
            Glide.with(ivGameImage).load(item.image_url).into(ivGameImage)
        }
    }
}
class ItemDiffUtil: DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}