package com.guuidea.inreading.ui.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guuidea.inreading.R;
import com.guuidea.inreading.model.local.BookRepository;
import com.guuidea.inreading.ui.adapter.FileSystemAdapter;
import com.guuidea.inreading.utils.media.MediaStoreHelper;
import com.guuidea.inreading.widget.RefreshLayout;
import com.guuidea.inreading.widget.itemdecoration.DividerItemDecoration;

import butterknife.BindView;

/**
 * Created by guuidea on 17-5-27.
 * 本地书籍
 */

public class LocalBookFragment extends BaseFileFragment{
    @BindView(R.id.refresh_layout)
    RefreshLayout mRlRefresh;
    @BindView(R.id.local_book_rv_content)
    RecyclerView mRvContent;
    @Override
    protected int getContentId() {
        return R.layout.fragment_local_book;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        setUpAdapter();
    }

    private void setUpAdapter(){
        mAdapter = new FileSystemAdapter();
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mRvContent.setAdapter(mAdapter);
    }

    @Override
    protected void initClick() {
        super.initClick();
        mAdapter.setOnItemClickListener(
                (view, pos) -> {
                    //如果是已加载的文件，则点击事件无效。
                    String id = mAdapter.getItem(pos).getAbsolutePath();
                    if (BookRepository.getInstance().getCollBook(id) != null){
                        return;
                    }

                    //点击选中
                    mAdapter.setCheckedItem(pos);

                    //反馈
                    if (mListener != null){
                        mListener.onItemCheckedChange(mAdapter.getItemIsChecked(pos));
                    }
                }
        );
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        MediaStoreHelper.getAllBookFile(getActivity(),
                (files) -> {
                    if (files.isEmpty()){
                        mRlRefresh.showEmpty();
                    }
                    else {
                        mAdapter.refreshItems(files);
                        mRlRefresh.showFinish();
                        //反馈
                        if (mListener != null){
                            mListener.onCategoryChanged();
                        }
                    }
                });
    }
}
