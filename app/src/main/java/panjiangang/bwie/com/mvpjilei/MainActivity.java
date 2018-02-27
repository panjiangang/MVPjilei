package panjiangang.bwie.com.mvpjilei;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import panjiangang.bwie.com.mvpjilei.model.bean.TestBean;
import panjiangang.bwie.com.mvpjilei.presenter.MainPresenter;
import panjiangang.bwie.com.mvpjilei.view.IView.MainIView;
import panjiangang.bwie.com.mvpjilei.view.activity.BaseActivity;
import panjiangang.bwie.com.mvpjilei.view.adapter.SongListAdapter;

public class MainActivity extends BaseActivity implements MainIView,SwipeRefreshLayout.OnRefreshListener {

    private MainPresenter mainPresenter;//P层
    private RecyclerView recyclerView;
    private SongListAdapter songListAdapter;//适配器
    private SwipeRefreshLayout swipeRefreshLayout;//刷新
    private LinearLayoutManager linearLayoutManager;//布局管理器
    private boolean isLoading = false;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initView();
//        initData();
    }

    public void initView() {//初始化控件
        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.songList);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.addItemDecoration();

        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setEnabled(true);//可以用,能够用,可以激活
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {//滚动的监听事件
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                当前能看见的列表项个数(小半个也算)
                int visibleItemCount = recyclerView.getChildCount();

//                获取或设置要分页的记录总数
                int totalItemCount = linearLayoutManager.getItemCount();

//                LayoutManager在Recycler中找到的第一个可见的Item
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                if (visibleItemCount + firstVisibleItemPosition == totalItemCount){
                    if (!isLoading){//isLoading=false
                        progressBar.setVisibility(View.VISIBLE);
                        mainPresenter.loadMore();
                        isLoading = true;
                    }
                }
            }
        });

    }

    public void initData() {
        songListAdapter = new SongListAdapter(this);
        recyclerView.setAdapter(songListAdapter);
        mainPresenter = new MainPresenter();
        mainPresenter.attachView(this);
        mainPresenter.loadDataFromServer();
    }

    @Override
    public int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onSuccess(TestBean testBean,boolean needClear) {
        isLoading = false;
        if(needClear) {
            swipeRefreshLayout.setRefreshing(false);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            },5000);

        }
        songListAdapter.setListData(testBean.getSong_list(),needClear);
        songListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mainPresenter != null) {
            mainPresenter.dettachView();
        }
    }

    @Override
    public void onRefresh() {

        mainPresenter.refreshData();

        Log.e("myMessage","onRefresh");
    }
}
