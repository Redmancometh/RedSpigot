package net.mcavenue.redspigot.configuration.context;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import jline.console.ConsoleReader;
import joptsimple.OptionSet;
import net.mcavenue.redspigot.configuration.ConfigManager;
import net.mcavenue.redspigot.configuration.pojo.ServerConfig;
import net.mcavenue.redspigot.controllers.InitializationController;
import net.mcavenue.redspigot.playerlist.RedPlayerList;
import net.minecraft.server.DataConverterManager;
import net.minecraft.server.DataConverterRegistry;
import net.minecraft.server.DedicatedServer;
import net.minecraft.server.DispenserRegistry;
import net.minecraft.server.ITickable;
import net.minecraft.server.MethodProfiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerList;
import net.minecraft.server.PropertyManager;
import net.minecraft.server.ServerPing;
import net.minecraft.server.UserCache;
import net.minecraft.server.WorldServer;

@Configuration
/*
 * TODO: Reorganize the Contexts into categorized folders. We want it all split
 * up for later when we have pre-spring load Configuration changes.
 * 
 * 
 * Also a lot of this stuff should be moved into a more specialized class. These
 * configs shouldn't be 1000 lines.
 * 
 */
public class NMSContext {

	@Bean("server-config-man")
	public ConfigManager<ServerConfig> srvConfig() {
		ConfigManager<ServerConfig> cfg = new ConfigManager("bukkit.json", ServerConfig.class);
		cfg.init();
		return cfg;
	}

	@Bean
	@Scope("prototype")
	/**
	 * Prototype scope so that it fetches every time the bean is used.
	 * 
	 * @param srvCfg
	 * @return
	 */
	public ServerConfig cfg(@Qualifier("server-config-man") ConfigManager<ServerConfig> srvCfg) {
		return srvCfg.getConfig();
	}

	@Bean(name = " world-servers")
	public List<WorldServer> worldServers() {
		return new ArrayList<WorldServer>();
	}

	@Bean(name = "world-server-array")
	public WorldServer[] worldServerArray() {
		return new WorldServer[3];
	}

	@Bean(name = "main-thread")
	public Thread mainThread(Logger logger, DedicatedServer server, ConsoleReader reader) {
		Thread thread = new Thread("Server console handler") {
			public void run() {
				jline.console.ConsoleReader bufferedreader = reader;
				// CraftBukkit end
				String s;
				try {
					// CraftBukkit start - JLine disabling compatibility
					while (!server.isStopped() && server.isRunning()) {
						// TODO: Fix this stupid jline shit. Autowire in a cfg
						if (InitializationController.useJline) {
							s = bufferedreader.readLine(">", null);
						} else {
							s = bufferedreader.readLine();
						}
						if (s != null && s.trim().length() > 0) { // Trim to
																	// filter
																	// lines
																	// which are
																	// just
																	// spaces
							server.issueCommand(s, server);
						}
						// CraftBukkit end
					}
				} catch (IOException ioexception) {
					logger.severe("Exception handling console input: \n" + ioexception.fillInStackTrace().toString());
				}

			}
		};
		return thread;
	}

	@Bean
	public ConsoleReader reader(@Qualifier("mc-logger") Logger logger) {
		ConsoleReader reader = null;
		try {
			reader = new ConsoleReader(System.in, System.out);
			reader.setExpandEvents(false);
		} catch (Throwable e) {
			try {
				System.setProperty("jline.terminal", "jline.UnsupportedTerminal");
				System.setProperty("user.language", "en");
				InitializationController.useJline = false;
				reader = new ConsoleReader(System.in, System.out);
				reader.setExpandEvents(false);
			} catch (IOException ex) {
				logger.severe(ex.fillInStackTrace().toString());
			}
		}
		return reader;
	}
	/**
	 * TODO: Wire these into the dedicated server class instead of popping them
	 * into the constructor
	 * 
	 * @param options
	 * @param yggdrasilauthenticationservice
	 * @return
	 */
	@Bean(name = "dedi-server")
	@DependsOn("property-manager")
	public DedicatedServer server(ServerConfig cfg, @Qualifier("resource-pack-sha1") String resourcePack) {
		DispenserRegistry.c();
		DedicatedServer server = new DedicatedServer();
		// For some reason the property manager was not being injected property
		// into the DedicatedServer instance
		// TODO: Make this autowire in properly. For now this fix is fine though
		setConfiguration(server, cfg, resourcePack);
		return server;
	}

