package ru.demchuk.messenger.ui.people

import androidx.recyclerview.widget.DiffUtil
import ru.demchuk.messenger.ui.people.model.UserModelUi

class CallBackListAdapter : DiffUtil.ItemCallback<UserModelUi>() {

    override fun areItemsTheSame(oldItem: UserModelUi, newItem: UserModelUi): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserModelUi, newItem: UserModelUi): Boolean {
       return oldItem == newItem
    }
}