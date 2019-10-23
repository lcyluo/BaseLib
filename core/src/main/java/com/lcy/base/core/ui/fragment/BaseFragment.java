package com.lcy.base.core.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcy.base.core.common.BaseApplication;
import com.lcy.base.core.injection.component.AppComponent;
import com.lcy.base.core.injection.module.FragmentModule;
import com.lcy.base.core.presenter.IBasePresenter;
import com.lcy.base.core.presenter.view.IBaseView;
import com.lcy.base.core.rx.lifecycle.FragmentLifecycleable;
import com.lcy.base.core.ui.activity.BaseAppCompatActivity;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.android.RxLifecycleAndroid;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;
import me.yokeyword.fragmentation.SupportFragment;

import javax.inject.Inject;


public abstract class BaseFragment<P extends IBasePresenter> extends SupportFragment implements IBaseView, FragmentLifecycleable {

    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    @Inject
    protected P mPresenter;
    protected BaseAppCompatActivity mActivity;
    protected Context mContext;
    //缓存Fragment view
    protected View mRootView;
    protected boolean isInit = false;

    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void onAttach(Context context) {
        mActivity = (BaseAppCompatActivity) context;
        mContext = context;
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
        super.onAttach(context);
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

    protected AppComponent getAppComponent() {
        return BaseApplication.Companion.instance().getAppComponent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutId(), null);
            initInject();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //noinspection unchecked
        mPresenter.attachView(this);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        if (savedInstanceState == null) {
            if (!isHidden()) {
                setInit();
            }
        } else {
            setInit();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isInit && !hidden) {
            setInit();
        }
    }

    private void setInit() {
        isInit = true;
        initEventAndData();
        initListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
        unSubscribe();
    }

    protected abstract int getLayoutId();

    protected abstract void initEventAndData();

    protected abstract void initListeners();

    protected abstract void initInject();

    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    protected void openActivity(Class<?> pClass, Bundle pBundle) {

        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        mContext.startActivity(intent);
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.hide();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }


    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        if (!isInit && !this.isHidden()) {
            setInit();
        }
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    @CallSuper
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onStop() {
        lifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

}