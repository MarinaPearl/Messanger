package ru.demchuk.messenger.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.demchuk.messenger.MainActivity
import ru.demchuk.messenger.databinding.FragmentStreamsBinding
import ru.demchuk.messenger.stubStreamList
import ru.demchuk.messenger.ui.adapterDelegate.MainAdapterDelegate
import ru.demchuk.messenger.ui.recyclerStreams.concatenateWithStream
import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.stream.StreamAdapter
import ru.demchuk.messenger.ui.recyclerStreams.toDelegateList
import ru.demchuk.messenger.ui.recyclerStreams.topic.TopicAdapter
import ru.demchuk.messenger.ui.recyclerStreams.vm.StreamViewModel


class StreamsFragment : Fragment() {

    private lateinit var binding: FragmentStreamsBinding
    private val adapter: MainAdapterDelegate by lazy { MainAdapterDelegate() }
    private val viewModel: StreamViewModel by viewModels()

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
        adapter.submitList(stubStreamList.toDelegateList())
        viewModel.listFilterTopic.observe(this) {
            adapter.submitList(
                it?.concatenateWithStream(stubStreamList)?.toList()
            )
        }
        binding.search.editTextSearch.addTextChangedListener { it ->
            lifecycleScope.launch {
                viewModel.shared.emit(it.toString())
            }
        }


        viewModel.state
            .flowWithLifecycle(lifecycle)
            .launchIn(lifecycleScope)
    }




    private val lambdaForStream = { stream: Stream ->
        if (!stream.press) {
            viewModel.filterList(stream)
        }
        viewModel.clearList(adapter.currentList, stream)
    }

    private fun createActionForTopic(): () -> Unit = {
        val activity = activity as MainActivity
        activity.router.navigateTo(MainActivity.Screens.Message())
    }


}