package vttp.batch5.paf.movies.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp.batch5.paf.movies.models.DirectorProfile;
import vttp.batch5.paf.movies.models.MYSQLMovie;

@Repository
public class MySQLMovieRepository {

  @Autowired
  JdbcTemplate template;
  // TODO: Task 2.3
  // You can add any number of parameters and return any type from the method

  public static final String SQL_BATCH_INSERT_MOVIES = "insert into imdb values(?,?,?,?,?,?,?);";
  public static final String SQL_COUNT_RECORDS = "select count(*) as n_records from imdb";
  public static final String SQL_GET_SUMMARY= """
  select sum(revenue) total_revenue ,sum(budget) total_budget,sum((revenue-budget)) margin from imdb
  where imdb_id in ("tt5463162","tt6806448");
      """;;
    
  public void batchInsertMovies(List<MYSQLMovie> mysqlMovies) {
    List<Object[]> params = mysqlMovies.stream()
            .map(li -> {
                Object[] rec = new Object[7];
                rec[0] = li.getImdb_id();
                rec[1] = li.getVote_average();
                rec[2] = li.getVote_count();
                rec[3] = li.getRelease_date();
                rec[4] = li.getRevenue();
                rec[5] = li.getBudget();
                rec[6] = li.getRuntime();
                return rec;
            }).toList();
    try {
      template.batchUpdate(SQL_BATCH_INSERT_MOVIES, params);
    } catch (Exception ex) {
      
      ex.getMessage();
    }
    
    
    
   
  }

  public Boolean checkIfRecordsLoaded() {
    SqlRowSet rs = template.queryForRowSet(SQL_COUNT_RECORDS);
    if (!rs.next()) {
      return false;
    }
    return true;
  }
  
  // TODO: Task 3

  public List<DirectorProfile> getTopDirectors(List<Document> documents) {
    
    // List<String> imdb_ids = documents.get(0).getList("imdb_id",String.class);
    SqlRowSet rs= template.queryForRowSet(SQL_GET_SUMMARY);
    List<DirectorProfile> directorProfiles = new ArrayList<>();
    String name = documents.get(0).getString("_id");
    List<String> imdb_ids = documents.get(0).getList("imdb_id",String.class);
  





    

    while (rs.next()) {
      DirectorProfile directorProfile = new DirectorProfile();
      directorProfile.setDirector_name(name);
      directorProfile.setMovies_count(imdb_ids.size());
      directorProfile.setTotal_revenue(rs.getBigDecimal("total_revenue"));
      
      
      directorProfile.setTotal_budget(rs.getBigDecimal("total_budget"));
      directorProfile.setMargin(rs.getBigDecimal("margin"));
      directorProfiles.add(directorProfile);
    }
    System.out.println(directorProfiles.size());
    return directorProfiles;

    
  }

}
