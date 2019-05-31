package com.chrisly.pluginlib

import android.app.Activity
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import java.lang.Exception

/**
 * 桥梁activity，用于管理插件apk中的activity以及主activity任务下发时进行任务的转发
 * @author big insect
 * @date 2019/5/28.
 */
class ProxyActivity: Activity() {
    private lateinit var mClassName: String
    private  var pluginApk: PluginApk? = null
    private lateinit var mIPlugin: IPlugin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mClassName = intent.getStringExtra("className")
        pluginApk = PluginManager.getInstance().getPluginApk()
        launchPluginActivity()
    }

    private fun launchPluginActivity(){
        if(pluginApk == null){
            Log.e("ProxyActivity--->","loading apk first！")
        }

        try {
            val clazz = pluginApk?.mClassLoader?.loadClass(mClassName)
            val obj = clazz?.newInstance()
            if (obj is IPlugin) {
                mIPlugin = obj
                mIPlugin.attach(this)
                //只处理外部来的信息
                val bundle = Bundle()
                bundle.putInt("FROM", IPlugin.FROM_EXTERNAL)
                mIPlugin.onCreate(bundle)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun getResources(): Resources {
        if (pluginApk != null){
            return pluginApk!!.mResources
        }
        return super.getResources()
    }

    override fun getAssets(): AssetManager {
        if(pluginApk != null){
            return pluginApk!!.mAssetManager
        }
        return super.getAssets()
    }

    override fun getClassLoader(): ClassLoader {
        if (pluginApk != null){
            return pluginApk!!.mClassLoader
        }
        return super.getClassLoader()
    }
}