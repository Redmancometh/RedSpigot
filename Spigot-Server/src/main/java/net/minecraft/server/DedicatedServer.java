package net.minecraft.server;

import com.google.common.collect.Lists;

import net.mcavenue.redspigot.configuration.pojo.ServerConfig;
import net.mcavenue.redspigot.event.EventManager;
import net.mcavenue.redspigot.playerlist.RedPlayerList;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import java.util.logging.Logger;

// CraftBukkit start
import java.io.PrintStream;

import org.apache.logging.log4j.Level;

import org.bukkit.craftbukkit.LoggerOutputStream;
import org.bukkit.craftbukkit.SpigotTimings; // Spigot
import org.bukkit.event.server.ServerCommandEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.bukkit.craftbukkit.util.Waitable;
import org.bukkit.event.server.RemoteServerCommandEvent;
// CraftBukkit end

public class DedicatedServer extends MinecraftServer implements IMinecraftServer {

	public static final Pattern l = Pattern.compile("^[a-fA-F0-9]{40}$");
	private final List<ServerCommand> serverCommandQueue = Collections.synchronizedList(Lists.<ServerCommand>newArrayList());
	private RemoteStatusListener n;
	public final RemoteControlCommandListener remoteControlCommandListener = new RemoteControlCommandListener(this);
	private RemoteControlListener p;
	@Autowired
	private EventManager events;
	@Autowired
	private ServerConfig srvCfg;
	// I neither know nor care why there were 2 seperate logging systems.
	@Autowired
	@Qualifier("mc-logger")
	private Logger LOGGER;
	private boolean generateStructures;
	private EnumGamemode t;
	private boolean u;
	// CraftBukkit start - Signature changed
	public DedicatedServer() {
		super();
	}
	@Autowired
	@Qualifier("main-thread")
	private Thread mainThread;

