package ru.demchuk.messenger.ui.recyclerStreams

import android.util.Log
import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.stream.StreamDelegate
import ru.demchuk.messenger.ui.recyclerStreams.topic.Topic
import ru.demchuk.messenger.ui.recyclerStreams.topic.TopicDelegate

fun List<Topic>.concatenateWithStream(streams : List<Stream>) :List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    streams.forEach { stream ->

        // Сразу добавляем в список дату
        delegateItemList.add(
           StreamDelegate(id = stream.id, stream = stream)
        )

        val streamName = stream.name
        // Выбираем покупки по дате
        val allTopicStream = this.filter { topic ->
            topic.stream == streamName
        }
        Log.d("bbbbbbkkkkkkkkkkkkkkkkkkkkk", allTopicStream[0].stream)
        // Добавляем эти покупки
        allTopicStream.forEach { topic ->
            delegateItemList.add(
                TopicDelegate(
                    id = topic.id,
                    topic = topic,
                )
            )
        }
        Log.d("bbbbbb", delegateItemList[0].id().toString())
    }
    return delegateItemList

}