package org.spigotmc;

import java.io.File;

import net.mcavenue.redspigot.configuration.pojo.spigot.SpigotConfig;
import net.minecraft.server.MinecraftServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.springframework.beans.factory.annotation.Autowired;

public class RestartCommand extends Command {

	@Autowired
	private SpigotConfig cfg;

	public RestartCommand(String name) {
		super(name);
		this.description = "Restarts the server";
		this.usageMessage = "/restart";
		this.setPermission("bukkit.command.restart");
	}

	@Override
	public boolean execute(CommandSender sender, String currentAlias, String[] args) {
		if (testPermission(sender)) {
			MinecraftServer.getServer().processQueue.add(new Runnable() {
				@Override
				public void run() {
					restart();
				}
			});
		}
		return true;
	}

	public void restart() {
		// TODON: Autowire in config and make this work.
		restart(new File(cfg.getRestartScript()));
	}

	public static void restart(final File script) {

	}
}
