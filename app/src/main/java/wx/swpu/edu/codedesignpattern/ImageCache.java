package wx.swpu.edu.codedesignpattern;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * date:2019.10.29
 * author：Wx
 * 本地缓存
 */
public class ImageCache {
    //图片LRU加载
    LruCache<String, Bitmap> mImageCache;

    public ImageCache(){
        initImageCache();
    }

    private void initImageCache() {
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

    public void put(String url,Bitmap bitmap){
        mImageCache.put(url,bitmap);
    }

    public Bitmap get(String url){
        return mImageCache.get(url);
    }

}

