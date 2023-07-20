package ru.demchuk.messenger.ui.recyclerStreams.stream

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.demchuk.messenger.R
import ru.demchuk.messenger.databinding.StreamItemBinding
import ru.demchuk.messenger.ui.adapterDelegate.AdapterDelegate
import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem

class StreamAdapter(val action : (Stream) -> Unit) : AdapterDelegate {

    private val _stateShimmer : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val stateShimmer = _stateShimmer.asStateFlow()
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

        (holder as ViewHolder).startShimmer()
        (holder as ViewHolder).bind(item.content() as Stream)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is StreamDelegate

    inner class ViewHolder(private val bind: StreamItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(streamModel: Stream) {
            bind.apply {
                shimmer.stopShimmer()
                shimmer.setShimmer(null)
                stream.text = streamModel.name
            }
            bind.buttonOpenTopic.setOnClickListener { view ->
             action(streamModel)
                when (streamModel.press) {
                  false -> {
                      view.setBackgroundResource(R.drawable.item_close_topic)
                      streamModel.press = true
                  }
                    true -> {
                        view.setBackgroundResource(R.drawable.item_open_topic)
                        streamModel.press = false
                    }
                }
            }

        }

        fun startShimmer() {
            bind.shimmer.startShimmer()
        }
    }
}