	/**
	 * TODO: This should be like 15 methods, and almost all of them can go in
	 * configuration
	 */
	public boolean init() throws IOException { // CraftBukkit - decompile error

		// CraftBukkit start - TODO: handle command-line logging arguments

		java.util.logging.Logger global = java.util.logging.Logger.getLogger(this.getClass().getName());
		global.setUseParentHandlers(false);
		for (java.util.logging.Handler handler : global.getHandlers()) {
			global.removeHandler(handler);
		}
		global.addHandler(new org.bukkit.craftbukkit.util.ForwardLogHandler());

		final org.apache.logging.log4j.core.Logger logger = ((org.apache.logging.log4j.core.Logger) LogManager.getRootLogger());
		for (org.apache.logging.log4j.core.Appender appender : logger.getAppenders().values()) {
			if (appender instanceof org.apache.logging.log4j.core.appender.ConsoleAppender) {
				logger.removeAppender(appender);
			}
		}
		new Thread(new org.bukkit.craftbukkit.util.TerminalConsoleWriterThread(System.out, this.reader)).start();
		System.setOut(new PrintStream(new LoggerOutputStream(logger, Level.INFO), true));
		System.setErr(new PrintStream(new LoggerOutputStream(logger, Level.WARN), true));
		// CraftBukkit end
		mainThread.setDaemon(true);
		mainThread.start();
		LOGGER.info("Starting minecraft server version 1.12.2");
		if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
			LOGGER.info("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
		}

		LOGGER.info("Loading properties");
		File f = new File("");
		System.out.println("Application context: " + f.getAbsolutePath());
		int i = srvCfg.getGameplayConfig().getGamemode();
		this.t = WorldSettings.a(i);
		LOGGER.info("Default game type: " + this.t);
		InetAddress inetaddress = null;

		if (!this.getServerIp().isEmpty()) {
			inetaddress = InetAddress.getByName(this.getServerIp());
		}

		if (this.P() < 0) {
			this.setPort(srvCfg.getServerPort());
		}
		// Spigot start
		org.spigotmc.SpigotConfig.init((File) getOptions().valueOf("spigot-settings"));
		org.spigotmc.SpigotConfig.registerCommands();
		// Spigot end
		LOGGER.info("Generating keypair");
		this.a(MinecraftEncryption.b());
		String ip = this.getServerIp().isEmpty() ? "*" : this.getServerIp();
		int port = srvCfg.getServerPort();
		LOGGER.info("Starting Minecraft server on " + ip + ":" + port);
		java.util.logging.Logger.getLogger(this.getClass().getName()).info("Line 162");
		if (!org.spigotmc.SpigotConfig.lateBind) {
			try {
				java.util.logging.Logger.getLogger(this.getClass().getName()).info("About to call an() a");
				this.an().a(inetaddress, this.P());
				java.util.logging.Logger.getLogger(this.getClass().getName()).info("Finished an.a");
			} catch (IOException ioexception) {
				ioexception.printStackTrace();
				LOGGER.severe("**** FAILED TO BIND TO PORT!");
				LOGGER.severe("The exception was: \n" + ioexception.fillInStackTrace().toString());
				LOGGER.severe("Perhaps a server is already running on that port?");
				return false;
			}
		}
		// CraftBukkit start
		// this.a((PlayerList) (new DedicatedPlayerList(this))); // Spigot -
		// moved up
		server.loadPlugins();
		server.enablePlugins(org.bukkit.plugin.PluginLoadOrder.STARTUP);
		// CraftBukkit end

		if (!this.getOnlineMode()) {
			LOGGER.info("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
		}

		if (this.aS()) {
			this.getUserCache().c();
		}

		// For player conversion. Temporarily disabled, and not likely to be
		// used often.
		// if (!NameReferencingFileConverter.a(this.propertyManager)) {
		// return false;
		// } else {

		if (this.S() == null) {
			this.setWorld(srvCfg.getWorldConfig().getLevelName());
		}
		String s = srvCfg.getWorldConfig().getLevelSeed();
		String s1 = srvCfg.getWorldConfig().getLevelType();
		String s2 = srvCfg.getWorldConfig().getGeneratorSettings();
		long k = (new Random()).nextLong();
		if (!s.isEmpty()) {
			try {
				long l = Long.parseLong(s);

				if (l != 0L) {
					k = l;
				}
			} catch (NumberFormatException numberformatexception) {
				k = (long) s.hashCode();
			}
		}
		WorldType worldtype = WorldType.getType(s1);
		if (worldtype == null) {
			worldtype = WorldType.NORMAL;
		}
		this.getEnableCommandBlock();
		this.q();
		this.getSnooperEnabled();
		this.aG();
		this.c(srvCfg.getWorldConfig().getMaxBuildHeight());
		this.c((this.getMaxBuildHeight() + 8) / 16 * 16);
		this.c(MathHelper.clamp(this.getMaxBuildHeight(), 64, 256));
		TileEntitySkull.a(this.getUserCache());
		TileEntitySkull.a(this.az());
		UserCache.a(this.getOnlineMode());
		LOGGER.info("Preparing level " + this.S());
		this.a(this.S(), this.S(), k, worldtype, s2);
		handleProperties();
		// CraftBukkit end

		if (org.spigotmc.SpigotConfig.lateBind) {
			try {
				this.an().a(inetaddress, this.P());
			} catch (IOException ioexception) {
				LOGGER.severe("**** FAILED TO BIND TO PORT!");
				LOGGER.severe("The exception was: \n" + ioexception.fillInStackTrace().toString());
				LOGGER.severe("Perhaps a server is already running on that port?");
				return false;
			}
		}
		Items.a.a(CreativeModeTab.g, NonNullList.a());
		return true;
		// }
	}

	public void handleProperties() {
		long j = System.nanoTime();
		long i1 = System.nanoTime() - j;
		String s3 = String.format("%.3fs", new Object[]{Double.valueOf((double) i1 / 1.0E9D)});
		LOGGER.info("Done ({})! For help, type \"help\" or \"?\"".replace("{}", s3));
		this.worlds.get(0).getGameRules().set("announceAdvancements", srvCfg.isAnnounceAdvancements() + ""); // CraftBukkit
		if (srvCfg.isEnableQuery()) {
			LOGGER.info("Starting GS4 status listener");
			this.n = new RemoteStatusListener(this);
			this.n.a();
		}

		if (srvCfg.isEnableRcon()) {
			LOGGER.info("Starting remote control listener");
			this.p = new RemoteControlListener(this);
			this.p.a();
			this.remoteConsole = new org.bukkit.craftbukkit.command.CraftRemoteConsoleCommandSender(this.remoteControlCommandListener); // CraftBukkit
		}

	}

	public String aO() {
		throw new IllegalStateException("Don't call aO it's just a vestigial method leftover from old NMS.");
	}

	public void setGamemode(EnumGamemode enumgamemode) {
		super.setGamemode(enumgamemode);
		this.t = enumgamemode;
	}

	public boolean getGenerateStructures() {
		return this.isGenerateStructures();
	}

	public EnumGamemode getGamemode() {
		return this.t;
	}

	public EnumDifficulty getDifficulty() {
		return EnumDifficulty.getById(srvCfg.getGameplayConfig().getDifficulty());
	}

	public boolean isHardcore() {
		return srvCfg.getGameplayConfig().isHardcore();
	}

	public CrashReport b(CrashReport crashreport) {
		crashreport = super.b(crashreport);
		crashreport.g().a("Is Modded", new CrashReportCallable() {
			public String a() throws Exception {
				String s = DedicatedServer.this.getServerModName();
				return !"vanilla".equals(s) ? "Definitely; Server brand changed to \'" + s + "\'" : "Unknown (can\'t tell)";
			}

			public Object call() throws Exception {
				return this.a();
			}
		});
		crashreport.g().a("Type", new CrashReportCallable() {
			public String a() throws Exception {
				return "Dedicated Server (map_server.txt)";
			}

			public Object call() throws Exception {
				return this.a();
			}
		});
		return crashreport;
	}

	public void B() {
		System.exit(0);
	}

	public void D() { // CraftBukkit - fix decompile error
		super.D();
		this.aP();
	}

	public boolean getAllowNether() {
		return srvCfg.getWorldConfig().isAllowNether();
	}

	public boolean getSpawnMonsters() {
		return srvCfg.getGameplayConfig().isSpawnMonsters();
	}

	public void a(MojangStatisticsGenerator mojangstatisticsgenerator) {
		mojangstatisticsgenerator.a("whitelist_enabled", Boolean.valueOf(this.aQ().getHasWhitelist()));
		mojangstatisticsgenerator.a("whitelist_count", Integer.valueOf(this.aQ().getWhitelisted().length));
		super.a(mojangstatisticsgenerator);
	}

	public boolean getSnooperEnabled() {
		return srvCfg.isSnooperEnabled();
	}

	public void issueCommand(String s, ICommandListener icommandlistener) {
		this.serverCommandQueue.add(new ServerCommand(s, icommandlistener));
	}

	public void aP() {
		SpigotTimings.serverCommandTimer.startTiming(); // Spigot
		while (!this.serverCommandQueue.isEmpty()) {
			ServerCommand servercommand = (ServerCommand) this.serverCommandQueue.remove(0);

			// CraftBukkit start - ServerCommand for preprocessing
			ServerCommandEvent event = new ServerCommandEvent(console, servercommand.command);
			// RedSpigot pass to Java 8 consumers
			events.callEvent(event);
			server.getPluginManager().callEvent(event);
			if (event.isCancelled())
				continue;
			servercommand = new ServerCommand(event.getCommand(), servercommand.source);
			server.dispatchServerCommand(console, servercommand);
			// CraftBukkit end
		}
		SpigotTimings.serverCommandTimer.stopTiming(); // Spigot
	}

	public boolean aa() {
		return true;
	}

	public boolean af() {
		return srvCfg.isUseNativeTransport();
	}

	public RedPlayerList aQ() {
		return (RedPlayerList) super.getPlayerList();
	}

	public void a() {
		// We want to take the property manager out completely.
		// this.propertyManager.savePropertiesFile();
	}

	public String b() {
		// TODO: FixHP
		// File file = this.propertyManager.c();
		// return file != null ? file.getAbsolutePath() : "No settings file";
		return null;
	}

	public String d_() {
		return this.getServerIp();
	}

	public int e_() {
		return this.P();
	}

	public String f_() {
		return this.getMotd();
	}

	public void aR() {
		ServerGUI.a(this);
		this.u = true;
	}

	public boolean ap() {
		return this.u;
	}

	public String a(EnumGamemode enumgamemode, boolean flag) {
		return "";
	}

	public boolean getEnableCommandBlock() {
		return srvCfg.isEnableCommandBlock();
	}

	public int getSpawnProtection() {
		return srvCfg.getGameplayConfig().getSpawnProtection();
	}

	public boolean a(World world, BlockPosition blockposition, EntityHuman entityhuman) {
		if (world.worldProvider.getDimensionManager().getDimensionID() != 0) {
			return false;
		} else if (this.aQ().getOPs().isEmpty()) {
			return false;
		} else if (this.aQ().isOp(entityhuman.getProfile())) {
			return false;
		} else if (this.getSpawnProtection() <= 0) {
			return false;
		} else {
			BlockPosition blockposition1 = world.getSpawn();
			int i = MathHelper.a(blockposition.getX() - blockposition1.getX());
			int j = MathHelper.a(blockposition.getZ() - blockposition1.getZ());
			int k = Math.max(i, j);

			return k <= this.getSpawnProtection();
		}
	}

	public int q() {
		return srvCfg.getOpPermissionLevel();
	}

	public void setIdleTimeout(int i) {
		super.setIdleTimeout(i);
		this.a();
	}

	public boolean r() {
		return srvCfg.isBroadcastRconToOps();
	}

	public boolean s() {
		return srvCfg.isBroadcastConsoleToOps();
	}

	public int aE() {
		int i = srvCfg.getWorldConfig().getMaxWorldSize();

		if (i < 1) {
			i = 1;
		} else if (i > super.aE()) {
			i = super.aE();
		}

		return i;
	}

	public int aG() {
		return srvCfg.getNetworkCompressionThreshold();
	}

	protected boolean aS() {
		// TODO: Fix conversion systemop
		return false;
	}

	private void aV() {
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException interruptedexception) {
			;
		}
	}

