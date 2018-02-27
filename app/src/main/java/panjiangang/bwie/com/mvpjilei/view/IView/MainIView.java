package panjiangang.bwie.com.mvpjilei.view.IView;

import panjiangang.bwie.com.mvpjilei.model.bean.TestBean;

/**
 * Created by lenovo on 2017/12/26.
 */

public interface MainIView extends BaseIView{
    void onSuccess(TestBean testBean, boolean needClear);
}
