package ru.demchuk.messenger.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.demchuk.messenger.R
import ru.demchuk.messenger.databinding.FragmentStreamsBinding
import ru.demchuk.messenger.ui.recyclerStreams.MainAdapterStream
import ru.demchuk.messenger.ui.recyclerStreams.concatenateWithStream
import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.stream.StreamAdapter
import ru.demchuk.messenger.ui.recyclerStreams.stream.StreamDelegate
import ru.demchuk.messenger.ui.recyclerStreams.toDelegateList
import ru.demchuk.messenger.ui.recyclerStreams.topic.Topic
import ru.demchuk.messenger.ui.recyclerStreams.topic.TopicAdapter
import ru.demchuk.messenger.ui.recyclerStreams.topic.TopicDelegate


class Streams : Fragment() {

    private lateinit var binding: FragmentStreamsBinding
    private val adapter: MainAdapterStream by lazy { MainAdapterStream() }

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
        binding.recyclerStream.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //binding.recyclerStream.addItemDecoration(StickyHeaderItemDecoration())
        adapter.submitList(stubStreamList.toDelegateList())

    }

    companion object {

        const val TAG = "DelegatesFragment TAG"

        private const val Stream_1 = "Stream_1"
        private const val Stream_2 = "Stream_2"
        private const val Stream_3 = "Stream_3"
        private const val Stream_4 = "Stream_4"
        private const val Stream_5 = "Stream_5"

        private val stubStreamList = listOf(
            Stream(1, Stream_1),
            Stream(2, Stream_2),
            Stream(3, Stream_3),
            Stream(4, Stream_4),
            Stream(5, Stream_5),
        )

        private val stubTopicList = listOf(
            Topic(1, "Topic_1", Stream_1),
            Topic(2, "Topic_2", Stream_1),
            Topic(3, "Topic_3", Stream_2),
            Topic(4, "Topic_4", Stream_2),
            Topic(5, "Topic_5", Stream_3),
            Topic(6, "Topic_6", Stream_3),
            Topic(7, "Topic_7", Stream_4),
            Topic(8, "Topic_8", Stream_4),
            Topic(9, "Topic_9", Stream_5),
            Topic(10, "Topic_10", Stream_5)
        )
    }

    private val lambdaForStream = { stream: Stream ->
        var filterTopicList = listOf<Topic>()
        if (!stream.press) {
            filterTopicList = stubTopicList.filter { topic ->
                topic.stream == stream.name
            }
        }
        filterTopicList = clearTopicList(stream, filterTopicList)
        adapter.submitList(
            filterTopicList.concatenateWithStream(stubStreamList).toList()
        )
    }

    private fun clearTopicList(stream: Stream, filterTopicList: List<Topic>) : List<Topic> {
        val newFilterList = filterTopicList.toMutableList()
        adapter.currentList.forEach { delegate ->
            if (delegate.content() as? Topic != null) {
                val topicOld = delegate.content() as Topic
                if (topicOld.stream != stream.name) {
                    newFilterList.add(topicOld)
                }
            }
        }
        return newFilterList
    }


}