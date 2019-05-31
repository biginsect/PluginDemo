package com.chrisly.plugindemo

import android.content.Context
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

/**
 * @author big insect
 * @date 2019/5/28.
 */
object Utils {

    /*
    * 将assets目录下的文件拷贝到app缓存目录下
    * */
    fun copyAssetAndWrite(context: Context, fileName: String): String{
        val cacheDir = context.cacheDir
        if (!cacheDir.exists()){
            cacheDir.mkdirs()
        }
        val outputFile = File(cacheDir, fileName)
        if (!outputFile.exists()){
            val flag = outputFile.createNewFile()
            if (flag){
                val input = context.assets.open(fileName)
                val output = FileOutputStream(outputFile)
                val len = input.available()
                val buffer = ByteArray(len) {0}
                var byteCount: Int
                while (true){
                    byteCount = input.read(buffer)
                    if (byteCount == -1){
                        break
                    }
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
                input.close()
                Toast.makeText(context, "download successful", Toast.LENGTH_SHORT).show()

                return outputFile.absolutePath
            }else{
                Toast.makeText(context, "file exist!", Toast.LENGTH_SHORT).show()
                return outputFile.absolutePath
            }
        }

        return ""
    }
}