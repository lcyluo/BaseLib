package com.lcy.base.core.rx.lifecycle

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

interface ActivityLifecycleable : LifecycleProvider<ActivityEvent>