package online.partyrun.testmanager.mongo;

import jakarta.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@AutoConfiguration
@PropertySource("classpath:embedded-mongo.properties")
public class MongoAutoConfig {
}
