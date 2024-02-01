package com.example.cacheExample.MyCommonMVC.Service;

import com.example.cacheExample.MyCommonMVC.MyCache.DTO.UpdateMyEntityCacheEntityRequestDTO;
import com.example.cacheExample.MyCommonMVC.MyEntity.DTO.CreateEntityRequestDTO;
import com.example.cacheExample.MyCommonMVC.MyEntity.Entity.MyEntity;

public interface MyCommonService {

    void save(CreateEntityRequestDTO createEntityRequestDTO);

    void delete(Long id);

    MyEntity find(Long id);

    void update(Long id, UpdateMyEntityCacheEntityRequestDTO updateMyEntityCacheEntityRequestDTO);
}
