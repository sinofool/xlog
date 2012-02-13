package com.renren.dp.xlog.config;

public interface HashableKey<T> {
    public int hash(T key, int size);
}
