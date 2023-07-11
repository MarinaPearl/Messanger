package ru.demchuk.messenger.ui.adapterDelegate


import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class MainAdapterDelegate : ListAdapter<DelegateItem, RecyclerView.ViewHolder>(
    AdapterDelegateItemCallback()
) {

    private val listDelegate = mutableListOf<AdapterDelegate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
     return listDelegate[viewType].onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        listDelegate[getItemViewType(position)].onBindViewHolder(holder, getItem(position), position)
    }

    fun addDelegate(delegate: AdapterDelegate) {
        listDelegate.add(delegate)
    }

    override fun getItemViewType(position: Int): Int {
        return listDelegate.indexOfFirst { it.isOfViewType(currentList[position]) }
    }
}