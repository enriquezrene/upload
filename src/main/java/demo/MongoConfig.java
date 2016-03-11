package demo;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 25/02/16.
 */
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    private final Logger log = Logger.getLogger(MongoConfig.class);

    private String host = "ds015508.mongolab.com";
    private Integer port = 15508;
    private String username = "demoodra";
    private String database = "demo";
    private String password = "Admin2016";

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Override
    public String getDatabaseName() {
        return database;
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        List servers = new ArrayList();
        servers.add(new ServerAddress(host, port));
        List creds = new ArrayList();
        creds.add(MongoCredential.createCredential(username, database, password.toCharArray()));
        return new MongoClient(servers, creds);
    }
}
