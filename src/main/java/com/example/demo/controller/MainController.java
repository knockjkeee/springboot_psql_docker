package com.example.demo.controller;

import com.example.demo.domain.Item;
import com.example.demo.repo.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class MainController {

    @Autowired
    ItemRepo itemRepo;

    //read all data
    @GetMapping
    public List<Item> readAllItems() {
        List<Item> all = itemRepo.findAll();
        return all;
    }

    //read by id
    @GetMapping("/{id}")
    public ResponseEntity<Item> readItemById(@PathVariable Long id) {
        Optional<Item> item = itemRepo.findById(id);
        if (!item.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(item.get());
        }

    }

    //create by id
    @PostMapping("/")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        Item newItem = itemRepo.save(item);
        if (newItem == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(newItem.getId())
                    .toUri();
            return ResponseEntity.created(uri).body(newItem);
        }

    }

    //update by id
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@RequestBody Item item, @PathVariable Long id){
        Optional<Item> itemUpdate = itemRepo.findById(id);
        if (!itemUpdate.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            Item newItem = itemUpdate.get();
            if (item.getName() != null) {
                newItem.setName(item.getName());
            }
            if (item.getAge() != 0) {
                newItem.setAge(item.getAge());
            }
            itemRepo.save(newItem);
            return ResponseEntity.ok(newItem);
        }
    }

    //delete by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable Long id) {
        Optional<Item> item = itemRepo.findById(id);
        itemRepo.delete(item.get());
        return ResponseEntity.noContent().build();
    }


    //test
    @RequestMapping(value = "/usr/{name}", method = RequestMethod.GET)
    public @ResponseBody
    String test(@PathVariable("name") String name,
                @RequestParam("id") String id
    ) {
        return name + " " + id;
    }
}
