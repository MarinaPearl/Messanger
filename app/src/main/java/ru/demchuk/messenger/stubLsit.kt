package ru.demchuk.messenger

import ru.demchuk.messenger.ui.recyclerStreams.stream.Stream
import ru.demchuk.messenger.ui.recyclerStreams.topic.Topic

private const val Stream_1 = "Stream_1"
private const val Stream_2 = "Stream_2"
private const val Stream_3 = "Stream_3"
private const val Stream_4 = "Stream_4"
private const val Stream_5 = "Stream_5"
private const val Stream_6 = "mama"

val stubStreamList = listOf(
    Stream(1, Stream_1),
    Stream(2, Stream_2),
    Stream(3, Stream_3),
    Stream(4, Stream_4),
    Stream(5, Stream_5),
    Stream(6, Stream_6),
)

val stubTopicList = listOf(
    Topic(1, "Topic_1", Stream_1),
    Topic(2, "Topic_2", Stream_1),
    Topic(3, "Topic_3", Stream_2),
    Topic(4, "Topic_4", Stream_2),
    Topic(5, "Topic_5", Stream_3),
    Topic(6, "Topic_6", Stream_3),
    Topic(7, "Topic_7", Stream_4),
    Topic(8, "Topic_8", Stream_4),
    Topic(9, "Topic_9", Stream_5),
    Topic(10, "Topic_10", Stream_5),
    Topic(11, "Topic_11", Stream_5),
    Topic(12, "Topic_12", Stream_5),
    Topic(13, "Topic_13", Stream_5),
)