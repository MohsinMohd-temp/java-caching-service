package com.caching;

import java.util.HashMap;
import java.util.Map;

public class DatabaseService {
    private final Map<String, Entity> database = new HashMap<>();

    public void save(Entity entity) {
        database.put(entity.getId(), entity);
        System.out.println("Entity saved to database: " + entity.getId());
    }

    public Entity get(String id) {
        System.out.println("Entity fetched from database: " + id);
        return database.get(id);
    }

    public void delete(String id) {
        database.remove(id);
        System.out.println("Entity deleted from database: " + id);
    }

    public void deleteAll() {
        database.clear();
        System.out.println("All entities deleted from database.");
    }
}