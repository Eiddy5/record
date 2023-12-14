package mongo.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoConfig {

    public static MongoClient use(){
        String uri = "mongodb://127.0.0.1:27017";
        return MongoClients.create(uri);
    }
    public static MongoClient use(String uri){
        return MongoClients.create(uri);
    }
}
