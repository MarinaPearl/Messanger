package ru.demchuk.messenger.ui.dialogue.message

data class MessageModel(
    var idMessage: Int,
    var idUser: Int,
    var avatar: String,
    var name: String,
    var message: String,
    var date : String
)