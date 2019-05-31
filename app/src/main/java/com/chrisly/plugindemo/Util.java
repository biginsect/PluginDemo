package com.chrisly.plugindemo;

import android.content.Context;
import android.widget.Toast;

import java.io.*;

/**
 * @author big insect
 * @date 2019/5/28.
 */
public class Util {

    private Util(){

    }

    public static String copyAssetAndWrite(Context context, String fileName){
        try{
            File chacheDir = context.getCacheDir();
            if (!chacheDir.exists()){
                chacheDir.mkdirs();
            }
            File outputFile = new File(chacheDir, fileName);
            if (!outputFile.exists()){
                boolean flag = outputFile.createNewFile();
                if (flag){
                    InputStream in = context.getAssets().open(fileName);
                    FileOutputStream out = new FileOutputStream(outputFile);
                    byte[] buffer = new byte[in.available()];
                    int byteCount ;
                    while ((byteCount = in.read(buffer)) != -1){
                        out.write(buffer, 0, byteCount);
                    }
                    out.flush();
                    in.close();
                    out.close();
                    Toast.makeText(context, "download successful", Toast.LENGTH_SHORT).show();
                    return outputFile.getAbsolutePath();
                }else {
                    Toast.makeText(context, "file exist!", Toast.LENGTH_SHORT).show();
                    return outputFile.getAbsolutePath();
                }
            }
        }catch (FileNotFoundException e){

        }catch (IOException e){

        }

        return "";
    }

}
