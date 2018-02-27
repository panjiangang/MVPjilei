package panjiangang.bwie.com.mvpjilei.utils;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by lenovo on 2017/12/27.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration aDefault = ImageLoaderConfiguration.createDefault(getApplicationContext());
        ImageLoader.getInstance().init(aDefault);
    }
}
