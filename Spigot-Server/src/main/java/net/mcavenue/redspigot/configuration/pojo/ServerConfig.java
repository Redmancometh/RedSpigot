package net.mcavenue.redspigot.configuration.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
/**
 * For some reason @Data is having serious issues with this project. For some
 * other reason @Setter and @Getter work properly.
 * 
 * @author Redmancometh
 *
 */
public class ServerConfig {
	private boolean debug, whitelist = false, snooperEnabled, useNativeTransport, enableQuery, enableRcon, enableCommandBlock, announceAchievements = false,
			announceAdvancements = false, broadcastConsoleToOps, broadcastRconToOps, onlineMode, preventProxyConnections;
	private String resourcePack, serverName, serverId, motd, serverIp, spigotConfigDir;
	private int viewDistance = 6, maxPlayers = 20, playerIdleTimeout = 0, opPermissionLevel = 4, networkCompressionThreshold = 256,
			serverPort = 25565;
	private long maxTickTime;
	private WorldConfiguration worldConfig;
	private GameplayConfiguration gameplayConfig;

}
