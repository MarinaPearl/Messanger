package ru.demchuk.messenger.ui.people

import androidx.recyclerview.widget.DiffUtil
import ru.demchuk.messenger.ui.people.model.PeopleModel

class CallBackListAdapter : DiffUtil.ItemCallback<PeopleModel>() {

    override fun areItemsTheSame(oldItem: PeopleModel, newItem: PeopleModel): Boolean {
       return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PeopleModel, newItem: PeopleModel): Boolean {
       return oldItem == newItem
    }
}