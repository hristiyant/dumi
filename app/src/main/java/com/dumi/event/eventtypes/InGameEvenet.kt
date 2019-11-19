package com.dumi.event.eventtypes

import com.dumi.event.LiveEvent
import com.dumi.event.enums.InGame

class InGameEvent(val inGameEnum: InGame) : LiveEvent()