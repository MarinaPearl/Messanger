package ru.demchuk.messenger.ui.recyclerStreams.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.demchuk.messenger.stubStreamList
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


    init {
        subscribeToSearchQueryChanges()
    }

    private fun subscribeToSearchQueryChanges() {
        searchQueryState
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .debounce(500L)
            .flatMapLatest { flow { emit(search(it)) } }
            .onEach { _searchState.value = it }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)

    }


    private suspend fun search(request: String): ScreenState {
        _searchState.emit(ScreenState.Loading)
        var listResult = listOf<Stream>()
        coroutineScope {
            launch(Dispatchers.IO) {
                delay(3000L)
                listResult = searchList(request)
            }
        }
        return if (listResult.isNotEmpty()) {
            ScreenState.Data(listResult)
        } else ScreenState.Error

    }

    fun filterList(stream: Stream) {
        listFilterTopic.value = stubTopicList.filter { topic ->
            topic.stream == stream.name
        }
    }

    private suspend fun searchList(request: String): List<Stream> {
        val resulListSearch: MutableList<Stream> = mutableListOf()
        delay(500L)
        stubStreamList.forEach {
            if (it.name == request) {
                resulListSearch.add(it)
            }
        }
        return resulListSearch
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