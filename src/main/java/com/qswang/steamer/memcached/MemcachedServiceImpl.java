package com.qswang.steamer.memcached;

import com.qswang.steamer.controller.GamingController;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemcachedServiceImpl implements MemcachedService{

    private static final Logger logger = LoggerFactory.getLogger(MemcachedServiceImpl.class);
    private MemcachedClient memcachedClient;

    @Value("${memcached.exptime}")
    private int exptime;

    @Override
    public void createRecord(String key, Object object) {
        memcachedClient.add(key, exptime, object);
    }

    @Override
    public Object fetchRecord(String key) {
        return Optional.ofNullable(memcachedClient.get(key));
    }

    @Override
    public void deleteRecord(String key) {
        memcachedClient.delete(key);
    }

    @Override
    public void clear() {
        memcachedClient.flush();
    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    @Autowired
    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public int getExptime() {
        return exptime;
    }

    public void setExptime(int exptime) {
        this.exptime = exptime;
    }
}
