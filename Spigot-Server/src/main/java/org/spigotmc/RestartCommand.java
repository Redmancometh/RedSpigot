package org.spigotmc;

import java.io.File;
import net.minecraft.server.MinecraftServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RestartCommand extends Command {

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

	public static void restart() {
		restart(new File(SpigotConfig.restartScript));
	}

	public static void restart(final File script) {
		// TODO: This restart command sucked dick. Make it actually work.
	}
}
