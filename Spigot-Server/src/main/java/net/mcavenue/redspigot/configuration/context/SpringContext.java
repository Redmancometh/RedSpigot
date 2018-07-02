package net.mcavenue.redspigot.configuration.context;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import net.mcavenue.redspigot.configuration.ConfigManager;
import net.mcavenue.redspigot.configuration.pojo.SpringConfig;

@Configuration
@ComponentScan({"net.mcavenue.*", "net.minecraft.server"})
public class SpringContext {
	@Bean
	public ScheduledExecutorService executor() {
		return Executors.newScheduledThreadPool(8);
	}

	@Bean
	public Environment env() {
		ConfigManager<SpringConfig> cfgMon = new ConfigManager("spring.json", SpringConfig.class);
		cfgMon.init();
		SpringConfig cfg = cfgMon.getConfig();
		ConfigurableEnvironment env = new StandardEnvironment();
		System.out.println(cfg.getProfiles().size());
		env.setActiveProfiles(cfg.getProfiles().toArray(new String[cfg.getProfiles().size()]));
		return env;
	}
}
