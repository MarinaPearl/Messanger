package ru.demchuk.messenger.ui.people

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.demchuk.messenger.databinding.PeopleItemBinding
import ru.demchuk.messenger.ui.people.model.UserModelUi

class Adapter : ListAdapter<UserModelUi, RecyclerView.ViewHolder>(CallBackListAdapter()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return ViewHolder(PeopleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(getItem(position))
    }

    inner class ViewHolder(private val binding : PeopleItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(userModelUi : UserModelUi) {
            binding.apply {
                namePeople.text = userModelUi.name
                emailPeople.text = userModelUi.email
            }
        }
    }
}