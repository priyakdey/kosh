package com.priyakdey.backend.security;

import com.priyakdey.backend.security.config.StatePayload;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Priyak Dey
 */
public class StateTokenCache {

    private final ConcurrentMap<String, StatePayload> table;

    public StateTokenCache() {
        this.table = new ConcurrentHashMap<>();
    }

    public void put(String key, StatePayload value) {
        table.put(key, value);
    }

    public StatePayload get(String key) {
        StatePayload value = table.get(key);
        if (value == null || value.isExpired()) {
            evict(key);
            return null;
        }

        return value;
    }

    public void evict(String key) {
        table.remove(key);
    }

}
