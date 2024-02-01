package com.example.cacheExample.MyCommonMVC.WriteBackCacheAside.Cache.Repository;

import com.example.cacheExample.MyCommonMVC.MyCache.Entity.MyEntityCacheEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class WriteBehindCacheAsideRedisRepositoryImpl implements WriteBehindCacheAsideRedisRepository {

    private final RedisTemplate<String, MyEntityCacheEntity> redisTemplate;

    @Value("#{${cache.expire.saveInDBMinutes} + ${cache.expire.redisTTLSpareMinutes}}")
    private Integer EXPIRE_MINUTES;

    private final String DIRTY = ":dirty";
    private final String NOT_DIRTY = ":notDirty";

    private String getKey(Long id) {
        return MyEntityCacheEntity.class.getSimpleName() + ":" + id;
    }

    @Override
    public void saveDirty(MyEntityCacheEntity cacheEntity) {
        String key = getKey(cacheEntity.getId()) + DIRTY;

        redisTemplate.opsForValue().set(key, cacheEntity, EXPIRE_MINUTES, TimeUnit.MINUTES);
    }

    @Override
    public void saveNotDirty(MyEntityCacheEntity cacheEntity) {
        String key = getKey(cacheEntity.getId()) + NOT_DIRTY;

        redisTemplate.opsForValue().set(key, cacheEntity, EXPIRE_MINUTES, TimeUnit.MINUTES);
    }

    private void deleteById(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void deleteDirtyById(Long id) {
        deleteById(getKey(id) + DIRTY);
    }

    @Override
    public void deleteNotDirtyById(Long id) {
        deleteById(getKey(id) + NOT_DIRTY);
    }

    @Override
    public Boolean existsById(Long id) {
        String key = getKey(id);

        return (redisTemplate.hasKey(key + DIRTY) || redisTemplate.hasKey(key + NOT_DIRTY));
    }

    private Optional<MyEntityCacheEntity> findById(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public Optional<MyEntityCacheEntity> findDirtyCacheById(Long id) {
        return findById(getKey(id) + DIRTY);
    }

    @Override
    public Optional<MyEntityCacheEntity> findNotDirtyCacheById(Long id) {
        return findById(getKey(id) + NOT_DIRTY);
    }

    @Override
    public List<MyEntityCacheEntity> findDirties() {
        String simpleName = MyEntityCacheEntity.class.getSimpleName();

        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(simpleName + ":*" + DIRTY)
                .count(100)
                .build();

        ConvertingCursor<byte[], ?> convertingCursor = redisTemplate // scan 명령의 추상화
                .executeWithStickyConnection(
                        redisConnection ->
                                new ConvertingCursor<>(
                                        redisConnection.scan(scanOptions),
                                        redisTemplate.getKeySerializer()::deserialize
                                )
                );

        List<String> keys = new ArrayList<>();
        while (convertingCursor.hasNext()) { // scan된 결과를 순회하면서 전부 순회하면 알아서 redis에서 scan한 결과를 가져옴
            String key = (String) convertingCursor.next();
            keys.add(key);
        }
        convertingCursor.close();

        return new ArrayList<>(Objects.requireNonNull(redisTemplate.opsForValue().multiGet(keys)));
    }
}
