package net.mcavenue.redspigot;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.mcavenue.redspigot.configuration.ConfigManager;
import net.mcavenue.redspigot.configuration.context.BukkitOptionsContext;
import net.mcavenue.redspigot.configuration.context.SpringContext;
import net.mcavenue.redspigot.configuration.pojo.SpringConfig;

public class RedSpigot {
	private static ConfigManager<SpringConfig> cfgMon = new ConfigManager("spring.json", SpringConfig.class);
	private static AnnotationConfigApplicationContext context;
	public static void main(String[] args) {
		BukkitOptionsContext.args = args;
		cfgMon.init();
		SpringConfig cfg = cfgMon.getConfig();
		System.setProperty("spring.profiles.active", String.join(",", cfg.getProfiles()));
		context = new AnnotationConfigApplicationContext(SpringContext.class);
		System.out.println(context == null);
		System.out.println(context.getEnvironment().getActiveProfiles().length);
	}

	public static AnnotationConfigApplicationContext context() {
		System.out.println("CONTEXT IS NULL: " + (context == null));
		return context;
	}
}
