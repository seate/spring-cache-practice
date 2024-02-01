package com.example.cacheExample.MyCommonMVC.MyEntity.Service;

import com.example.cacheExample.MyCommonMVC.MyEntity.Entity.MyEntity;

import java.util.List;

public interface MyEntityService {

    void saveMyEntity(MyEntity myEntity);

    void saveMyEntities(List<MyEntity> myEntities);

    void deleteMyEntity(Long id);

    MyEntity findMyEntity(Long myEntityId);

    void updateMyEntity(Long myEntityId, String newName);
}
