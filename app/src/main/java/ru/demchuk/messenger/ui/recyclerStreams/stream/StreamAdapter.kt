package ru.demchuk.messenger.ui.recyclerStreams.stream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.demchuk.messenger.databinding.StreamItemBinding
import ru.demchuk.messenger.ui.recyclerStreams.AdapterDelegateStream
import ru.demchuk.messenger.ui.recyclerStreams.DelegateItem

class StreamAdapter : AdapterDelegateStream {

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

    class ViewHolder(private val bind: StreamItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(stream: Stream) {
            bind.stream.text = stream.name
        }
    }
}