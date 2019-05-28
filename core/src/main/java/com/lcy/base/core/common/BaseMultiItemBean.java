package com.lcy.base.core.common;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class BaseMultiItemBean<T> implements MultiItemEntity {

    public int type;
    public T data;

    public BaseMultiItemBean(int type, T data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
