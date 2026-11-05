package pet.project.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = "pet.project", excludeFilters = @ComponentScan.Filter(Controller.class))
public class AppConfig {
}
