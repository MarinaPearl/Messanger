package ru.demchuk.messenger.ui.dialogue.message

import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem

class MessageDelegate(private val id : Int, private  val messageModel : MessageModel):
    DelegateItem {
    override fun content(): Any = messageModel

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as MessageDelegate).content() == content()
    }
}