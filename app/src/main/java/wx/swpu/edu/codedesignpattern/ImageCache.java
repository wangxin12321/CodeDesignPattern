package wx.swpu.edu.codedesignpattern;

import android.graphics.Bitmap;


/**
 * date:2019.10.29
 * author：Wx
 * 图片缓存接口
 */
public interface ImageCache {


    public Bitmap get(String url);

    public void put(String url,Bitmap bitmap);

}

