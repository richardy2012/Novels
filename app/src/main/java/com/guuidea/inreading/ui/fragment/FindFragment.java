package com.guuidea.inreading.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.guuidea.inreading.R;;
import com.guuidea.inreading.model.bean.SectionBean;
import com.guuidea.inreading.model.flag.FindType;
import com.guuidea.inreading.ui.activity.BillboardActivity;
import com.guuidea.inreading.ui.activity.BookListActivity;
import com.guuidea.inreading.ui.activity.BookSortActivity;
import com.guuidea.inreading.ui.adapter.SectionAdapter;
import com.guuidea.inreading.ui.base.BaseFragment;
import com.guuidea.inreading.widget.itemdecoration.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by guuidea on 17-4-15.
 */

public class FindFragment extends BaseFragment {
    /******************view************************/
    @BindView(R.id.find_rv_content)
    RecyclerView mRvContent;
    /*******************Object***********************/
    SectionAdapter mAdapter;

    @Override
    protected int getContentId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        setUpAdapter();
    }

    private void setUpAdapter(){
        ArrayList<SectionBean> sections = new ArrayList<>();
        for (FindType type : FindType.values()){
            sections.add(new SectionBean(type.getTypeName(),type.getIconId()));
        }

        mAdapter = new SectionAdapter();
        mRvContent.setHasFixedSize(true);
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext()));
        mRvContent.setAdapter(mAdapter);
        mAdapter.addItems(sections);
    }


    @Override
    protected void initClick() {
        mAdapter.setOnItemClickListener(
                (view,pos) -> {
                    FindType type = FindType.values()[pos];
                    Intent intent;
                    //跳转
                    switch (type){
                        case TOP:
                            intent = new Intent(getContext(),BillboardActivity.class);
                            startActivity(intent);
                            break;
                        case SORT:
                            intent = new Intent(getContext(), BookSortActivity.class);
                            startActivity(intent);
                            break;
                        case TOPIC:
                            intent = new Intent(getContext(), BookListActivity.class);
                            startActivity(intent);
                            break;
                    }
                }
        );

    }
}