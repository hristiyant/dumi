package com.dumi.event.eventtypes

import com.dumi.event.LiveEvent
import com.dumi.event.enums.Navigation

class ScreenNavigationEvent(val navigation: Navigation) : LiveEvent()