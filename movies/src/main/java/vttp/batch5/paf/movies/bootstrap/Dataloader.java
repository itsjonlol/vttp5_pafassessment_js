package vttp.batch5.paf.movies.bootstrap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.batch5.paf.movies.models.MYSQLMovie;
import vttp.batch5.paf.movies.repositories.MongoMovieRepository;
import vttp.batch5.paf.movies.repositories.MySQLMovieRepository;

@Component
public class Dataloader implements CommandLineRunner {

  @Autowired
  MySQLMovieRepository mySQLMovieRepo;

  @Autowired
  MongoMovieRepository mongoMovieRepo;
  
  //TODO: Task 2

  @Override
  public void run(String... args) throws Exception {
    // TODO Auto-generated method stub

    //task 2.1

    System.out.println(mongoMovieRepo.checkIfRecordsLoaded());

    System.out.println("test");
    List<MYSQLMovie> mysqlMovies = new ArrayList<>();
    List<Document> documents = new ArrayList<>();

    //step 1 -> check to see if data has been loaded into either of the 2 databases

    ZipFile zipFile = new ZipFile("../data/movies_post_2010.zip");
    

    Enumeration<? extends ZipEntry> entries = zipFile.entries();
     List<String> listOfStrings = new ArrayList<String>();
     JsonArrayBuilder jab = Json.createArrayBuilder();

    while(entries.hasMoreElements()){
        ZipEntry entry = entries.nextElement();
        InputStream stream = zipFile.getInputStream(entry);
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(stream)); 
      

      String inputStr;
      while ((inputStr = streamReader.readLine()) != null) {
        // listOfStrings.add(inputStr);
        jab.add(inputStr);
        
      }
      
    
        
        // while (stream.available() > 0) {
          
        //   try {
        //     System.out.println(stream.available());
        //     JsonReader reader = Json.createReader(stream);
        //     JsonObject jsonObject = reader.readObject();

            
        //   } catch (Exception e) {
        //     System.out.println(e.getMessage());
        //   }}
          
       
        }
        JsonArray jsonArray = jab.build();
        
        System.out.println(jsonArray.size());
        if (!mongoMovieRepo.checkIfRecordsLoaded() || !mySQLMovieRepo.checkIfRecordsLoaded()) {
          for ( int i = 0; i< jsonArray.size();i++) {
          
          
          


            String jsonObjectString = jsonArray.getString(i);
            JsonReader r = Json.createReader(new StringReader(jsonObjectString));
            
            JsonObject jsonResult = r.readObject();
            // Document document = new Document();
            String imdb_id;
            try {
              imdb_id = jsonResult.getString("imdb_id","");
            } catch (Exception ex) {
              imdb_id = "";
            }
            Float vote_average;
            try {
              vote_average = (float) jsonResult.getJsonNumber("vote_average").doubleValue();
            } catch (Exception ex ) {
              vote_average = 0.0f;
            }
            Integer vote_count;
            try {
              vote_count = jsonResult.getInt("vote_count");
            } catch (Exception ex) {
              vote_count = 0;
            }
            String release_date = jsonResult.getString("release_date","");
            try {
              release_date = jsonResult.getString("release_date","");
            } catch (Exception ex) {
              release_date = "";
            }
            Integer revenue;
            try {
              revenue = jsonResult.getInt("revenue");
        
            } catch ( Exception ex) {
              revenue = 0;
            }
            Integer budget;
            try {
              budget = jsonResult.getInt("budget");
        
            } catch ( Exception ex) {
              budget = 0;
            }
            Integer runtime;
            try {
              runtime = jsonResult.getInt("runtime");
        
            } catch ( Exception ex) {
              runtime = 0;
            }
            MYSQLMovie mysqlMovie = new MYSQLMovie();
            mysqlMovie.setImdb_id(imdb_id);
            mysqlMovie.setVote_average(vote_average);
            mysqlMovie.setVote_count(vote_count);
            mysqlMovie.setRelease_date(release_date);
            mysqlMovie.setRevenue(revenue);
            mysqlMovie.setBudget(budget);
            mysqlMovie.setRevenue(revenue);
            mysqlMovie.setRuntime(runtime);
            mysqlMovies.add(mysqlMovie);
            
  
            Document document = new Document();
            document.put("imdb_id",imdb_id);
            document.put("title", jsonResult.getString("title",""));
            document.put("directors",jsonResult.getString("director",""));
            document.put("overview",jsonResult.getString("overview",""));
            document.put("tagline",jsonResult.getString("tagline"));
            document.put("genres",jsonResult.getString("genres"));
            Integer imdb_rating;
  
            try {
              imdb_rating = jsonResult.getInt("imdb_rating");
            } catch (Exception e) {
              imdb_rating = 0;
            }
            Integer imdb_votes;
            document.put("imdb_rating",imdb_rating);
  
            try {
              imdb_votes = jsonResult.getInt("imdb_votes");
            } catch (Exception e) {
              imdb_votes = 0;
            }
            document.put("imdb_rating",imdb_votes);
            documents.add(document);
  
            if (i % 25 == 0) {
              mySQLMovieRepo.batchInsertMovies(mysqlMovies);
              mongoMovieRepo.batchInsertMovies(documents);
              mysqlMovies = new ArrayList<>();
              documents = new ArrayList<>();
            }
          
  
            
  
            
            
  
            // Document.parse(jsonResult.toString());
            // System.out.println(jsonResult.toString());
            // JsonObject jsonObject = jsonArray.getJsonObject(i);
            // System.out.println(jsonObject.toString());
          }
        
          
      }
        }
        
        
    

    //step 2 read the json file

    // File file = new FileReader("data/movies_post_2010.json");
    // ClassPathResource resource = new ClassPathResource("movies_post_2010.json");
    // InputStream is = resource.getInputStream();
    // BufferedReader br = new BufferedReader(new InputStreamReader(is));
    // JsonReader reader = Json.createReader(is);
    // JsonObject jsonObject = reader.readObject();
    // String inputLine;
    // int count = 0;

    // while (count <2) {
    //   inputLine = br.readLine();
    //   // Document.parse(inputLine);
    //   System.out.println(inputLine);
    //   count++;

    // }
    
    // while ((inputLine = br.readLine()) != null || count <5) 
    // {   
    //   System.out.println(inputLine);
    //   Document document = new Document();
    //   count++;
    //   // Document.parse(inputLine);
    //   //  JsonReader r = Json.createReader(new StringReader(inputLine));
    //    JsonReader r = Json.createReader(new StringReader(inputLine));
    //    JsonObject jsonResult = r.readObject();
    //   //  try {
    //   //   JsonObject jsonResult = r.readObject();
    //   //   count++;
    //   //  } catch(Exception ex) {
    //   //   System.out.println(ex.getMessage());
    //   //   System.out.println(count);
    //   //  }
        
    //     // System.out.printf(">>> in JSON-P: %s\n",jsonResult.toString());
    //     // Document document = Document.parse(inputLine);
    //     // System.out.println(document.toJson());
    // }
    // br.close();
    
    
   



    
  }


