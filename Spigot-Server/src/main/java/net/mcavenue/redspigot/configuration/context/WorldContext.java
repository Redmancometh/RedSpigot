package net.mcavenue.redspigot.configuration.context;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.minecraft.server.Convertable;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldLoaderServer;

@Configuration
public class WorldContext {

	@Bean
	@Qualifier("world-load-server")
	public Convertable worldLoadServer(@Qualifier("world-container") File worldContainer, MinecraftServer server) {
		Convertable converter = new WorldLoaderServer(worldContainer, server.dataConverterManager);
		return converter;
	}

	@Bean("world-container")
	public File worldContainer(MinecraftServer server, @Qualifier("bukkit-cfg") YamlConfiguration cfg) {
		File container = null;
		if (server.universe != null) {
			return server.universe;
		}
		if (container == null) {
			container = new File(cfg.getString("settings.world-container", "."));
		}
		return container;
	}

}
