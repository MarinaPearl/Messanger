package ru.demchuk.messenger.ui.recyclerStreams

import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.stream.StreamDelegate

fun List<Stream>.toDelegateList(): MutableList<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    this.forEach { stream ->

        delegateItemList.add(
            StreamDelegate(id = stream.id, stream = stream)
        )
    }
    return delegateItemList
}