package online.partyrun.testmanager.mongo;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@AutoConfiguration
@EnableConfigurationProperties(MongoConfigProps.class)
public class MongoAutoConfig {}
