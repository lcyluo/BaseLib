package com.lcy.base.core.ui.activity;

import androidx.annotation.NonNull;

import com.lcy.base.core.presenter.IBasePresenter;
import com.lcy.base.core.presenter.view.IBaseView;

import javax.inject.Inject;

@SuppressWarnings("all")
public abstract class BaseActivity<P extends IBasePresenter> extends SimpleActivity implements IBaseView {

    @Inject
    protected P mPresenter;

    @Override
    protected void initPresenter() {
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(int code, @NonNull String msg) {

    }

}