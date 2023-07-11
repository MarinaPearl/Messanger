package ru.demchuk.messenger.ui.adapterDelegate

interface DelegateItem {
    fun content(): Any
    fun id(): Int
    fun compareToOther(other: DelegateItem): Boolean
}