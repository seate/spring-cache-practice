package com.example.cacheExample.MyCommonMVC.WriteBackCacheAside.Cache.Repository;

import com.example.cacheExample.MyCommonMVC.MyCache.Entity.MyEntityCacheEntity;

import java.util.List;
import java.util.Optional;

public interface WriteBehindCacheAsideRedisRepository {

    void saveDirty(MyEntityCacheEntity cacheEntity);

    void saveNotDirty(MyEntityCacheEntity cacheEntity);

    void deleteDirtyById(Long id);

    void deleteNotDirtyById(Long id);

    Boolean existsById(Long id);

    Optional<MyEntityCacheEntity> findDirtyCacheById(Long id);

    Optional<MyEntityCacheEntity> findNotDirtyCacheById(Long id);

    List<MyEntityCacheEntity> findDirties();
}
