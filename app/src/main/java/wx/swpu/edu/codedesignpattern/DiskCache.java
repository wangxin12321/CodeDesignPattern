package wx.swpu.edu.codedesignpattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * author:Wx
 * date:2019.10.29
 */
public class DiskCache {
    static String cacheDir="sdcard/cache/";

    /**
     * 从缓存中获得图片
     * @param url
     * @return
     */
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir+url);
    }


    /**
     * 将图片缓存到内存中
     * @param url
     * @param bmp
     */
    public void put(String url,Bitmap bmp){
        FileOutputStream fileOutputStream=null;
        try{
            fileOutputStream=new FileOutputStream(cacheDir+url);
            bmp.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }finally {
            if(fileOutputStream!=null){
                try{
                    fileOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }


}
