package com.guuidea.inreading.ui.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.guuidea.inreading.R;
import com.guuidea.inreading.model.bean.packages.BookSortPackage;
import com.guuidea.inreading.model.bean.BookSubSortBean;
import com.guuidea.inreading.model.bean.packages.BookSubSortPackage;
import com.guuidea.inreading.presenter.BookSortPresenter;
import com.guuidea.inreading.presenter.contract.BookSortContract;
import com.guuidea.inreading.ui.adapter.BookSortAdapter;
import com.guuidea.inreading.ui.base.BaseMVPActivity;
import com.guuidea.inreading.widget.RefreshLayout;
import com.guuidea.inreading.widget.itemdecoration.DividerGridItemDecoration;

import butterknife.BindView;

/**
 * Created by guuidea on 17-4-23.
 * 分类选择
 *
 */

public class BookSortActivity extends BaseMVPActivity<BookSortContract.Presenter> implements BookSortContract.View{
    /*******************Constant*********************/
    private static final String TAG = "SortActivity";
    private static final int SPAN_COUNT = 3;

    @BindView(R.id.book_sort_rl_refresh)
    RefreshLayout mRlRefresh;
    @BindView(R.id.book_sort_rv_boy)
    RecyclerView mRvBoy;
    @BindView(R.id.book_sort_rv_girl)
    RecyclerView mRvGirl;

    private BookSortAdapter mBoyAdapter;
    private BookSortAdapter mGirlAdapter;

    private BookSubSortPackage mSubSortPackage;
    /**********************init***********************************/
    @Override
    protected int getContentId() {
        return R.layout.activity_book_sort;
    }

    @Override
    protected void setUpToolbar(Toolbar toolbar) {
        super.setUpToolbar(toolbar);
        getSupportActionBar().setTitle(
                getResources().getString(R.string.nb_fragment_find_sort));
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setUpAdapter();
    }

    private void setUpAdapter(){
        mBoyAdapter = new BookSortAdapter();
        mGirlAdapter = new BookSortAdapter();

        RecyclerView.ItemDecoration itemDecoration = new DividerGridItemDecoration(this,R.drawable.shape_divider_row,R.drawable.shape_divider_col);

        mRvBoy.setLayoutManager(new GridLayoutManager(this,SPAN_COUNT));
        mRvBoy.addItemDecoration(itemDecoration);
        mRvBoy.setAdapter(mBoyAdapter);

        mRvGirl.setLayoutManager(new GridLayoutManager(this,SPAN_COUNT));
        mRvGirl.addItemDecoration(itemDecoration);
        mRvGirl.setAdapter(mGirlAdapter);
    }

    @Override
    protected BookSortContract.Presenter bindPresenter() {
        return new BookSortPresenter();
    }

    @Override
    protected void initClick() {
        super.initClick();
        mBoyAdapter.setOnItemClickListener(
                (view,pos) -> {
                    BookSubSortBean subSortBean = mSubSortPackage.getMale().get(pos);
                    //上传
                    BookSortListActivity.startActivity(this,"male",subSortBean);
                }
        );
        mGirlAdapter.setOnItemClickListener(
                (view,pos) -> {
                    BookSubSortBean subSortBean = mSubSortPackage.getFemale().get(pos);
                    //上传
                    BookSortListActivity.startActivity(this,"female",subSortBean);
                }
        );
    }

    /*********************logic*******************************/

    @Override
    protected void processLogic() {
        super.processLogic();

        mRlRefresh.showLoading();
        mPresenter.refreshSortBean();
    }

    /***********************rewrite**********************************/
    @Override
    public void finishRefresh(BookSortPackage sortPackage, BookSubSortPackage subSortPackage) {
        if (sortPackage == null || sortPackage.getMale().size() == 0 || sortPackage.getFemale().size() == 0){
            mRlRefresh.showEmpty();
        }
        else {
            mBoyAdapter.refreshItems(sortPackage.getMale());
            mGirlAdapter.refreshItems(sortPackage.getFemale());
        }
        mSubSortPackage = subSortPackage;
    }

    @Override
    public void showError() {
        mRlRefresh.showError();
    }

    @Override
    public void complete() {
        mRlRefresh.showFinish();
    }
}