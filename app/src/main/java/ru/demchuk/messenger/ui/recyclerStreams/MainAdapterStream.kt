package ru.demchuk.messenger.ui.recyclerStreams


import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MainAdapterStream : ListAdapter<DelegateItem, RecyclerView.ViewHolder>(StreamAdapterItemCallback()) {

    private val listDelegate = mutableListOf<AdapterDelegateStream>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("dddddddddddddddddddddddddddddddddd", "mmmmmmmmmmmmmmmmmmm")
     return listDelegate[viewType].onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("sssssssssssssssssssss", getItemViewType(position).toString())
        listDelegate[getItemViewType(position)].onBindViewHolder(holder, getItem(position), position)
    }

    fun addDelegate(delegate:AdapterDelegateStream) {
        Log.d("dddddddddddddddddddddddddddddddddd", "mmmmmmmmmmmmmmmmmmm1")
        listDelegate.add(delegate)
    }

    override fun getItemViewType(position: Int): Int {
        return listDelegate.indexOfFirst { it.isOfViewType(currentList[position]) }
    }


}