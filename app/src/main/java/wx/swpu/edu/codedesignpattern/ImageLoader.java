package wx.swpu.edu.codedesignpattern;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader {
    //内存缓存
    ImageCache mImageCache=new ImageCache();
    //SD卡缓存
    DiskCache mDiskCache=new DiskCache();
    //是否使用SD卡缓存

    boolean isUseDiskCache=false;

    //线程池
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());




    /**
     * 展示图片
     * @param url
     * @param imageView
     */
    public void displayImage(final String url,final ImageView imageView){
        //判断使用那种缓存
        Bitmap bitmap=isUseDiskCache?mDiskCache.get(url):mImageCache.get(url);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            return;
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
