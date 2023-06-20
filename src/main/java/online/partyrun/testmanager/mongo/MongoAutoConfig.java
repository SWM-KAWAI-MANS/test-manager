package online.partyrun.testmanager.mongo;

import jakarta.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.AutoConfiguration;

@AutoConfiguration
public class MongoAutoConfig {
    @PostConstruct
    public void init() {
        System.setProperty("de.flapdoodle.mongodb.embedded.version", "5.0.5");
    }
}
