package online.partyrun.testmanager.mongo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@PropertySource("classpath:application.properties")
public class MongoConfigProps {
    private String version;
}
