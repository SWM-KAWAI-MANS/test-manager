package online.partyrun.testmanager.mongo;

import de.flapdoodle.embed.mongo.distribution.IFeatureAwareVersion;
import de.flapdoodle.embed.mongo.distribution.Version;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class MongoAutoConfig {
    @Bean
    public IFeatureAwareVersion version() {
        return Version.V6_0_6;
    }
}
