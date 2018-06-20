package net.mcavenue.redspigot.configuration.context;

import java.util.List;

import org.bukkit.Server.Spigot;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.md_5.bungee.api.chat.BaseComponent;

@Configuration
public class SpigotContext {

	@Bean
	public Spigot spigot(@Qualifier("player-view") List<CraftPlayer> playerView) {
		Spigot spigot = new Spigot() {

			@Override
			public YamlConfiguration getConfig() {
				return org.spigotmc.SpigotConfig.config;
			}

			@Override
			public void restart() {
				org.spigotmc.RestartCommand.restart();
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
