package panjiangang.bwie.com.mvpjilei.view.activity;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by lenovo on 2017/12/26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentViewId());
        initView();
        initData();
    }

    public abstract void initView();
    public abstract void initData();
    public abstract int setContentViewId();
}
