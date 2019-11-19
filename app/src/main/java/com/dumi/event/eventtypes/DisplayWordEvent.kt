package com.dumi.event.eventtypes

import com.dumi.adapter.item.WordItem
import com.dumi.event.LiveEvent

class DisplayWordEvent(val word: WordItem) : LiveEvent()