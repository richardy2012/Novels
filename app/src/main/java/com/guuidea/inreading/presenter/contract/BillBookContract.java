package com.guuidea.inreading.presenter.contract;

import com.guuidea.inreading.model.bean.BillBookBean;
import com.guuidea.inreading.ui.base.BaseContract;

import java.util.List;

/**
 * Created by guuidea on 17-5-3.
 */

public interface BillBookContract {
    interface View extends BaseContract.BaseView{
        void finishRefresh(List<BillBookBean> beans);
    }

    interface Presenter extends BaseContract.BasePresenter<View>{
        void refreshBookBrief(String billId);
    }
}
