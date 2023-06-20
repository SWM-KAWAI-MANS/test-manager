package online.partyrun.testmanager.mongo;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@AutoConfiguration
@ConfigurationProperties("de.flapdoodle.mongodb.embedded")
public class MongoAutoConfig {
    private String version;
}
