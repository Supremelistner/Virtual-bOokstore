package profile.profiler.profile.Services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FooConfiguration {
    @Bean
    System.Logger.Level feignLoggerLevel() {
        return System.Logger.Level.ALL;
    }
}
