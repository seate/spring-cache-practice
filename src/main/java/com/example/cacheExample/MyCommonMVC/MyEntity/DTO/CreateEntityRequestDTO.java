package com.example.cacheExample.MyCommonMVC.MyEntity.DTO;

import com.example.cacheExample.MyCommonMVC.MyEntity.Entity.MyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEntityRequestDTO {

    private Long id;

    private String name;

    public MyEntity toMyEntity() {
        return new MyEntity(id, name);
    }
}
