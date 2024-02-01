package com.example.cacheExample.MyCommonMVC.WriteBackCacheAside.Common.Service;


import com.example.cacheExample.MyCommonMVC.MyCache.DTO.CreateMyEntityCacheEntityRequestDTO;
import com.example.cacheExample.MyCommonMVC.MyCache.DTO.UpdateMyEntityCacheEntityRequestDTO;
import com.example.cacheExample.MyCommonMVC.MyCache.Entity.MyEntityCacheEntity;
import com.example.cacheExample.MyCommonMVC.MyEntity.DTO.CreateEntityRequestDTO;
import com.example.cacheExample.MyCommonMVC.MyEntity.Entity.MyEntity;
import com.example.cacheExample.MyCommonMVC.MyEntity.Service.MyEntityService;
import com.example.cacheExample.MyCommonMVC.Service.MyCommonService;
import com.example.cacheExample.MyCommonMVC.WriteBackCacheAside.Cache.Service.MyCacheServiceForWriteBehindCacheAside;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyWriteBehindCacheAsideServiceImpl implements MyCommonService {

    private final MyEntityService myEntityService;

    private final MyCacheServiceForWriteBehindCacheAside myCacheService;

    @Override
    public void save(CreateEntityRequestDTO createEntityRequestDTO) {
        myCacheService.saveMyDirtyCache(new CreateMyEntityCacheEntityRequestDTO(createEntityRequestDTO));
    }

    @Scheduled(cron = "0 0/#{${cache.expire.saveInDBMinutes}} * * * *")
    private void saveEntityFromCache() {
        log.debug("스케줄러에 의해 저장됨");

        List<MyEntity> dirtyCaches = myCacheService
                .findDirtyCaches()
                .stream().map(MyEntityCacheEntity::toMyEntity)
                .toList();

        myEntityService.saveMyEntities(dirtyCaches);
    }

    @Override
    public void delete(Long id) {
        myCacheService.deleteMyDirtyCache(id);
        myCacheService.deleteMyNotDirtyCache(id);
        myEntityService.deleteMyEntity(id);
    }

    @Override
    public MyEntity find(Long id) {
        Optional<MyEntityCacheEntity> myCache = myCacheService.findMyCache(id);

        if (myCache.isPresent()) return myCache.get().toMyEntity();
        else {
            MyEntity myEntity = myEntityService.findMyEntity(id);

            myCacheService.saveMyNotDirtyCache(new CreateMyEntityCacheEntityRequestDTO(myEntity));

            return myEntity;
        }
    }

    @Override
    public void update(Long id, UpdateMyEntityCacheEntityRequestDTO updateMyEntityCacheEntityRequestDTO) {
        if (myCacheService.existsMyCache(id))
            myCacheService.updateMyCache(id, updateMyEntityCacheEntityRequestDTO.getNewName());
        else {
            MyEntity myEntity = myEntityService.findMyEntity(id);
            myEntity.setName(updateMyEntityCacheEntityRequestDTO.getNewName());

            myCacheService.saveMyDirtyCache(new CreateMyEntityCacheEntityRequestDTO(myEntity));
        }
    }
}
