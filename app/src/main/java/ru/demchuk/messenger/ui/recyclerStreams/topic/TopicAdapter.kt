package ru.demchuk.messenger.ui.recyclerStreams.topic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.demchuk.messenger.databinding.TopicItemBinding
import ru.demchuk.messenger.ui.adapterDelegate.AdapterDelegate
import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem

class TopicAdapter(private val action: () -> Unit) : AdapterDelegate {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(
            TopicItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as Topic)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is TopicDelegate


    inner class ViewHolder(private val bind: TopicItemBinding) : RecyclerView.ViewHolder(bind.root) {

        fun bind(topic: Topic) {
            bind.topic.text = topic.name
            bind.topic.setOnClickListener {
                action()
            }
        }
    }
}