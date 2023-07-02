package ru.demchuk.messenger.ui.recyclerStreams

import android.annotation.SuppressLint
import android.util.Log
import androidx.recyclerview.widget.DiffUtil

class StreamAdapterItemCallback : DiffUtil.ItemCallback<DelegateItem>() {

    override fun areItemsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean {
        return oldItem::class == newItem::class && oldItem.id() == newItem.id()
    }


    override fun areContentsTheSame(oldItem: DelegateItem, newItem: DelegateItem): Boolean {
        return oldItem.compareToOther(newItem)
    }
}