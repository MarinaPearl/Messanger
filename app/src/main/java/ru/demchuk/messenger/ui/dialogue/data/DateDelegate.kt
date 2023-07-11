package ru.demchuk.messenger.ui.dialogue.data

import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem

class DateDelegate(private val id : Int, private val dateModel: DateModel): DelegateItem {
    override fun content(): Any = dateModel

    override fun id(): Int = id

    override fun compareToOther(other: DelegateItem): Boolean {
        return (other as DateDelegate).content() == content()
    }
}