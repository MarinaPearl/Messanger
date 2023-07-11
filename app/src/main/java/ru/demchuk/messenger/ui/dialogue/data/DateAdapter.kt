package ru.demchuk.messenger.ui.dialogue.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.demchuk.messenger.databinding.DateModelBinding
import ru.demchuk.messenger.ui.adapterDelegate.AdapterDelegate
import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem

class DateAdapter : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(DateModelBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as DateModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is DateDelegate

    inner class ViewHolder(private val binding: DateModelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dateModel: DateModel) {
            binding.apply {
                date.text = dateModel.date
            }
        }
    }
}