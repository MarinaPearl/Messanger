package ru.demchuk.messenger.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
            addDelegate(TopicAdapter())
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
    }

    private val lambdaForStream = { stream: Stream ->
        if (!stream.press) {
            viewModel.filterList(stream)
        }
        viewModel.clearList(adapter.currentList, stream)
    }


}