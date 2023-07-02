package ru.demchuk.messenger.ui.recyclerStreams

interface DelegateItem {
    fun content(): Any
    fun id(): Int
    fun compareToOther(other: DelegateItem): Boolean
}