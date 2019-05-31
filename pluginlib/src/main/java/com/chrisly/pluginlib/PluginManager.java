package com.chrisly.pluginlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import dalvik.system.DexClassLoader;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 创建ClassLoader加载插件apk中的代码
 * 创建Resources 加载插件apk中的资源
 * @author big insect
 * @date 2019/5/28.
 */
public class PluginManager {

    private Context mContext;
    private PluginApk mPluginApk;

    public static PluginManager getInstance(){
        return Holder.INSTANCE;
    }

    private PluginManager(){

    }

    public PluginApk getPluginApk() {
        return mPluginApk;
    }

    public void init(Context context){
        this.mContext = context;
    }

    public void loadApk(String apkPath){
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null){
            return;
        }

        DexClassLoader classLoader = createClassLoader(apkPath);
        AssetManager assetManager = createAssetManager(apkPath);
        Resources resources = createResources(assetManager);
        mPluginApk = new PluginApk(classLoader, resources, packageInfo, assetManager);
    }

    private DexClassLoader createClassLoader (String apkPath) {
        File file = mContext.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, file.getAbsolutePath(), null, mContext.getClassLoader());
    }

    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager am = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(am, apkPath);

            return am;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private Resources createResources (AssetManager assetManager) {
        Resources resources = mContext.getResources();

        return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    }

    private static class Holder{
        @SuppressLint("StaticFieldLeak")
        private static final PluginManager INSTANCE = new PluginManager();
    }
}
