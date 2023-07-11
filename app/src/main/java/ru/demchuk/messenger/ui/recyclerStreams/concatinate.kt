package ru.demchuk.messenger.ui.recyclerStreams

import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem
import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.stream.StreamDelegate
import ru.demchuk.messenger.ui.recyclerStreams.topic.Topic
import ru.demchuk.messenger.ui.recyclerStreams.topic.TopicDelegate

fun List<Topic>.concatenateWithStream(streams : List<Stream>) :List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    streams.forEach { stream ->

        delegateItemList.add(
           StreamDelegate(id = stream.id, stream = stream)
        )

        val streamName = stream.name
        val allTopicStream = this.filter { topic ->
            topic.stream == streamName
        }
        allTopicStream.forEach { topic ->
            delegateItemList.add(
                TopicDelegate(
                    id = topic.id,
                    topic = topic,
                )
            )
        }
    }
    return delegateItemList

}