	@Bean("resource-pack-sha1")
	public String resourcePack(PropertyManager propertyManager, Logger logger) {
		if (propertyManager.a("resource-pack-hash")) {
			if (propertyManager.a("resource-pack-sha1")) {
				logger.info("resource-pack-hash is deprecated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
			} else {
				logger.info("resource-pack-hash is deprecated. Please use resource-pack-sha1 instead.");
				propertyManager.getString("resource-pack-sha1", propertyManager.getString("resource-pack-hash", ""));
				propertyManager.b("resource-pack-hash");
			}
		}
		String s = propertyManager.getString("resource-pack-sha1", "");
		if (!s.isEmpty() && !DedicatedServer.l.matcher(s).matches()) {
			logger.info("Invalid sha1 for ressource-pack-sha1");
		}
		if (!propertyManager.getString("resource-pack", "").isEmpty() && s.isEmpty()) {
			logger.info(
					"You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if you change the name of the pack.");
		}
		return s;
	}

	private void setConfiguration(DedicatedServer server, ServerConfig cfg, String resourcePack) {
		server.setSpawnAnimals(cfg.getGameplayConfig().isSpawnAnimals());
		server.setSpawnNPCs(cfg.getGameplayConfig().isSpawnNpcs());
		server.setPVP(cfg.getGameplayConfig().isPvp());
		server.setAllowFlight(cfg.getGameplayConfig().isAllowFlight());
		// TODO: Fix
		server.setResourcePack(cfg.getResourcePack(), resourcePack);
		server.setMotd(cfg.getMotd());
		server.setForceGamemode(cfg.getGameplayConfig().isForceGamemode());
		server.setIdleTimeout(cfg.getPlayerIdleTimeout());
		server.setGenerateStructures(cfg.getWorldConfig().isGenerateStructures());
		/**
		 * Something tells me this R shit and the multi calls don't need to be
		 * there.
		 */
		if (server.R())
			server.c("127.0.0.1");
		else {
			server.setOnlineMode(cfg.isOnlineMode());
			server.e(cfg.isPreventProxyConnections());
			server.c(cfg.getServerIp());
		}
		server.setOnlineMode(cfg.isOnlineMode());
		server.e(cfg.isPreventProxyConnections());
		server.c(cfg.getServerIp());
	}

	@Bean(name = "player-list")
	public PlayerList list() {
		return new RedPlayerList();
	}

	@Bean(name = "property-manager")
	public PropertyManager properties(OptionSet options) {
		for (int x = 0; x < 500; x++)
			System.out.println((File) options.valueOf("config"));
		return new PropertyManager(options);
	}

	@Bean(name = "tickable-list")
	public List<ITickable> tickableList() {
		return Lists.newArrayList();
	}

	@Bean
	public MethodProfiler profiler() {
		return new MethodProfiler();
	}

	@Bean
	public ServerPing ping() {
		return new ServerPing();
	}

	@Bean
	public DataConverterManager converter() {
		return DataConverterRegistry.a();
	}

	@Bean
	public YggdrasilAuthenticationService authService() {
		return new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString());
	}

	@Bean
	public UserCache userCache(GameProfileRepository gameprofilerepository) {
		return new UserCache(gameprofilerepository, new File(".", MinecraftServer.a.getName()));
	}

	@Bean
	public MinecraftSessionService sessService(YggdrasilAuthenticationService authService) {
		return authService.createMinecraftSessionService();
	}

	@Bean
	public GameProfileRepository gameprofilerepository(YggdrasilAuthenticationService authService) {
		return authService.createProfileRepository();
	}
}
