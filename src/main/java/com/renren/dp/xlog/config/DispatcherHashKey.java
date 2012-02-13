package com.renren.dp.xlog.config;

public class DispatcherHashKey implements HashableKey<String[]> {

    @Override
    public int hash(String[] key, int size) {
        int count = 0;
        for (String category : key) {
            for (char c : category.toCharArray()) {
                count += c;
            }
        }
        return Math.abs(count % size);
    }

}
