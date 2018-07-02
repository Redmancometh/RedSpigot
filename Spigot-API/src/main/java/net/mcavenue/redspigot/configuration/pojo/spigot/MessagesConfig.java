package net.mcavenue.redspigot.configuration.pojo.spigot;

import lombok.Data;
import lombok.Getter;

@Data
/**
 * TODO: Add a broadcaster system to this so people don't need plugins for it on
 * *every fucking server*
 * 
 * @author Redmancometh
 *
 */
@Getter
public class MessagesConfig {
	private String whitelist = "You are not whitelisted on this server!", unknownCommand = "Unknown command. Type \"/help\" for help.",
			serverFull = "The server is full!", outdatedClient = "Outdated client! Please use {0}",
			outdatedServer = "Outdated server! I'm still on {0}", restart = "Server is restarting";
}
