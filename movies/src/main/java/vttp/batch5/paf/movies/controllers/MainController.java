package vttp.batch5.paf.movies.controllers;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vttp.batch5.paf.movies.models.DirectorProfile;
import vttp.batch5.paf.movies.services.MovieService;


@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    MovieService movieService;
  // TODO: Task 3
   
    @GetMapping("/summary")
    public ResponseEntity<?> getProlificDirectors(@RequestParam("count") Integer count) {
        List<DirectorProfile> directorProfiles = movieService.getProlificDirectors();
        return ResponseEntity.status(200).header("Content-Type", "application/json").body(directorProfiles);
    }
    

    @GetMapping("/test")
    public ResponseEntity<?> getMethodName() {
      List<Document> documents =  movieService.getMongoDocs();
      return ResponseEntity.status(200).header("Content-Type", "application/json").body(documents);
    }

    @GetMapping("/test2")
    public ResponseEntity<?> getMethodName2() {
      List<DirectorProfile> directorProfiles =  movieService.Test();
      return ResponseEntity.status(200).header("Content-Type", "application/json").body(directorProfiles);
    }
    
  
  // TODO: Task 4
    @GetMapping("/summary/pdf")
    public ResponseEntity<?> getMethodName(@RequestParam String param) {
        return null;
    }
    

}
