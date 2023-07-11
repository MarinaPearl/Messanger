package ru.demchuk.messenger.ui.dialogue

import ru.demchuk.messenger.ui.adapterDelegate.DelegateItem
import ru.demchuk.messenger.ui.dialogue.data.DateDelegate
import ru.demchuk.messenger.ui.dialogue.data.DateModel
import ru.demchuk.messenger.ui.dialogue.message.MessageDelegate
import ru.demchuk.messenger.ui.dialogue.message.MessageModel

fun List<MessageModel>.concatenateWithDate(dates: List<DateModel>): List<DelegateItem> {
    val delegateItemList: MutableList<DelegateItem> = mutableListOf()

    dates.forEach { dateModel ->

        delegateItemList.add(
            DateDelegate(id = dateModel.id, dateModel = dateModel)
        )

        val date = dateModel.date

        val allDayMessage = this.filter { expense ->
            expense.date == date
        }

        allDayMessage.forEach { model ->
            delegateItemList.add(
                MessageDelegate(
                    id = model.idMessage,
                    messageModel = model,
                )
            )
        }
    }
    return delegateItemList
}