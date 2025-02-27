package com.caching;

import java.util.Map;

public class CacheService {
    private final CacheEvictionPolicy cache;
    private final DatabaseService databaseService;

    public CacheService(int maxSize, DatabaseService databaseService) {
        this.cache = new CacheEvictionPolicy(maxSize);
        this.databaseService = databaseService;
    }

    public void add(Entity entity) {
        if (cache.size() >= cache.maxSize) {
            Map.Entry<String, Entity> eldest = cache.entrySet().iterator().next();
            databaseService.save(eldest.getValue());
            cache.remove(eldest.getKey());
        }
        cache.put(entity.getId(), entity);
        System.out.println("Entity added to cache: " + entity.getId());
    }

    public void remove(Entity entity) {
        cache.remove(entity.getId());
        databaseService.delete(entity.getId());
        System.out.println("Entity removed from cache and database: " + entity.getId());
    }

    public void removeAll() {
        cache.clear();
        databaseService.deleteAll();
        System.out.println("All entities removed from cache and database.");
    }

    public Entity get(Entity entity) {
        Entity cachedEntity = cache.get(entity.getId());
        if (cachedEntity != null) {
            System.out.println("Entity fetched from cache: " + entity.getId());
            return cachedEntity;
        } else {
            Entity dbEntity = databaseService.get(entity.getId());
            if (dbEntity != null) {
                cache.put(dbEntity.getId(), dbEntity);
                System.out.println("Entity fetched from database and added to cache: " + entity.getId());
            }
            return dbEntity;
        }
    }

    public void clear() {
        cache.clear();
        System.out.println("Cache cleared.");
    }
}

public static void main(String[] args) {
    DatabaseService databaseService = new DatabaseService();
    CacheService cacheService = new CacheService(2, databaseService);

    Entity entity1 = new Entity("1", "Data1");
    Entity entity2 = new Entity("2", "Data2");
    Entity entity3 = new Entity("3", "Data3");

    cacheService.add(entity1);
    cacheService.add(entity2);
    cacheService.add(entity3);

    System.out.println("Entity 1: " + cacheService.get(entity1)); // Should be null (evicted)
    System.out.println("Entity 2: " + cacheService.get(entity2)); // Should return entity2
    System.out.println("Entity 3: " + cacheService.get(entity3)); // Should return entity3
}