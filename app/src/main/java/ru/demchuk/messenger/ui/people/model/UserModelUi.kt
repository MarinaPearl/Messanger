package ru.demchuk.messenger.ui.people.model

data class UserModelUi(
    val id: Int,
    var name: String,
    var active : Boolean,
    var email: String,
    var avatar: String?
) {}