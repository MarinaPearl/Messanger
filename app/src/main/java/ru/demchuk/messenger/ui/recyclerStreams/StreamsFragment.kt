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
import ru.demchuk.messenger.data.repository.UserRequestSubscribedStreams
import ru.demchuk.messenger.databinding.FragmentStreamsBinding
import ru.demchuk.messenger.stubStreamList
import ru.demchuk.messenger.ui.adapterDelegate.MainAdapterDelegate
import ru.demchuk.messenger.ui.recyclerStreams.elm.Effect
import ru.demchuk.messenger.ui.recyclerStreams.elm.Event
import ru.demchuk.messenger.ui.recyclerStreams.elm.State
import ru.demchuk.messenger.ui.recyclerStreams.elm.StoreFactory
import ru.demchuk.messenger.ui.recyclerStreams.repository.UserRequestRepository
import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.stream.StreamAdapter
import ru.demchuk.messenger.ui.recyclerStreams.topic.TopicAdapter
import ru.demchuk.messenger.ui.recyclerStreams.use_case.UseCaseUserRequestAllSubscribed
import ru.demchuk.messenger.ui.recyclerStreams.use_case.UserRequestUseCase
import ru.demchuk.messenger.ui.recyclerStreams.use_case.model.StreamModelUseCase
import ru.demchuk.messenger.ui.recyclerStreams.vm.StreamViewModel
import ru.demchuk.messenger.ui.state.ScreenState
import vivid.money.elmslie.android.base.ElmFragment
import vivid.money.elmslie.core.store.Store


@SuppressLint("ResourceAsColor")
class StreamsFragment : ElmFragment<Event, Effect, State>() {

    private lateinit var binding: FragmentStreamsBinding
    private val adapter: MainAdapterDelegate by lazy { MainAdapterDelegate() }
    private val viewModel: StreamViewModel by viewModels()
    private var actualStateStreamList = stubStreamList
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
    private val repoSubscribedStreams : UserRequestRepository = UserRequestSubscribedStreams()
    private val useCaseSubscribedStreams : UserRequestUseCase = UseCaseUserRequestAllSubscribed(
        repoSubscribedStreams)

    override val initEvent: Event = Event.Ui.LoadingSubscribedStreams(useCaseSubscribedStreams)

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
            addDelegate(StreamAdapter(lambdaForStream))
            addDelegate(TopicAdapter(createActionForTopic()))
        }
        binding.recyclerStream.adapter = adapter
        binding.recyclerStream.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        //binding.recyclerStream.addItemDecoration(StickyHeaderItemDecoration())
//        if (viewModel.listFilterTopic.value?.isEmpty() == null) {
//            lifecycleScope.launch {
//                viewModel.loadStreams()
//            }
//        }
//        viewModel.listFilterTopic.observe(this) {
//            adapter.submitList(
//                it?.concatenateWithStream(actualStateStreamList)?.toList()
//            )
//        }
        binding.search.editTextSearch.addTextChangedListener {
            lifecycleScope.launch {
                it?.let { query ->
                    viewModel.searchQueryState.emit(query.toString())
                }
            }

        }
//        viewModel.searchState
//            .flowWithLifecycle(lifecycle)
//            .onEach(::setScreen)
//            .launchIn(lifecycleScope)
//        binding.subscribedStreamButton.setOnClickListener { store.accept(Event.Ui.LoadingSubscribedStreams) }
    }

    override fun createStore(): Store<Event, Effect, State> =
        StoreFactory.provide()

    override fun render(state: State) {
        binding.apply {
            shimmer.isVisible = state.shimmerShow
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

    private val lambdaForStream = { stream: Stream ->
        binding.search.editTextSearch.clearFocus()
        if (!stream.press) {
            viewModel.filterList(stream)
        }
        viewModel.clearList(adapter.currentList, stream)
    }

    private fun createActionForTopic(): () -> Unit = {
        val activity = activity as MainActivity
        activity.router.navigateTo(MainActivity.Screens.Message())
    }


    private fun setScreen(state: ScreenState) {
        when (state) {
            ScreenState.Error -> {
                binding.apply {
                    recyclerStream.isVisible = false
                    shimmer.isVisible = false
                }
                snackBar.show()
            }
            ScreenState.Loading -> {
                binding.apply {
                    recyclerStream.isVisible = false
                    shimmer.isVisible = true
                }
            }
            is ScreenState.Data -> {
                binding.apply {
                    recyclerStream.isVisible = true
                    shimmer.isVisible = false
                }
                actualStateStreamList = state.list
                if (viewModel.listFilterTopic.value?.isEmpty() == null) {
                    adapter.submitList(state.list.toDelegateList())
                } else {
                    adapter.submitList(
                        viewModel.listFilterTopic.value?.concatenateWithStream(actualStateStreamList)
                            ?.toList()
                    )
                }
            }
            ScreenState.Init -> {}
        }
    }


}