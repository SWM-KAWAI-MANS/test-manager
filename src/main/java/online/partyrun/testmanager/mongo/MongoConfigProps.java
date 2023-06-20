package online.partyrun.testmanager.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("de.flapdoodle.mongodb.embedded")
public class MongoConfigProps {
    private String version;
}
