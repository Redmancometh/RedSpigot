package net.mcavenue.redspigot.configuration.context;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;

import jline.console.ConsoleReader;
import net.mcavenue.redspigot.controllers.InitializationController;
import net.mcavenue.redspigot.playerlist.RedPlayerList;
import net.minecraft.server.Convertable;
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
import org.springframework.context.annotation.DependsOn;

@Configuration
/*
 * TODO: Reorganize the Contexts into categorized folders. We want it all split
 * up for later when we have pre-spring load Configuration changes.
 */
public class NMSContext {

	@Bean(name = " world-servers")
	public List<WorldServer> worldServers() {
		return new ArrayList<WorldServer>();
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
	public DedicatedServer server() {
		DispenserRegistry.c();
		DedicatedServer server = new DedicatedServer();
		return server;
	}

	@Bean(name = "player-list")
	public PlayerList list() {
		return new RedPlayerList();
	}

	@Bean
	public PropertyManager properties(DedicatedServer server) {
		return new PropertyManager(server.getOptions());
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
