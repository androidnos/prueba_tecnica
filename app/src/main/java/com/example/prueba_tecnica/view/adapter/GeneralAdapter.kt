package com.example.prueba_tecnica.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prueba_tecnica.databinding.GeneralItemAdapterBinding
import com.example.prueba_tecnica.model.character.CharacterModel
import com.squareup.picasso.Picasso

class GeneralAdapter(
        private var list: List<CharacterModel?>,
        private val listener: ListenerAdapter
) :
        RecyclerView.Adapter<GeneralAdapter.ViewHolder>() {

    class ViewHolder(private val binding: GeneralItemAdapterBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(
                item: CharacterModel?,
                listener: ListenerAdapter
        ) {
            item?.apply {
                binding.root.setOnClickListener {
                    listener.click(id.toString())
                }
                binding.nameTextView.text = name
                // With the Picasso library we can access the images and it has a small cache added to load the images faster
                Picasso.get()
                    .load(image)
                    .into(binding.iconImageView)
            }
        }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {

        val binding =
            GeneralItemAdapterBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
    ) {
        holder.bind(list[position], listener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(listString: List<CharacterModel?>) {
        this.list = listString
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    interface ListenerAdapter {
        fun click(idItem: String)
    }
}