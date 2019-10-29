package wx.swpu.edu.codedesignpattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载类
 * date:2019.10.29
 * author:Wx
 */
public class ImageLoader {
    //内存缓存
    ImageCache mImageCache=new ImageCache();
    //SD卡缓存
    DiskCache mDiskCache=new DiskCache();
    //双缓存
    DoubleCache mDoubleCache=new DoubleCache();
    //是否使用SD卡缓存
    boolean isUseDiskCache=false;
    //使用双缓存
    boolean isUseDoubleCache=false;


    //线程池
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());




    /**
     * 展示图片
     * @param url
     * @param imageView
     */
    public void displayImage(final String url,final ImageView imageView){

        Bitmap bmp=null;
        if(isUseDoubleCache){
            bmp=mDoubleCache.get(url);
        }else if(isUseDiskCache){
            bmp=mDiskCache.get(url);
        }else {
            bmp=mImageCache.get(url);
        }

        //判断使用那种缓存
        if(bmp!=null){
            imageView.setImageBitmap(bmp);
        }
        //没有缓存则交给线程池进行下载
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap=downloadImage(url);
                if(bitmap==null){
                    return;
                }
                if(imageView.getTag().equals(url)){
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(url,bitmap);
            }
        });
    }

    public void setUseDiskCache(boolean useDiskCache){
        isUseDiskCache=useDiskCache;
    }

    public void setUseDoubleCache(boolean useDoubleCache){
        isUseDoubleCache=useDoubleCache;
    }


    /**
     * @param imageUrl
     * @return
     * 网络图片加载
     */
    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap=null;
        try{
            URL url=new URL(imageUrl);
            final HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            bitmap= BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

}
