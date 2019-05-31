package com.chrisly.plugin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chrisly.pluginlib.PluginActivity

class RealPluginActivity : PluginActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)
    }
}
