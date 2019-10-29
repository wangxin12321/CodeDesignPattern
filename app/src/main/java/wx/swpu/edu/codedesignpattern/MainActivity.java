package wx.swpu.edu.codedesignpattern;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageLoader imageLoader;
    private String url="https://p0.ssl.qhimgs1.com/bdr/800__/t0187f7f6d0515b56ae.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.iv);
        //使用SD卡缓存
        imageLoader.setUseDiskCache(true);
        //不适用SD卡缓存
        //imageLoader.setUseDiskCache(false);
        imageLoader.displayImage(url,imageView);
    }
}
