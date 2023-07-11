package ru.demchuk.messenger.ui.recyclerStreams.stream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.demchuk.messenger.R
import ru.demchuk.messenger.databinding.StreamItemBinding
import ru.demchuk.messenger.ui.adapterDelegate.AdapterDelegate
import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem

class StreamAdapter(val action : (Stream) -> Unit) : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(
            StreamItemBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as Stream)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is StreamDelegate

    inner class ViewHolder(private val bind: StreamItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(stream: Stream) {
            bind.stream.text = stream.name
            bind.buttonOpenTopic.setOnClickListener { view ->
             action(stream)
                when (stream.press) {
                  false -> {
                      view.setBackgroundResource(R.drawable.item_close_topic)
                      stream.press = true
                  }
                    true -> {
                        view.setBackgroundResource(R.drawable.item_open_topic)
                        stream.press = false
                    }
                }
            }
        }
    }
}