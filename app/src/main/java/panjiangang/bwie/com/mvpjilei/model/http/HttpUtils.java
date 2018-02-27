package panjiangang.bwie.com.mvpjilei.model.http;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lenovo on 2017/12/26.
 */

public class HttpUtils<T> {
//    http://tingapi.ting.baidu.com/v1/restserver/ting?size=20&type=2&callback=cb_list&_t=1468380543284&format=json&method=baidu.ting.billboard.billList
    private static final String BASE_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting";
    private static final String METHOD_GET_MUSIC_LIST = "?method=baidu.ting.billboard.billList&format=json";
    private static HttpUtilsCallback mHttpUtilsCallback = null;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            T obj = (T) msg.obj;
            mHttpUtilsCallback.callbackOK(obj);
        }
    };

    public void loadData(final HttpUtilsCallback httpUtilsCallback, String type, int size, int offset, final Class<T> classOfT) {
        mHttpUtilsCallback = httpUtilsCallback;
        String url = BASE_URL + METHOD_GET_MUSIC_LIST + "&type=" + type + "&size=" + size + "&offset=" + offset;

        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new HttpInterceptor()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHttpUtilsCallback.callbackErr(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                T t = gson.fromJson(string, classOfT);
                Message message = handler.obtainMessage();
                message.obj = t;
                handler.sendMessage(message);
            }
        });
    }

    public interface HttpUtilsCallback<T> {
        void callbackOK(T testBean);
        void callbackErr(String errMessage);
    }
}
