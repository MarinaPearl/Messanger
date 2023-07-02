package ru.demchuk.messenger.ui.recyclerStreams.topic

import ru.demchuk.messenger.ui.recyclerStreams.DelegateItem

class TopicDelegate(private val id: Int, private val topic: Topic) : DelegateItem {

    override fun content(): Any = topic

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
      return (other as TopicDelegate).content() == content()
    }
}