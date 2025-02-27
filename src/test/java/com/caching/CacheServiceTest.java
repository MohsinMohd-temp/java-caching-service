package com.caching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CacheServiceTest {
    private CacheService cacheService;
    private DatabaseService databaseService;

    @BeforeEach
    void setUp() {
        databaseService = new DatabaseService();
        cacheService = new CacheService(2, databaseService);
    }

    @Test
    void testAddAndGet() {
        Entity entity1 = new Entity("1", "Data1");
        Entity entity2 = new Entity("2", "Data2");

        cacheService.add(entity1);
        cacheService.add(entity2);

        assertEquals(entity1, cacheService.get(entity1));
        assertEquals(entity2, cacheService.get(entity2));
    }

    @Test
    void testEviction() {
        Entity entity1 = new Entity("1", "Data1");
        Entity entity2 = new Entity("2", "Data2");
        Entity entity3 = new Entity("3", "Data3");

        cacheService.add(entity1);
        cacheService.add(entity2);
        cacheService.add(entity3);

        assertNull(cacheService.get(entity1)); // entity1 should be evicted
        assertEquals(entity2, cacheService.get(entity2));
        assertEquals(entity3, cacheService.get(entity3));
    }

    @Test
    void testRemove() {
        Entity entity1 = new Entity("1", "Data1");
        cacheService.add(entity1);
        cacheService.remove(entity1);
        assertNull(cacheService.get(entity1));
    }

    @Test
    void testRemoveAll() {
        Entity entity1 = new Entity("1", "Data1");
        Entity entity2 = new Entity("2", "Data2");

        cacheService.add(entity1);
        cacheService.add(entity2);
        cacheService.removeAll();

        assertNull(cacheService.get(entity1));
        assertNull(cacheService.get(entity2));
    }

    @Test
    void testClear() {
        Entity entity1 = new Entity("1", "Data1");
        cacheService.add(entity1);
        cacheService.clear();
        assertNull(cacheService.get(entity1));
    }
}