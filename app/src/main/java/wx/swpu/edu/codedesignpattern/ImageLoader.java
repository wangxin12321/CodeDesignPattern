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
    //图片缓存
    ImageCache mImageCache=new MemoryCache();

    //线程池
    ExecutorService mExecutorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //注入缓存实现
    public void setImageCache(ImageCache cache){
        mImageCache=cache;
    }


    /**
     * 展示图片
     * @param imageUrl
     * @param imageView
     */
    public void displayImage(final String imageUrl,ImageView imageView) {
        Bitmap bitmap = mImageCache.get(imageUrl);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        submitLoadRequest(imageUrl,imageView);

    }

    private void submitLoadRequest(final String imageUrl, final ImageView imageView) {
        imageView.setTag(imageUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(imageUrl);
                if (bitmap == null) {
                    return;
                }
                if (imageView.getTag().equals(imageUrl)) {
                    imageView.setImageBitmap(bitmap);
                }
                mImageCache.put(imageUrl, bitmap);
            }
        });
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
