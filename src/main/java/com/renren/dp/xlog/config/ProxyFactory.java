package com.renren.dp.xlog.config;

public interface ProxyFactory<T> {
    public T get(String config);
}
