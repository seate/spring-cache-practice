package com.example.cacheExample.MyCommonMVC.MyEntity.Repository;


import com.example.cacheExample.MyCommonMVC.MyEntity.Entity.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {
}
