package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.HashMap;

@SpringBootApplication
@RestController
public class DemoApplication {
    public static void main(String[] args) {
      SpringApplication.run(DemoApplication.class, args);
    }

	
    HashMap<String, String> database = new HashMap<String, String>();

    @GetMapping("/{dynamicKey}/get")
    public String getValue(@PathVariable String dynamicKey) {
		System.out.println("database.get(dynamicKey) >>"+database.get(dynamicKey));
      if(Boolean.parseBoolean(database.get(dynamicKey))){
		  return String.format(database.get(dynamicKey));
	  }
	  return "not found";
    }

    @PostMapping("/{dynamicKey}/post")
    public String postValue(@PathVariable String dynamicKey,@RequestBody String inputString) {
      database.put(String.format(dynamicKey), String.format(inputString));
      return String.format("data inserted as {"+dynamicKey+':'+inputString+"}");
    }
    
    @PutMapping("/{dynamicKey}/update")
    public String updateValue(@PathVariable String dynamicKey,@RequestBody String inputString) {
      database.put(dynamicKey, inputString);
      return String.format("data Updated as {"+dynamicKey+':'+inputString+"}");
    }

    @DeleteMapping("/{dynamicKey}/remove")
    public String removeValue(@PathVariable String dynamicKey) {
      database.remove(dynamicKey);
	  if(Boolean.parseBoolean(database.get(dynamicKey))){
		  return String.format("data deleted against key "+dynamicKey);
		}
		return dynamicKey+" not found";
    }
}