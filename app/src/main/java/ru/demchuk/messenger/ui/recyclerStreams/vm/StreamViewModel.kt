package ru.demchuk.messenger.ui.recyclerStreams.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.demchuk.messenger.stubTopicList
import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem
import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.topic.Topic

class StreamViewModel : ViewModel() {

    var listFilterTopic = MutableLiveData<List<Topic>?>()

    fun filterList(stream: Stream) {
        listFilterTopic.value = stubTopicList.filter { topic ->
            topic.stream == stream.name
        }
    }

    fun clearList(list: List<DelegateItem>, stream: Stream) {
        val newFilterList = listFilterTopic.value?.toMutableSet()
        list.forEach { delegate ->
            if (delegate.content() as? Topic != null) {
                val topicOld = delegate.content() as Topic
                if (topicOld.stream != stream.name) {
                    newFilterList?.add(topicOld)
                }
                if (stream.press) {
                    if (topicOld.stream == stream.name) {
                        newFilterList?.remove(topicOld)
                    }
                }
            }
        }
        listFilterTopic.value = newFilterList?.toMutableList()
    }

}