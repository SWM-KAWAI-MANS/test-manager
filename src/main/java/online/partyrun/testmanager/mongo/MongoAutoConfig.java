package online.partyrun.testmanager.mongo;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@AutoConfiguration
@PropertySource("classpath:embedded-mongo.yml")
public class MongoAutoConfig {}
