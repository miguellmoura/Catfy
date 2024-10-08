package com.example.catify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.catify.databinding.ItemViewBinding
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class CatAdapter : RecyclerView.Adapter<CatAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cat: Cat) {

            binding.nomeGatinhozinho.text = cat.breeds[0].name

            Picasso.get()
                .load(cat.url)
                .resize(300, 200)
                .centerCrop()
                .transform(RoundedCornersTransformation(14, 0))
                .into(binding.fotoGatinhozinho)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = Singleton.catsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Singleton.catsList[position].let {
            holder.bind(it)
        }
    }
}