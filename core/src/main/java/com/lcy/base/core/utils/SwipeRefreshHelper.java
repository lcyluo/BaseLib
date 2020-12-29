package com.lcy.base.core.utils;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.lcy.base.core.R;


/**
 * Created by long on 2016/12/1.
 * 下拉刷新帮助类
 */
public class SwipeRefreshHelper {

    private SwipeRefreshHelper() {
        throw new AssertionError();
    }

    /**
     * 初始化，关联AppBarLayout，处理滑动冲突
     */
    public static void init(final SwipeRefreshLayout refreshLayout, AppBarLayout appBar, SwipeRefreshLayout.OnRefreshListener listener) {
        refreshLayout.setColorSchemeResources(R.color.base_core_color_accent);
        refreshLayout.setOnRefreshListener(listener);
        appBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> refreshLayout.setEnabled(verticalOffset >= 0));
    }

    /**
     * 初始化
     */
    public static void init(SwipeRefreshLayout refreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        refreshLayout.setColorSchemeResources(R.color.base_core_color_accent);
        refreshLayout.setOnRefreshListener(listener);
    }

    /**
     * 使能刷新
     */
    public static void enableRefresh(SwipeRefreshLayout refreshLayout, boolean isEnable) {
        if (refreshLayout != null) {
            refreshLayout.setEnabled(isEnable);
        }
    }

    /**
     * 控制刷新
     */
    public static void controlRefresh(SwipeRefreshLayout refreshLayout, boolean isRefresh) {
        if (refreshLayout != null) {
            if (isRefresh != refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(isRefresh);
            }
        }
    }
}
