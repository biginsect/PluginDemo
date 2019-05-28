package com.chrisly.pluginlib

import android.app.Activity
import android.os.Bundle

/**
 * 约束接口，控制插件apk中activity的生命周期
 * 传递上下文context
 * @author big insect
 * @date 2019/5/28.
 */
interface IPlugin {
    companion object{
        const val FROM_INTERNAL = 0
        const val FROM_EXTERNAL = 1
    }

    fun attach(proxyActivity: Activity)

    fun onCreate(saveInstanceState: Bundle?)
}