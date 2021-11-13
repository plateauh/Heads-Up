package com.najed.headsupv2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.najed.headsupv2.databinding.CelebItemBinding
import com.najed.headsupv2.db.Celeb
import com.najed.headsupv2.db.CelebsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Adapter(private var celebsList: List<Celeb>,
              private val context: Context):
    RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: CelebItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(CelebItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val celeb = celebsList[position]
        val alert by lazy { Alert(context, celeb) }
        holder.binding.apply {

            itemNameTv.text = celeb.name
            itemTaboo1Tv.text = celeb.taboo1
            itemTaboo2Tv.text = celeb.taboo2
            itemTaboo3Tv.text = celeb.taboo3

            editIb.setOnClickListener {
                alert.updateAlert()
                update()
            }

            deleteIb.setOnClickListener {
                alert.deleteAlert()
                update()
            }
        }
    }

    override fun getItemCount() = celebsList.size

    fun update() {
        CoroutineScope(Dispatchers.IO).launch {
            celebsList = CelebsDatabase.getInstance(context).celebDAO().getAllCelebs()
            withContext(Main) {
                notifyDataSetChanged()
            }
        }
        notifyDataSetChanged()
    }
}