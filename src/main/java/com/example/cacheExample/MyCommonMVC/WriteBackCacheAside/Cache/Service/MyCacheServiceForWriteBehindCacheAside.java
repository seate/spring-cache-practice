package com.example.cacheExample.MyCommonMVC.WriteBackCacheAside.Cache.Service;

import com.example.cacheExample.MyCommonMVC.MyCache.DTO.CreateMyEntityCacheEntityRequestDTO;
import com.example.cacheExample.MyCommonMVC.MyCache.Entity.MyEntityCacheEntity;

import java.util.List;
import java.util.Optional;

public interface MyCacheServiceForWriteBehindCacheAside {

    void saveMyDirtyCache(CreateMyEntityCacheEntityRequestDTO createMyEntityCacheEntityRequestDTO);

    void saveMyNotDirtyCache(CreateMyEntityCacheEntityRequestDTO createMyEntityCacheEntityRequestDTO);

    void deleteMyDirtyCache(Long myEntityId);

    void deleteMyNotDirtyCache(Long myEntityId);


    Boolean existsMyCache(Long myEntityId);

    Optional<MyEntityCacheEntity> findMyCache(Long myEntityId);

    List<MyEntityCacheEntity> findDirtyCaches();

    void updateMyCache(Long myEntityId, String newName);
}
