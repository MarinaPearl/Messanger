package ru.demchuk.messenger.ui.recyclerStreams.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.demchuk.messenger.stubTopicList
import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem
import ru.demchuk.messenger.ui.exception.runCatchingNonCancellation
import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.topic.Topic
import ru.demchuk.messenger.ui.state.ScreenState
import ru.demchuk.messenger.use_case.LoadStreams

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
            .debounce(500L)
            .onEach { println(it) }
            .distinctUntilChanged()
            .flatMapLatest { flow { emit(search(it)) } }
            .onEach { _searchState.value = it }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    suspend fun loadStreams() {
        _searchState.emit(ScreenState.Loading)
        val result = runCatchingNonCancellation {
            loadStreams { LoadStreams.loadAllStreams() }
        }.getOrNull()
        _searchState.value = if (result != null) {
            ScreenState.Data(result)
        } else {
            ScreenState.Error
        }

    }


    private suspend fun search(request: String): ScreenState {
        _searchState.emit(ScreenState.Loading)
        val result = runCatchingNonCancellation {
            loadStreams { LoadStreams.loadStreamsForRequest(request) }
        }.getOrNull()
        return result?.let { ScreenState.Data(it) }
            ?: ScreenState.Error
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