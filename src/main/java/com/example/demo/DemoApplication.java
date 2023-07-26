package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.*;

@SpringBootApplication
@RestController
public class DemoApplication <T> {
    public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
    }

	
    private final Map<String, Set<T>> database = new HashMap<>();


    @PostMapping("/{dynamicKey}/add")
      public ResponseEntity<String> add(@PathVariable String dynamicKey, @RequestBody T value) {
          Set<T> values = database.computeIfAbsent(dynamicKey, k -> new HashSet<>());
          values.add(value);
          return ResponseEntity.ok("Object added to the collection with key: " + dynamicKey);
      }
  
    @GetMapping("/clear")
      public ResponseEntity<String> clear() {
          database.clear();
          return ResponseEntity.ok("Collection cleared.");
      }
  
      @GetMapping("/contains/{key}")
      public ResponseEntity<Boolean> contains(@PathVariable String key) {
          boolean containsKey = database.containsKey(key);
          return ResponseEntity.ok(containsKey);
      }
  
      @GetMapping("/isEmpty")
      public ResponseEntity<Boolean> isEmpty() {
          boolean empty = database.isEmpty();
          return ResponseEntity.ok(empty);
      }
  
      @GetMapping("/iterator")
      public ResponseEntity<Iterator<Map.Entry<String, Set<T>>>> iterator() {
          Set<Map.Entry<String, Set<T>>> entrySet = database.entrySet();
          return ResponseEntity.ok(entrySet.iterator());
      }
  
      @DeleteMapping("/{dynamicKey}/remove")
      public ResponseEntity<String> remove(@PathVariable String dynamicKey, @RequestBody T value) {
          Set<T> values = database.get(dynamicKey);
          if (values != null && values.contains(value)) {
              values.remove(value);
              return ResponseEntity.ok("Object removed from the collection with key: " + dynamicKey);
          } else {
              return ResponseEntity.notFound().build();
          }
      }
  
      @GetMapping("/size")
      public ResponseEntity<Integer> size() {
          int size = database.size();
          return ResponseEntity.ok(size);
      }
   
}