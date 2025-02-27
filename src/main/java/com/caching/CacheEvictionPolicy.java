package com.caching;

import java.util.LinkedHashMap;
import java.util.Map;

public class CacheEvictionPolicy extends LinkedHashMap<String, Entity> {
    private final int maxSize;

    public CacheEvictionPolicy(int maxSize) {
        super(maxSize, 0.75f, true);
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, Entity> eldest) {
        return size() > maxSize;
    }
}