package vttp.batch5.paf.movies.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.json.data.JsonDataSource;
import vttp.batch5.paf.movies.models.DirectorProfile;
import vttp.batch5.paf.movies.models.MYSQLMovie;
import vttp.batch5.paf.movies.repositories.MongoMovieRepository;
import vttp.batch5.paf.movies.repositories.MySQLMovieRepository;
@Service
public class MovieService {

  @Autowired
  MySQLMovieRepository mySQLMovieRepo;

  @Autowired
  MongoMovieRepository mongoMovieRepo;

  @Value("${name}")
  private String name;

  @Value("${batch}")
  private String batch;
  
  
  // TODO: Task 2
  @Transactional
  public void batchUpdate(List<MYSQLMovie> sqlMovies, List<Document> documents) {
    mySQLMovieRepo.batchInsertMovies(sqlMovies);
    mongoMovieRepo.batchInsertMovies(documents);

  }

  // TODO: Task 3
  // You may change the signature of this method by passing any number of parameters
  // and returning any type
  
  public List<DirectorProfile> getProlificDirectors() {
    List<Document> documents = this.getMongoDocs();
    List<DirectorProfile> directorProfiles = mySQLMovieRepo.getTopDirectors(documents);
    
    return directorProfiles;
  }

  public List<Document> getMongoDocs() {
    List<Document> documents =  mongoMovieRepo.getAggregate();
    return documents;
  }
  public List<DirectorProfile> Test() {
    List<Document> documents = this.getMongoDocs();
    List<DirectorProfile> directorProfiles = mySQLMovieRepo.getTopDirectors(documents);
   
    return directorProfiles;
  }

  // TODO: Task 4
  // You may change the signature of this method by passing any number of parameters
  // and returning any type
  public void generatePDFReport(List<DirectorProfile> directorProfiles) throws FileNotFoundException, JRException {

    //generate json for name and batch
    // JsonObject
    JsonObject nameJsonObj = Json.createObjectBuilder()
      .add("name",name)
      .add("batch",batch)
      .build();

    String nameContents = nameJsonObj.toString(); //your json
    
    InputStream is1 = new ByteArrayInputStream(nameContents.getBytes());
    JsonDataSource reportDs = new JsonDataSource(is1);
    // JsonArrayBuilder jab = new JsonArrayBuilder()
    //   .add()
    JsonArrayBuilder jab = Json.createArrayBuilder();

    for (DirectorProfile directorProfile : directorProfiles) {
      JsonObject jsonObj = Json.createObjectBuilder()
        .add("director",directorProfile.getDirector_name())
        .add("count",directorProfile.getMovies_count())
        .add("revenue",directorProfile.getTotal_revenue())
        .add("budget",directorProfile.getTotal_budget())
        .build();
        jab.add(jsonObj);
    }
    //the jsonarray for director profiles
    JsonArray jsonArray = jab.build();
    
    File file = new File("../data/director_movies_report.jrxml");
    // JsonDataSource reportDs = new JsonDataSource(file);

    String reportContents = jsonArray.toString(); //your json
    InputStream is2 = new ByteArrayInputStream(reportContents.getBytes());
    JsonDataSource directorsDs = new JsonDataSource(is2);
    
    Map<String,Object> params = new HashMap<>();
    params.put("DIRECTOR_TABLE_DATASET",directorsDs);

    //assume report loaded
    JasperReport report = null;

    // JasperReport rpt = (JasperReport)JRLoader.loadObject("../data/director_movies_report.jrxml");

    JasperPrint print = JasperFillManager.fillReport(report, params, reportDs);
    
    //then export pdf

  }

}
