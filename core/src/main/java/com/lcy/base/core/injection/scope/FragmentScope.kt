package com.lcy.base.core.injection.scope

import java.lang.annotation.Retention
import javax.inject.Scope
import java.lang.annotation.RetentionPolicy.RUNTIME

/**
 * Activity级别 作用域
 */
@Scope
@Retention(RUNTIME)
annotation class FragmentScope