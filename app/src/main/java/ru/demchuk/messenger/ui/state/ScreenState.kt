package ru.demchuk.messenger.ui.state

import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream

sealed class ScreenState {

    object Error : ScreenState()

    object Loading : ScreenState()

    class Data(val list: List<Stream>) : ScreenState()

    object Init : ScreenState()
}