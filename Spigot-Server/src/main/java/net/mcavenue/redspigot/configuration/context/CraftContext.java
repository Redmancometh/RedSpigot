package net.mcavenue.redspigot.configuration.context;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.help.SimpleHelpMap;
import org.bukkit.craftbukkit.metadata.EntityMetadataStore;
import org.bukkit.craftbukkit.metadata.PlayerMetadataStore;
import org.bukkit.craftbukkit.metadata.WorldMetadataStore;
import org.bukkit.craftbukkit.scheduler.CraftScheduler;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.SimpleServicesManager;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.MapMaker;
@Configuration
public class CraftContext {

	/**
	 * private EntityMetadataStore entityMetadata = private PlayerMetadataStore
	 * playerMetadata = private WorldMetadataStore worldMetadata =
	 * 
	 * @return
	 */

	@Bean(name = "offline-players")
	public Map<UUID, OfflinePlayer> offlinePlayers() {
		return new MapMaker().weakValues().makeMap();
	}

	@Bean(name = "worlds")
	public Map<String, World> worlds() {
		return new LinkedHashMap<String, World>();
	}

	@Bean
	public SimpleHelpMap help(CraftServer server) {
		return new SimpleHelpMap(server);
	}

	@Bean
	public SimpleCommandMap commands(CraftServer server) {
		return new SimpleCommandMap(server);
	}

	@Bean
	public SimplePluginManager simple(CraftServer server, SimpleCommandMap commandMap) {
		return new SimplePluginManager();
	}

	@Bean
	public EntityMetadataStore entityMeta() {
		return new EntityMetadataStore();
	}

	@Bean
	public PlayerMetadataStore playerMeta() {
		return new PlayerMetadataStore();
	}

	@Bean
	public WorldMetadataStore worldMeta() {
		return new WorldMetadataStore();
	}

	@Bean
	public CraftServer cServer() {
		return new CraftServer();
	}

	@Bean
	public CraftScheduler scheduler() {
		return new CraftScheduler();
	}

	@Bean
	public SimpleServicesManager services() {
		return new SimpleServicesManager();
	}

	@Bean
	public StandardMessenger messenger() {
		return new StandardMessenger();
	}
}
