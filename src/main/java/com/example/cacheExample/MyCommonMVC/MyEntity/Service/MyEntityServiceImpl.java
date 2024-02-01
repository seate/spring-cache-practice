package com.example.cacheExample.MyCommonMVC.MyEntity.Service;

import com.example.cacheExample.MyCommonMVC.MyEntity.Entity.MyEntity;
import com.example.cacheExample.MyCommonMVC.MyEntity.Repository.MyEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyEntityServiceImpl implements MyEntityService {

    private final MyEntityRepository myEntityRepository;

    @Override
    public void saveMyEntity(MyEntity myEntity) {
        myEntityRepository.save(myEntity);
    }

    @Override
    public void saveMyEntities(List<MyEntity> myEntities) {
        myEntityRepository.saveAll(myEntities);
    }

    @Override
    public void deleteMyEntity(Long id) {
        myEntityRepository.deleteById(id);
    }

    @Override
    public MyEntity findMyEntity(Long myEntityId) {
        return myEntityRepository.findById(myEntityId)
                .orElseThrow(() -> new RuntimeException("myEntityId: " + myEntityId + " is not exist"));
    }

    @Override
    public void updateMyEntity(Long myEntityId, String newName) {
        MyEntity myEntity = myEntityRepository.findById(myEntityId)
                    .orElseThrow(() -> new RuntimeException("myEntityId: " + myEntityId + " is not exist"));
        myEntity.setName(newName);
    }
}
