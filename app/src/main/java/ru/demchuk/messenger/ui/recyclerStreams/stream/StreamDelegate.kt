package ru.demchuk.messenger.ui.recyclerStreams.stream

import ru.demchuk.messenger.ui.recyclerStreams.DelegateItem

class StreamDelegate(private val id: Int, private val stream: Stream) : DelegateItem {
    override fun content(): Any = stream

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as StreamDelegate).content() == content()
    }
}