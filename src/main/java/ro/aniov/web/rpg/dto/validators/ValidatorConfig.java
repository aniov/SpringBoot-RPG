package ro.aniov.web.rpg.dto.validators;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * Created by Marius on 1/27/2017.
 */

/** We need it to use @Validated, our scope is to use custom validation at @RequestParam level in Controllers*/
@Configuration
public class ValidatorConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
