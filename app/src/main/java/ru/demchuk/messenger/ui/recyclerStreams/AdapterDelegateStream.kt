package ru.demchuk.messenger.ui.recyclerStreams

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface  AdapterDelegateStream {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int)
    fun isOfViewType(item: DelegateItem): Boolean
}