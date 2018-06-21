package net.mcavenue.redspigot.configuration.context;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import joptsimple.OptionSet;
import net.minecraft.server.Convertable;
import net.minecraft.server.DataConverterManager;
import net.minecraft.server.IDataManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerNBTManager;
import net.minecraft.server.WorldLoaderServer;
import net.minecraft.server.WorldServer;
import net.minecraft.server.WorldSettings;

@Configuration
public class WorldContext {

	@Bean("world-load-server")
	public Convertable worldLoadServer(@Qualifier("world-container") File worldContainer, DataConverterManager dataConverterManager) {
		Convertable converter = new WorldLoaderServer(worldContainer, dataConverterManager);
		return converter;
	}

	@Bean("world-name")
	public String worldName(OptionSet options) {
		return (String) options.valueOf("world");
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

	/**
	 * this.a(s); this.b("menu.loadingLevel"); int worldCount = 3; for (int j =
	 * 0; j < worldCount; ++j) { WorldServer world; byte dimension = 0;
	 * 
	 * if (j == 1) { if (getAllowNether()) { dimension = -1; } else { continue;
	 * } }
	 * 
	 * if (j == 2) { if (server.getAllowEnd()) { dimension = 1; } else {
	 * continue; } }
	 * 
	 * String worldType =
	 * org.bukkit.World.Environment.getEnvironment(dimension).toString().toLowerCase();
	 * String name = (dimension == 0) ? s : s + "_" + worldType;
	 * 
	 * org.bukkit.generator.ChunkGenerator gen = this.server.getGenerator(name);
	 * WorldSettings worldsettings = new WorldSettings(i, this.getGamemode(),
	 * this.getGenerateStructures(), this.isHardcore(), worldtype);
	 * worldsettings.setGeneratorSettings(s2);
	 * 
	 * 
	 */

	/**
	 * @Bean public IDataManager iData(@Qualifier("world-container") File
	 *       worldContainer, OptionSet options, DataConverterManager
	 *       dataConverter) { IDataManager idatamanager = new
	 *       ServerNBTManager(worldContainer, (String) options.valueOf("world"),
	 *       true, dataConverter); return idatamanager; }
	 **/
}
