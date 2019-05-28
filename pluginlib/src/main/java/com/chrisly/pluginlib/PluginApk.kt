package com.chrisly.pluginlib

import android.content.pm.PackageInfo
import android.content.res.AssetManager
import android.content.res.Resources
import dalvik.system.DexClassLoader

/**
 * @author big insect
 * @date 2019/5/28.
 */
class PluginApk(classLoader: DexClassLoader, resources: Resources, packageInfo: PackageInfo, assetManager: AssetManager) {
    //用于加载插件apk
    var mClassLoader: DexClassLoader  = classLoader
    var mResources: Resources = resources
    var mPackageInfo: PackageInfo = packageInfo
    var mAssetManager: AssetManager = assetManager
}