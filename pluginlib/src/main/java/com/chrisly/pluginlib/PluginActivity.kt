package com.chrisly.pluginlib

import android.app.Activity
import android.os.Bundle
import com.chrisly.pluginlib.IPlugin.Companion

/**
 * 插件apk中activity的base类
 * @author big insect
 * @date 2019/5/28.
 */
open class PluginActivity: Activity(), IPlugin {

    //默认是内部跳转
    private var mFrom = Companion.FROM_INTERNAL
    private lateinit var mProxyActivity: Activity

    override fun attach(proxyActivity: Activity) {
        this.mProxyActivity = proxyActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState != null){
            mFrom = savedInstanceState.getInt("FROM")
        }
        if (mFrom == Companion.FROM_INTERNAL) {
            super.onCreate(savedInstanceState)
            mProxyActivity = this
        }
    }

    override fun setContentView(layoutResID: Int) {
        if (mFrom == Companion.FROM_INTERNAL) {
            super.setContentView(layoutResID)
        }else{
            mProxyActivity.setContentView(layoutResID)
        }
    }
}