package com.qswang.steamer.memcached;

public interface MemcachedService {
    public void createRecord(String key,Object object);
    public Object fetchRecord(String key);
    public void deleteRecord(String key);
    public void clear();
}
