package com.lcy.base.core.injection.scope

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.RUNTIME
import javax.inject.Scope

/**
 * 组件级别 作用域
 */
@Scope
@Retention(RUNTIME)
annotation class PerComponentScope
