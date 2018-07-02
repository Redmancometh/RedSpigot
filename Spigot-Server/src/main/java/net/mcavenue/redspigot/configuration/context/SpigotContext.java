package net.mcavenue.redspigot.configuration.context;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Server.Spigot;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import net.mcavenue.redspigot.configuration.ConfigManager;
import net.mcavenue.redspigot.configuration.pojo.spigot.SpigotConfig;
import net.md_5.bungee.api.chat.BaseComponent;

@Configuration
public class SpigotContext {

	@Bean
	public Map<String, Command> commands() {
		Map<String, Command> commands = new ConcurrentHashMap();

		return commands;
	}

	@Bean
	public ConfigManager<SpigotConfig> spigotCfgMon() {
		ConfigManager<SpigotConfig> spigotCfg = new ConfigManager("spigot.json", SpigotConfig.class);
		spigotCfg.init();
		return spigotCfg;
	}

	@Bean
	@Scope(scopeName = "prototype")
	public SpigotConfig cfg(ConfigManager<SpigotConfig> spigotCfgMon) {
		return spigotCfgMon.getConfig();
	}

	@Bean
	public Spigot spigot(@Qualifier("player-view") List<CraftPlayer> playerView) {
		Spigot spigot = new Spigot() {

			@Override
			public void restart() {
				// TODO: Reimplmenent this without the damn static garbage.
				return;
			}

			@Override
			public void broadcast(BaseComponent component) {
				for (Player player : playerView) {
					player.spigot().sendMessage(component);
				}
			}

			@Override
			public void broadcast(BaseComponent... components) {
				for (Player player : playerView) {
					player.spigot().sendMessage(components);
				}
			}
		};
		return spigot;
	}

}
