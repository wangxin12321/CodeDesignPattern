package wx.swpu.edu.codedesignpattern;

import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.View;

/**
 * 内存缓存
 * author:Wx
 * date:2019.10.29
 */
public class MemoryCache implements ImageCache{

    //图片LRU加载
    LruCache<String, Bitmap> mImageCache;

    private LruCache<String,Bitmap> mMemeryCache;

    public MemoryCache(){
        //计算可使用的最大内存
        final int maxMemoery=(int) (Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize=maxMemoery/4;
        mImageCache=new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }

    @Override
    public Bitmap get(String url) {
        return null;
    }

    @Override
    public void put(String url, Bitmap bitmap) {

    }
}
