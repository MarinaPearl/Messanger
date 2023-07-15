package ru.demchuk.messenger.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.demchuk.messenger.databinding.FragmentPeopleBinding
import ru.demchuk.messenger.ui.people.Adapter
import ru.demchuk.messenger.ui.people.PeopleModel


class PeopleFragment : Fragment() {

    private lateinit var binding: FragmentPeopleBinding
    private val adapter: Adapter by lazy { Adapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerPeople.adapter = adapter
        binding.recyclerPeople.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter.submitList(people)
    }

    companion object {
        val people = listOf<PeopleModel>(
            PeopleModel(1, "Марина", "g@mail.com", "1"),
            PeopleModel(2, "Марина1", "g@mail.com", "1"),
            PeopleModel(3, "Марина2", "g@mail.com", "1"),
            PeopleModel(4, "Марина3", "g@mail.com", "1"),
            PeopleModel(5, "Марина4", "g@mail.com", "1"),
            PeopleModel(6, "Марина5", "g@mail.com", "1"),
            PeopleModel(7, "Марина6", "g@mail.com", "1"),
            PeopleModel(8, "Марина7", "g@mail.com", "1"),
            PeopleModel(9, "Марина8", "g@mail.com", "1"),
            PeopleModel(10, "Марина9", "g@mail.com", "1"),
            PeopleModel(11, "Марина10", "g@mail.com", "1"),
            PeopleModel(12, "Марина10", "g@mail.com", "1"),
            PeopleModel(13, "Марина10", "g@mail.com", "1"),
            PeopleModel(14, "Марина10", "g@mail.com", "1"),
            PeopleModel(15, "Марина10", "g@mail.com", "1"),
            PeopleModel(16, "Марина10", "g@mail.com", "1"),
            PeopleModel(17, "Марина10", "g@mail.com", "1"),
            PeopleModel(18, "Марина10", "g@mail.com", "1"),
            PeopleModel(19, "Марина10", "g@mail.com", "1"),
            PeopleModel(20, "Марина10", "g@mail.com", "1"),
            PeopleModel(21, "Марина10", "g@mail.com", "1"),
        )
    }

}