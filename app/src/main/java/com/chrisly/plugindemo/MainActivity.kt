package com.chrisly.plugindemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chrisly.pluginlib.PluginManager
import com.chrisly.pluginlib.ProxyActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PluginManager.getInstance().init(this)

        btn_download.setOnClickListener {
            val apkPath = Utils.copyAssetAndWrite(this, "plug.apk")
            PluginManager.getInstance().loadApk(apkPath)
        }

        jump.setOnClickListener{
            val intent = Intent(this, ProxyActivity::class.java)
            intent.putExtra("className", "com.chrisly.plugin.RealPluginActivity")
            startActivity(intent)
        }
    }
}
