package mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoIterable;
import mongo.config.MongoConfig;

public class Main {
    public static void main(String[] args) {
        MongoClient use = MongoConfig.use();
        MongoIterable<String> strings = use.listDatabaseNames();
        strings.forEach(System.out::println);
    }
}
