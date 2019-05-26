package com.lcy.base.core.common

/**
 * 定义状态栏 系统类型
 *
 * @author lcy
 */
object StatusBarMode {

    /** 低于Android 21版本 **/
    const val KITKAT = 0
    /** 大于等于Android 23版本 **/
    const val M = 1
    /** 小米 **/
    const val MIUI = 2
    /** 魅族 **/
    const val FLYME = 3
    /** 其他 **/
    const val OTHERWISE = 4
}