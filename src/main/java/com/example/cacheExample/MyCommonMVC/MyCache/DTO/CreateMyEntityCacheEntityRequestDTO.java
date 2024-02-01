package com.example.cacheExample.MyCommonMVC.MyCache.DTO;

import com.example.cacheExample.MyCommonMVC.MyCache.Entity.MyEntityCacheEntity;
import com.example.cacheExample.MyCommonMVC.MyEntity.DTO.CreateEntityRequestDTO;
import com.example.cacheExample.MyCommonMVC.MyEntity.Entity.MyEntity;
import lombok.Data;

@Data
public class CreateMyEntityCacheEntityRequestDTO {

    private Long id;

    private String name;

    public CreateMyEntityCacheEntityRequestDTO(MyEntity myEntity) {
        this.id = myEntity.getId();
        this.name = myEntity.getName();
    }

    public CreateMyEntityCacheEntityRequestDTO(CreateEntityRequestDTO createEntityRequestDTO) {
        this.id = createEntityRequestDTO.getId();
        this.name = createEntityRequestDTO.getName();
    }

    public MyEntityCacheEntity toEntity() {
        return new MyEntityCacheEntity(this.id, this.name);
    }


}
