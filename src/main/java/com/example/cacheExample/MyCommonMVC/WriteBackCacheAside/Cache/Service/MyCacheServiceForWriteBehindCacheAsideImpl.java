package com.example.cacheExample.MyCommonMVC.WriteBackCacheAside.Cache.Service;

import com.example.cacheExample.MyCommonMVC.MyCache.DTO.CreateMyEntityCacheEntityRequestDTO;
import com.example.cacheExample.MyCommonMVC.MyCache.Entity.MyEntityCacheEntity;
import com.example.cacheExample.MyCommonMVC.WriteBackCacheAside.Cache.Repository.WriteBehindCacheAsideRedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyCacheServiceForWriteBehindCacheAsideImpl implements MyCacheServiceForWriteBehindCacheAside {

    private final WriteBehindCacheAsideRedisRepository myCacheRepository;

    @Override
    public void saveMyDirtyCache(CreateMyEntityCacheEntityRequestDTO createMyEntityCacheEntityRequestDTO) {
        myCacheRepository.saveDirty(createMyEntityCacheEntityRequestDTO.toEntity());
    }

    @Override
    public void saveMyNotDirtyCache(CreateMyEntityCacheEntityRequestDTO createMyEntityCacheEntityRequestDTO) {
        myCacheRepository.saveNotDirty(createMyEntityCacheEntityRequestDTO.toEntity());
    }

    @Override
    public void deleteMyDirtyCache(Long myEntityId) {
        myCacheRepository.deleteDirtyById(myEntityId);
    }

    @Override
    public void deleteMyNotDirtyCache(Long myEntityId) {
        myCacheRepository.deleteNotDirtyById(myEntityId);
    }

    @Override
    public Boolean existsMyCache(Long myEntityId) {
        return myCacheRepository.existsById(myEntityId);
    }

    @Override
    public Optional<MyEntityCacheEntity> findMyCache(Long myEntityId) {
        return myCacheRepository.findDirtyCacheById(myEntityId);
    }

    @Override
    public List<MyEntityCacheEntity> findDirtyCaches() {
        return myCacheRepository.findDirties();
    }

    @Override
    public void updateMyCache(Long myEntityId, String newName) {
        Optional<MyEntityCacheEntity> dirtyCacheById = myCacheRepository.findDirtyCacheById(myEntityId);

        if (dirtyCacheById.isPresent()) {
            MyEntityCacheEntity myEntityCacheEntity = dirtyCacheById.get();
            myEntityCacheEntity.updateDTO(newName);

            saveMyDirtyCache(new CreateMyEntityCacheEntityRequestDTO(myEntityCacheEntity.toMyEntity()));
            return;
        }

        Optional<MyEntityCacheEntity> notDirtyCacheById = myCacheRepository.findNotDirtyCacheById(myEntityId);

        if (notDirtyCacheById.isPresent()) {
            MyEntityCacheEntity myEntityCacheEntity = notDirtyCacheById.get();
            myEntityCacheEntity.updateDTO(newName);

            deleteMyNotDirtyCache(myEntityId);
            saveMyDirtyCache(new CreateMyEntityCacheEntityRequestDTO(myEntityCacheEntity.toMyEntity()));
            return;
        }

        throw new RuntimeException("캐시가 존재하지 않습니다.");
    }
}
