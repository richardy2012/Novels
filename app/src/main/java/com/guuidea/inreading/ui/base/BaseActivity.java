package com.guuidea.inreading.ui.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.guuidea.inreading.R;
import com.guuidea.inreading.utils.StatusBarCompatKt;
import com.guuidea.inreading.utils.StatusUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by PC on 2016/9/8.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final int INVALID_VAL = -1;

    protected CompositeDisposable mDisposable;
    //ButterKnife
    private Toolbar mToolbar;

    private Unbinder unbinder;

    /****************************abstract area*************************************/

    @LayoutRes
    protected abstract int getContentId();

    /************************init area************************************/
    protected void addDisposable(Disposable d) {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
        }
        mDisposable.add(d);
    }

    /**
     * 配置Toolbar
     *
     * @param toolbar
     */
    protected void setUpToolbar(Toolbar toolbar) {
    }

    protected void initData(Bundle savedInstanceState) {
    }

    /**
     * 初始化零件
     */
    protected void initWidget() {

    }

    /**
     * 初始化点击事件
     */
    protected void initClick() {
    }

    /**
     * 逻辑使用区
     */
    protected void processLogic() {
    }

    /*************************lifecycle area*****************************************************/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentId());
        setStatusColor();
        setSystemInvadeBlack();
        initData(savedInstanceState);
        unbinder = ButterKnife.bind(this);
        initToolbar();
        initWidget();
        initClick();
        processLogic();

    }

    protected void setStatusColor() {

        StatusUtil.INSTANCE.setUseStatusBarColor(this, Color.parseColor("#EDEDED"));
    }

    protected void setSystemInvadeBlack() {
        // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtil.INSTANCE.setSystemStatus(this, true, true);
    }

    private void initToolbar() {
        //更严谨是通过反射判断是否存在Toolbar
//        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        mToolbar = findViewById(R.id.toolbar);
        if (mToolbar != null) {
            supportActionBar(mToolbar);
            setUpToolbar(mToolbar);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    /**************************used method area*******************************************/

    protected void startActivity(Class<? extends AppCompatActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    protected ActionBar supportActionBar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        mToolbar.setNavigationOnClickListener(
                (v) -> finish()
        );
        return actionBar;
    }

    protected void setStatusBarColor(int statusColor) {
        StatusBarCompatKt.Companion.compat(this, ContextCompat.getColor(this, statusColor));
    }
}
