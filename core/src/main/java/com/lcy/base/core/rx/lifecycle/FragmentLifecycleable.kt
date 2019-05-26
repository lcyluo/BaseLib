package com.lcy.base.core.rx.lifecycle

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.FragmentEvent

interface FragmentLifecycleable : LifecycleProvider<FragmentEvent>