	public long aT() {
		return srvCfg.getMaxTickTime();
	}

	public String getPlugins() {
		// CraftBukkit start - Whole method
		StringBuilder result = new StringBuilder();
		org.bukkit.plugin.Plugin[] plugins = server.getPluginManager().getPlugins();
		result.append(server.getName());
		result.append(" on Bukkit ");
		result.append(server.getBukkitVersion());
		if (plugins.length > 0 && server.getQueryPlugins()) {
			result.append(": ");

			for (int i = 0; i < plugins.length; i++) {
				if (i > 0) {
					result.append("; ");
				}

				result.append(plugins[i].getDescription().getName());
				result.append(" ");
				result.append(plugins[i].getDescription().getVersion().replaceAll(";", ","));
			}
		}

		return result.toString();
		// CraftBukkit end
	}

	// CraftBukkit start - fire RemoteServerCommandEvent
	public String executeRemoteCommand(final String s) {
		Waitable<String> waitable = new Waitable<String>() {
			@Override
			protected String evaluate() {
				remoteControlCommandListener.clearMessages();
				// Event changes start
				RemoteServerCommandEvent event = new RemoteServerCommandEvent(remoteConsole, s);
				server.getPluginManager().callEvent(event);
				if (event.isCancelled()) {
					return "";
				}
				// Event change end
				ServerCommand serverCommand = new ServerCommand(event.getCommand(), remoteControlCommandListener);
				server.dispatchServerCommand(remoteConsole, serverCommand);
				return remoteControlCommandListener.getMessages();
			}
		};
		processQueue.add(waitable);
		try {
			return waitable.get();
		} catch (java.util.concurrent.ExecutionException e) {
			throw new RuntimeException("Exception processing rcon command " + s, e.getCause());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt(); // Maintain interrupted state
			throw new RuntimeException("Interrupted processing rcon command " + s, e);
		}
		// CraftBukkit end
	}

	public boolean isGenerateStructures() {
		return generateStructures;
	}

	public void setGenerateStructures(boolean generateStructures) {
		this.generateStructures = generateStructures;
	}

}
