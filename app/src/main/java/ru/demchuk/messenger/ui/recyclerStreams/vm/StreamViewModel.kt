package ru.demchuk.messenger.ui.recyclerStreams.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
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
            .debounce(500L)
            .onEach { println(it) }
            .distinctUntilChanged()
            .flatMapLatest { flow { emit(search(it)) }}
            .onEach { _searchState.value = it }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    suspend fun loadStreams() {
        _searchState.emit(ScreenState.Loading)
        var listResult = listOf<Stream>()
        coroutineScope {
            launch(Dispatchers.IO) {
                delay(2000L)
                listResult = stubStreamList
            }
        }
        if (listResult.isNotEmpty()) {
            _searchState.value = ScreenState.Data(listResult)
        } else _searchState.value = ScreenState.Error
    }


    private suspend fun search(request: String): ScreenState {
        _searchState.emit(ScreenState.Loading)
        var listResult = listOf<Stream>()
        coroutineScope {
            launch(Dispatchers.IO) {
                delay(2000L)
                listResult = if (request.isNotEmpty()) {
                    searchList(request)
                } else stubStreamList
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

    private fun searchList(request: String): List<Stream> {
        val resulListSearch: MutableList<Stream> = mutableListOf()
        stubStreamList.forEach {
            val regex = request.toRegex(RegexOption.IGNORE_CASE)
            if (regex.containsMatchIn(it.name)) {
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