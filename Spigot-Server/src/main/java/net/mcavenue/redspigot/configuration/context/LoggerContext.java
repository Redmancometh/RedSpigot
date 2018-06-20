package net.mcavenue.redspigot.configuration.context;

import java.util.logging.Logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerContext {
	@Bean(name = "mc-logger")
	public Logger mcLogger() {
		return Logger.getLogger("Minecraft");
	}
}
