package senthil.jayaraman;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource("classpath:application.properties")
public class SpringConfiguration {
	@Bean("PushNotify")
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public static PushNotify getPushNotify() {
		return new PushNotify();
	}}
