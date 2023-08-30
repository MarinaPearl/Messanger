package ru.demchuk.messenger.ui.people

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.demchuk.messenger.R
import ru.demchuk.messenger.databinding.FragmentPeopleBinding
import ru.demchuk.messenger.di.PeopleDI
import ru.demchuk.messenger.di.StreamsDI
import ru.demchuk.messenger.ui.people.elm.Effect
import ru.demchuk.messenger.ui.people.elm.Event
import ru.demchuk.messenger.ui.people.elm.State
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import vivid.money.elmslie.android.storeholder.StoreHolder
import vivid.money.elmslie.core.store.Store


@SuppressLint("ResourceAsColor")
class PeopleFragment() : ElmFragment<Event, Effect, State>() {

    private lateinit var binding: FragmentPeopleBinding
    private val adapter: Adapter by lazy { Adapter() }
    private val snackBar: Snackbar by lazy {
        val snackBar = Snackbar.make(
            binding.root,
            "Ошибка при загрузке пользователей",
            Snackbar.LENGTH_LONG
        )
        val sbView: View = snackBar.view
        sbView.setBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.blue_green
            )
        )
        snackBar.setActionTextColor(R.color.grey_black)
    }

    override val initEvent: Event = Event.Ui.Init


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPeopleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.search.editTextSearch.hint = activity?.getString(R.string.users)
        binding.recyclerPeople.adapter = adapter
        binding.recyclerPeople.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    override val storeHolder: StoreHolder<Event, Effect, State> = LifecycleAwareStoreHolder(lifecycle) {
        PeopleDI.INSTANCE.storeFactory.provide()}


    override fun render(state: State) {
        binding.apply {
            progressBar.isVisible = state.progressBarShow
            recyclerPeople.isVisible = state.recyclerViewShow
            if (state.errorShow) {
                snackBar.show()
            }
            if (state.listUsers != null) {
                adapter.submitList(state.listUsers)
            }
        }
    }

}