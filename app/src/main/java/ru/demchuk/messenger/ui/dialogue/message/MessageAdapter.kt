package ru.demchuk.messenger.ui.dialogue.message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.demchuk.messenger.databinding.ModelMessageBinding
import ru.demchuk.messenger.ui.adapterDelegate.AdapterDelegate
import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem

class MessageAdapter : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(
            ModelMessageBinding.inflate(
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
        (holder as ViewHolder).bind(item.content() as MessageModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is MessageDelegate

    inner class ViewHolder(private val binding: ModelMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(messageModel: MessageModel) {
            binding.apply {
                message.text = messageModel.message
                name.text = messageModel.name
            }

        }
    }
}