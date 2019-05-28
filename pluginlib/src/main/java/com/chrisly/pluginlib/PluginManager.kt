package com.chrisly.pluginlib

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import dalvik.system.DexClassLoader

/**
 * 创建ClassLoader加载插件apk中的代码
 * 创建Resources 加载插件apk中的资源
 * @author big insect
 * @date 2019/5/28.
 */
@SuppressLint("StaticFieldLeak")
object PluginManager {
    private lateinit var mContext: Context
    lateinit var mPluginApk: PluginApk
    private set

    fun init(context: Context){
        this.mContext = context
    }

    fun loadApk(apkPath: String){
        val packageInfo = mContext.packageManager.getPackageArchiveInfo(apkPath,
            PackageManager.GET_ACTIVITIES or PackageManager.GET_SERVICES) ?: return

        val classLoader = createClassLoader(apkPath)
        val assetManager = createAssetManager(apkPath)
        val resources = createResources(assetManager)
        mPluginApk = PluginApk(classLoader, resources, packageInfo, assetManager)
    }

    private fun createClassLoader(apkPath: String): DexClassLoader {
        val file = mContext.getDir("dex", Context.MODE_PRIVATE)
        return DexClassLoader(apkPath, file.absolutePath, null, mContext.classLoader)
    }

    private fun createAssetManager(apkPath: String): AssetManager {
        val am = AssetManager::class.java.newInstance()
        val method = AssetManager::class.java.getDeclaredMethod("addAssetPath", String::class.java)
        method.invoke(am, apkPath)

        return am
    }

    private fun createResources(assetManager: AssetManager): Resources {
        val resources = mContext.resources

        return Resources(assetManager, resources.displayMetrics, resources.configuration)
    }
}