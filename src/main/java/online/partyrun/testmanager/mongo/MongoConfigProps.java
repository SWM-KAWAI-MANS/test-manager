package online.partyrun.testmanager.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("de.flapdoodle.mongodb.embedded")
public class MongoConfigProps {
    private String version;
}
