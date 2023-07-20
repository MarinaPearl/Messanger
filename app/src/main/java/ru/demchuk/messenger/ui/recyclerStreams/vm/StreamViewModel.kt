package ru.demchuk.messenger.ui.recyclerStreams.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.demchuk.messenger.stubTopicList
import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem
import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.topic.Topic
import ru.demchuk.messenger.ui.state.ScreenState

class StreamViewModel : ViewModel() {

    var listFilterTopic = MutableLiveData<List<Topic>?>()

    private val _searchState: MutableStateFlow<ScreenState> =
        MutableStateFlow(ScreenState.Init)
    val searchState: StateFlow<ScreenState> get() = _searchState.asStateFlow()

    val searchQueryState: MutableSharedFlow<String> = MutableSharedFlow()

    val state = MutableStateFlow("a")
    val shared = MutableSharedFlow<String>()

    init {
        shared
            .filter { it.isNotEmpty() }
            .onEach {state.emit(it)}
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)

    }


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