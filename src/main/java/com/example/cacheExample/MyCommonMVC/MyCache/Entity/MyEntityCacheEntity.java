package com.example.cacheExample.MyCommonMVC.MyCache.Entity;


import com.example.cacheExample.MyCommonMVC.MyEntity.Entity.MyEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Getter
@NoArgsConstructor
@AllArgsConstructor
//@RedisHash(value = "MyEntityCacheEntity", timeToLive = 60 * 10)
public class MyEntityCacheEntity {

    @Id
    private Long id;

    private String tempUUID;

    private String name;

    private String description;

    public MyEntityCacheEntity(Long id, String name) {
        this.id = id;
        this.name = name;
        this.description = "defaultDescription";
    }

    public MyEntity toMyEntity() {
        return new MyEntity(id, name);
    }

    public void updateDTO(String newName) {
        this.name = newName;
        this.description = "updatedDescription";
    }
}
