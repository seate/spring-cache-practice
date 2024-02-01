package com.example.cacheExample.MyCommonMVC.MyEntity.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MyEntity {

    @Id
    private Long id;

    private String name;
}
