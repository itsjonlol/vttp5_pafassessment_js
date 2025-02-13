package vttp.batch5.paf.movies.repositories;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class MongoMovieRepository {

    @Autowired
    MongoTemplate mongoTemplate;

 // TODO: Task 2.3
 // You can add any number of parameters and return any type from the method
 // You can throw any checked exceptions from the method
 // Write the native Mongo query you implement in the method in the comments
 //
 //    native MongoDB query here
 //
 public void batchInsertMovies(List<Document> documents) {

    mongoTemplate.insert(documents,"imdb");

 }

 public Boolean checkIfRecordsLoaded() {
    Query query = new Query();
    long count = mongoTemplate.count(query, "imdb");
    System.out.println("count of records is " + count);
    return count > 0;
 }

 // TODO: Task 2.4
 // You can add any number of parameters and return any type from the method
 // You can throw any checked exceptions from the method
 // Write the native Mongo query you implement in the method in the comments
 //
 //    native MongoDB query here
 //
 public void logError() {

 }

 // TODO: Task 3
 // Write the native Mongo query you implement in the method in the comments
 //
 //    native MongoDB query here
 //
 /*
  * db.imdb.aggregate([
    {
        $group: {
            _id: "$directors",
            imdb_id: {
                $push: "$imdb_id"
            }
        }
    }
])
  */
    public List<Document> getAggregate() {
        MatchOperation matchStage = Aggregation.match(Criteria.where("directors").regex("David Leitch","i"));
        
        GroupOperation groupStage = Aggregation.group("directors")
            .push("$imdb_id").as("imdb_id");

        Aggregation pipeline = Aggregation.newAggregation(
            matchStage,
            groupStage

        );

        AggregationResults<Document> results = mongoTemplate.aggregate(pipeline, "imdb",Document.class);
        List<Document> documents = results.getMappedResults();
        System.out.println(documents.size());
        return documents;
    }


}
