package panjiangang.bwie.com.mvpjilei.presenter;

import panjiangang.bwie.com.mvpjilei.model.bean.TestBean;
import panjiangang.bwie.com.mvpjilei.model.http.HttpUtils;
import panjiangang.bwie.com.mvpjilei.view.IView.MainIView;

/**
 * Created by lenovo on 2017/12/26.
 */

public class MainPresenter extends BasePresenter implements HttpUtils.HttpUtilsCallback<TestBean> {

    private MainIView mainIView;
    private boolean needClear = false;
    private int page;
    private int pageSize = 15;
    private HttpUtils httpUtils;

    public MainPresenter() {
        httpUtils = new HttpUtils();
    }

    public void loadDataFromServer() {
        httpUtils.loadData(this, "23", 15, 0, TestBean.class);
    }

    public void attachView(MainIView view) {
        this.mainIView = view;
    }

    public void dettachView() {
        mainIView = null;
    }

    @Override
    public void callbackOK(TestBean testBean) {
        mainIView.onSuccess(testBean,needClear);
        needClear = false;
    }

    @Override
    public void callbackErr(String errMessage) {

    }

    public void refreshData() {//刷新数据
        needClear = true;
        page = 0;
        httpUtils.loadData(this,"23",pageSize,page*pageSize,TestBean.class);
    }

    public void loadMore() {//加载更多
        page++;
        httpUtils.loadData(this,"23",pageSize,page*pageSize,TestBean.class);
    }
}
