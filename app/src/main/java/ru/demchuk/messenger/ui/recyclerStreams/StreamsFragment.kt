package ru.demchuk.messenger.ui.recyclerStreams

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.demchuk.messenger.MainActivity
import ru.demchuk.messenger.R
import ru.demchuk.messenger.databinding.FragmentStreamsBinding
import ru.demchuk.messenger.di.StreamsDI
import ru.demchuk.messenger.ui.adapterDelegate.MainAdapterDelegate
import ru.demchuk.messenger.ui.recyclerStreams.elm.Effect
import ru.demchuk.messenger.ui.recyclerStreams.elm.Event
import ru.demchuk.messenger.ui.recyclerStreams.elm.State
import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.stream.StreamAdapter
import ru.demchuk.messenger.ui.recyclerStreams.topic.TopicAdapter
import ru.demchuk.messenger.domain.useCase.streams.model.StreamModelUseCase
import ru.demchuk.messenger.ui.recyclerStreams.vm.StreamViewModel
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.android.storeholder.LifecycleAwareStoreHolder
import vivid.money.elmslie.android.storeholder.StoreHolder
import vivid.money.elmslie.core.store.Store


@SuppressLint("ResourceAsColor")
class StreamsFragment : ElmFragment<Event, Effect, State>() {

    private lateinit var binding: FragmentStreamsBinding
    private val adapter: MainAdapterDelegate by lazy { MainAdapterDelegate() }

    private val snackBar: Snackbar by lazy {
        val snackBar = Snackbar.make(
            binding.root,
            "Ошибка при загрузке стримов",
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
        binding = FragmentStreamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.apply {
            addDelegate(StreamAdapter())
            addDelegate(TopicAdapter(createActionForTopic()))
        }
        binding.search.editTextSearch.hint = activity?.getString(R.string.search)
        binding.recyclerStream.adapter = adapter
        binding.recyclerStream.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.search.editTextSearch.addTextChangedListener {
            lifecycleScope.launch {
                it?.let { query ->
                    //viewModel.searchQueryState.emit(query.toString())
                    store.accept(Event.Ui.SearchStreams(query.toString()))
                }
            }
        }
        binding.allStreamButton.setOnClickListener {
            clearFocusEditText()
            store.accept(Event.Ui.LoadingAllStreams)
        }
        binding.subscribedStreamButton.setOnClickListener {
            clearFocusEditText()
            store.accept(Event.Ui.LoadingSubscribedStreams)
        }
    }

    override val storeHolder: StoreHolder<Event, Effect, State> = LifecycleAwareStoreHolder(lifecycle) {
        StreamsDI.INSTANCE.storeFactory.provide()
    }




    override fun render(state: State) {
        binding.apply {
            progressBar.isVisible = state.progressBarShow
            recyclerStream.isVisible = state.recyclerViewShow
            if (state.errorShow) {
                snackBar.show()
            }
            if (state.listStreams != null) {
                adapter.submitList(state.listStreams.toListStreamOnUi().toDelegateList())
            }
        }
    }

    private fun List<StreamModelUseCase>.toListStreamOnUi(): List<Stream> {
        val listStreamUi = mutableListOf<Stream>()
        this.forEach {
            val streamUi = Stream(id = it.streamId, name = it.name)
            listStreamUi.add(streamUi)
        }
        return listStreamUi
    }

//    private val lambdaForStream = { stream: Stream ->
//        binding.search.editTextSearch.clearFocus()
//        if (!stream.press) {
//            viewModel.filterList(stream)
//        }
//        viewModel.clearList(adapter.currentList, stream)
//    }

    private fun createActionForTopic(): () -> Unit = {
        val activity = activity as MainActivity
        activity.router.navigateTo(MainActivity.Screens.Message())
    }

    private fun clearFocusEditText() {
        binding.search.editTextSearch.text?.clear()
        binding.search.textField.clearFocus()
    }

}