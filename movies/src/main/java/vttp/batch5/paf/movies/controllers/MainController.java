package vttp.batch5.paf.movies.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class MainController {

  // TODO: Task 3
   
    @GetMapping("/summary/{count}")
    public ResponseEntity<?> getProlificDirectors(@RequestParam("count") Integer count) {
        
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(null);
    }
    

  
  // TODO: Task 4


}
