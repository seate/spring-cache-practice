package com.example.cacheExample.MyCommonMVC.Controller;

import com.example.cacheExample.MyCommonMVC.MyCache.DTO.UpdateMyEntityCacheEntityRequestDTO;
import com.example.cacheExample.MyCommonMVC.MyEntity.DTO.CreateEntityRequestDTO;
import com.example.cacheExample.MyCommonMVC.Service.MyCommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/myEntity")
public class MyCommonController {

    private final MyCommonService myCommonService;

    @PostMapping
    public ResponseEntity<Void> saveMyEntity(@RequestBody CreateEntityRequestDTO createMyEntityRequestDTO) {
        myCommonService.save(createMyEntityRequestDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyEntity(@PathVariable Long id) {
        myCommonService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> findMyEntity(@PathVariable Long id) {
        return new ResponseEntity<>(myCommonService.find(id).getName(), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMyEntity(@PathVariable Long id, @RequestBody UpdateMyEntityCacheEntityRequestDTO updateMyEntityCacheEntityRequestDTO) {
        myCommonService.update(id, updateMyEntityCacheEntityRequestDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
