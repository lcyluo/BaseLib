package com.lcy.base.core.utils;

import android.content.Context;
import android.support.v7.widget.*;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lcy.base.core.widgets.TopSmoothScroller;

public class RecyclerViewHelper {

    private RecyclerViewHelper() {
        throw new RuntimeException("RecyclerViewHelper cannot be initialized!");
    }


    /**
     * 配置垂直列表RecyclerView
     *
     * @param view
     */
    public static void initRecyclerViewV(Context context, RecyclerView view, RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initRecyclerView(layoutManager, view, adapter);
    }

    public static void initRecyclerView(LinearLayoutManager manager, RecyclerView view, RecyclerView.Adapter adapter) {
        view.setLayoutManager(manager);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

    public static void initRecyclerViewV(Context context, RecyclerView view, BaseQuickAdapter adapter, BaseQuickAdapter.RequestLoadMoreListener listener) {
        initRecyclerViewV(context, view, adapter);
        adapter.setOnLoadMoreListener(listener, view);
    }


    /**
     * 配置水平列表RecyclerView
     *
     * @param view
     */
    public static void initRecyclerViewH(Context context, RecyclerView view, RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        view.setLayoutManager(layoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

    /**
     * 配置网格列表RecyclerView
     *
     * @param view
     */
    public static void initRecyclerViewG(Context context, RecyclerView view, RecyclerView.Adapter adapter, int column) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, column, LinearLayoutManager.VERTICAL, false);
        view.setLayoutManager(layoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }


    /**
     * 配置瀑布流列表RecyclerView
     *
     * @param view
     */
    public static void initRecyclerViewSV(Context context, RecyclerView view, RecyclerView.Adapter adapter, int column) {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(column, StaggeredGridLayoutManager.VERTICAL);
        view.setLayoutManager(layoutManager);
        view.setItemAnimator(new DefaultItemAnimator());
        view.setAdapter(adapter);
    }

    public static void isFullScreen(RecyclerView recyclerView, final BaseQuickAdapter adapter, final OnFullScreenListener listener) {
        if (recyclerView == null || adapter == null || listener == null) return;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null) return;
        if (manager instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) manager;
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isFullScreen(linearLayoutManager, adapter.getItemCount())) {
                        listener.fullScreen();
                    }
                }
            }, 50);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            final StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    final int[] positions = new int[staggeredGridLayoutManager.getSpanCount()];
                    staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(positions);
                    int pos = getTheBiggestNumber(positions) + 1;
                    if (pos != adapter.getItemCount()) {
                        listener.fullScreen();
                    }
                }
            }, 50);
        }
    }

    private static boolean isFullScreen(LinearLayoutManager llm, int itemCount) {
        return (llm.findLastCompletelyVisibleItemPosition() + 1) != itemCount ||
                llm.findFirstCompletelyVisibleItemPosition() != 0;
    }

    private static int getTheBiggestNumber(int[] numbers) {
        int tmp = -1;
        if (numbers == null || numbers.length == 0) {
            return tmp;
        }
        for (int num : numbers) {
            if (num > tmp) {
                tmp = num;
            }
        }
        return tmp;
    }


    public static void moveToPosition(RecyclerView recyclerView, int position) {
        moveToPosition(recyclerView, position, false);
    }

    public static void moveToPosition(RecyclerView recyclerView, int position, boolean smooth) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (manager == null) return;
        int orientation = manager.getOrientation();
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        //然后区分情况
        if (position <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView.scrollToPosition(position);
        } else if (position <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                int left = recyclerView.getChildAt(position - firstItem).getLeft();
                if (smooth) {
                    recyclerView.smoothScrollBy(left, 0);
                } else {
                    recyclerView.scrollBy(left, 0);
                }
            } else {
                int top = recyclerView.getChildAt(position - firstItem).getTop();
                if (smooth) {
                    recyclerView.smoothScrollBy(0, top);
                } else {
                    recyclerView.scrollBy(0, top);
                }
            }
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            if (smooth) {
                recyclerView.smoothScrollToPosition(position);
            } else {
                recyclerView.scrollToPosition(position);
            }
            //这里这个变量是用在RecyclerView滚动监听里面的
        }
    }

    public static void moveToTopPosition(RecyclerView recyclerView, int position) {
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (manager != null) {
            TopSmoothScroller mScroller = new TopSmoothScroller(recyclerView.getContext());
            mScroller.setTargetPosition(position);
            manager.startSmoothScroll(mScroller);
        }

    }

    public interface OnFullScreenListener {
        void fullScreen();
    }
}
