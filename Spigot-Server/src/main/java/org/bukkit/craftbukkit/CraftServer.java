package org.bukkit.craftbukkit;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.server.*;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.UnsafeValues;
import org.bukkit.Warning.WarningState;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.conversations.Conversable;
import org.bukkit.craftbukkit.boss.CraftBossBar;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.generator.CraftChunkData;
import org.bukkit.craftbukkit.help.SimpleHelpMap;
import org.bukkit.craftbukkit.inventory.CraftFurnaceRecipe;
import org.bukkit.craftbukkit.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.inventory.CraftItemFactory;
import org.bukkit.craftbukkit.inventory.CraftMerchantCustom;
import org.bukkit.craftbukkit.inventory.CraftRecipe;
import org.bukkit.craftbukkit.inventory.CraftShapedRecipe;
import org.bukkit.craftbukkit.inventory.CraftShapelessRecipe;
import org.bukkit.craftbukkit.inventory.RecipeIterator;
import org.bukkit.craftbukkit.map.CraftMapView;
import org.bukkit.craftbukkit.metadata.EntityMetadataStore;
import org.bukkit.craftbukkit.metadata.PlayerMetadataStore;
import org.bukkit.craftbukkit.metadata.WorldMetadataStore;
import org.bukkit.craftbukkit.potion.CraftPotionBrewer;
import org.bukkit.craftbukkit.scheduler.CraftScheduler;
import org.bukkit.craftbukkit.scoreboard.CraftScoreboardManager;
import org.bukkit.craftbukkit.util.CraftIconCache;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.util.DatFileFilter;
import org.bukkit.craftbukkit.util.Versioning;
import org.bukkit.craftbukkit.util.permissions.CraftDefaultPermissions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.server.BroadcastMessageEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.help.HelpMap;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.plugin.messaging.StandardMessenger;
import org.bukkit.util.CachedServerIcon;
import org.bukkit.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.MarkedYAMLException;
import org.apache.commons.lang.Validate;

import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.mojang.authlib.GameProfile;
import jline.console.ConsoleReader;
import org.apache.commons.lang.StringUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.util.CraftNamespacedKey;
import org.bukkit.event.server.TabCompleteEvent;

import net.mcavenue.redspigot.configuration.pojo.ServerConfig;
import net.mcavenue.redspigot.configuration.pojo.spigot.SpigotConfig;
import net.mcavenue.redspigot.controllers.PluginController;
import net.mcavenue.redspigot.playerlist.RedPlayerList;
import net.mcavenue.redspigot.util.BooleanWrapper;
import net.mcavenue.redspigot.world.RedWorldManager;

@SuppressWarnings("deprecation")
public class CraftServer implements Server {
	private String serverName = "CraftBukkit";
	private String bukkitVersion = Versioning.getBukkitVersion();
	@Autowired
	@Qualifier("mc-logger")
	private Logger logger;
	@Autowired
	@Qualifier("server-version")
	private String serverVersion;
	@Autowired
	private SimpleCommandMap commandMap;
	@Autowired
	private SimpleHelpMap helpMap;
	@Autowired
	private StandardMessenger messenger;
	@Autowired
	private EntityMetadataStore entityMetadata;
	@Autowired
	private PlayerMetadataStore playerMetadata;
	@Autowired
	private WorldMetadataStore worldMetadata;
	@Autowired
	protected MinecraftServer console;
	@Autowired
	private AutowireCapableBeanFactory beans;
	@Autowired
	protected PlayerList playerList;
	@Autowired
	private ServicesManager servicesManager;
	@Autowired
	private CraftScheduler scheduler;
	@Autowired
	private SimplePluginManager pluginManager;
	@Autowired
	@Qualifier("worlds")
	private Map<String, World> worlds;
	@Autowired
	@Qualifier("offline-players")
	private Map<UUID, OfflinePlayer> offlinePlayers;
	@Autowired
	@Qualifier("online")
	private BooleanWrapper online;
	@Autowired
	@Qualifier("bukkit-cfg")
	private YamlConfiguration configuration;
	@Autowired
	private SpigotConfig spigotCfg;
	@Autowired
	@Qualifier("player-view")
	private List<CraftPlayer> playerView;
	@Autowired
	@Qualifier("command-cfg")
	private YamlConfiguration commandsConfiguration;
	@Autowired
	private Yaml yaml;
	@Autowired
	@Qualifier("override-all-command-block-commands")
	private boolean overrideAllCommandBlockCommands;
	@Autowired
	@Qualifier("unrestricted-advancements")
	private boolean unrestrictedAdvancements;
	@Autowired
	private Spigot spigot;
	@Autowired
	private CraftIconCache icon;
	@Autowired
	private PluginController pluginController;
	@Autowired
	@Qualifier("world-container")
	private File container;
	public CraftScoreboardManager scoreboardManager;
	@Autowired
	private ServerConfig srvCfg;
	private int monsterSpawn = -1;
	private int animalSpawn = -1;
	private int waterAnimalSpawn = -1;
	private int ambientSpawn = -1;
	public int chunkGCPeriod = -1;
	public int chunkGCLoadThresh = 0;
	private WarningState warningState = WarningState.DEFAULT;
	public boolean playerCommandState;
	private boolean printSaveWarning;
	public int reloadCount;

	static {
		ConfigurationSerialization.registerClass(CraftOfflinePlayer.class);
		CraftItemFactory.instance();
	}

	public CraftServer() {

	}

	public void initialize() {
		online.setValue(srvCfg.isOnlineMode());
		Bukkit.setServer(this);

		// Register all the Enchantments and PotionTypes now so we can stop new
		// registration immediately after
		Enchantments.DAMAGE_ALL.getClass();
		Potion.setPotionBrewer(new CraftPotionBrewer());
		MobEffects.BLINDNESS.getClass();
		// Migrate aliases from old file and add previously implicit $1- to pass
		// all arguments
		pluginManager.useTimings(configuration.getBoolean("settings.plugin-profiling"));
		monsterSpawn = configuration.getInt("spawn-limits.monsters");
		animalSpawn = configuration.getInt("spawn-limits.animals");
		waterAnimalSpawn = configuration.getInt("spawn-limits.water-animals");
		ambientSpawn = configuration.getInt("spawn-limits.ambient");
		console.autosavePeriod = configuration.getInt("ticks-per.autosave");
		warningState = WarningState.value(configuration.getString("settings.deprecated-verbose"));
		chunkGCPeriod = configuration.getInt("chunk-gc.period-in-ticks");
		chunkGCLoadThresh = configuration.getInt("chunk-gc.load-threshold");
	}

	public boolean getPermissionOverride(ICommandListener listener) {
		while (listener instanceof CommandListenerWrapper) {
			listener = ((CommandListenerWrapper) listener).base;
		}

		return unrestrictedAdvancements && listener instanceof AdvancementRewards.AdvancementCommandListener;
	}

	public boolean getCommandBlockOverride(String command) {
		return overrideAllCommandBlockCommands || commandsConfiguration.getStringList("command-block-overrides").contains(command);
	}

	// TODO: Autowire in the options once CraftServer is a spring managed class

	private File getConfigFile() {
		return (File) console.getOptions().valueOf("bukkit-settings");
	}

	private File getCommandsConfigFile() {
		return (File) console.getOptions().valueOf("commands-settings");
	}

	private void saveConfig() {
		try {
			configuration.save(getConfigFile());
		} catch (IOException ex) {
			Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + getConfigFile(), ex);
		}
	}

	/**
	 * First this is called from DedicatedServer then enablePlugins
	 */
	public void loadPlugins() {
		pluginController.loadPlugins();
	}

	/**
	 * Called after loadPlugins in DedicatedServer
	 */
	public void enablePlugins(PluginLoadOrder type) {
		pluginController.enablePlugins(type);
	}

	public void disablePlugins() {
		pluginManager.disablePlugins();
	}

	@Override
	public String getName() {
		return serverName;
	}

	@Override
	public String getVersion() {
		return serverVersion + " (MC: " + console.getVersion() + ")";
	}

	@Override
	public String getBukkitVersion() {
		return bukkitVersion;
	}

	@Override
	public List<CraftPlayer> getOnlinePlayers() {
		return this.playerView;
	}

	@Override
	@Deprecated
	public Player getPlayer(String name) {
		Validate.notNull(name, "Name cannot be null");

		Player found = getPlayerExact(name);
		// Try for an exact match first.
		if (found != null) {
			return found;
		}

		String lowerName = name.toLowerCase(java.util.Locale.ENGLISH);
		int delta = Integer.MAX_VALUE;
		for (Player player : getOnlinePlayers()) {
			if (player.getName().toLowerCase(java.util.Locale.ENGLISH).startsWith(lowerName)) {
				int curDelta = Math.abs(player.getName().length() - lowerName.length());
				if (curDelta < delta) {
					found = player;
					delta = curDelta;
				}
				if (curDelta == 0)
					break;
			}
		}
		return found;
	}

	@Override
	@Deprecated
	public Player getPlayerExact(String name) {
		Validate.notNull(name, "Name cannot be null");

		EntityPlayer player = playerList.getPlayer(name);
		return (player != null) ? player.getBukkitEntity() : null;
	}

	@Override
	public Player getPlayer(UUID id) {
		EntityPlayer player = playerList.a(id);

		if (player != null) {
			return player.getBukkitEntity();
		}

		return null;
	}

	@Override
	public int broadcastMessage(String message) {
		return broadcast(message, BROADCAST_CHANNEL_USERS);
	}

	public Player getPlayer(EntityPlayer entity) {
		return entity.getBukkitEntity();
	}

	@Override
	@Deprecated
	public List<Player> matchPlayer(String partialName) {
		Validate.notNull(partialName, "PartialName cannot be null");

		List<Player> matchedPlayers = new ArrayList<Player>();

		for (Player iterPlayer : this.getOnlinePlayers()) {
			String iterPlayerName = iterPlayer.getName();

			if (partialName.equalsIgnoreCase(iterPlayerName)) {
				// Exact match
				matchedPlayers.clear();
				matchedPlayers.add(iterPlayer);
				break;
			}
			if (iterPlayerName.toLowerCase(java.util.Locale.ENGLISH).contains(partialName.toLowerCase(java.util.Locale.ENGLISH))) {
				// Partial match
				matchedPlayers.add(iterPlayer);
			}
		}

		return matchedPlayers;
	}

	@Override
	public int getMaxPlayers() {
		return playerList.getMaxPlayers();
	}

	// NOTE: These are dependent on the corresponding call in MinecraftServer
	// so if that changes this will need to as well
	@Override
	public int getPort() {
		return srvCfg.getServerPort();
	}

	@Override
	public int getViewDistance() {
		return srvCfg.getViewDistance();
	}

	@Override
	public String getIp() {
		return srvCfg.getServerIp();
	}

	@Override
	public String getServerName() {
		return srvCfg.getServerName();
	}

	@Override
	public String getServerId() {
		return srvCfg.getServerId();
	}

	@Override
	public String getWorldType() {
		return srvCfg.getWorldConfig().getLevelType();
	}

	@Override
	public boolean getGenerateStructures() {
		return srvCfg.getWorldConfig().isGenerateStructures();
	}

	@Override
	public boolean getAllowEnd() {
		return this.configuration.getBoolean("settings.allow-end");
	}

	@Override
	public boolean getAllowNether() {
		return srvCfg.getWorldConfig().isAllowNether();
	}

	public boolean getWarnOnOverload() {
		return true;
	}

	public boolean getQueryPlugins() {
		return this.configuration.getBoolean("settings.query-plugins");
	}

	@Override
	public boolean hasWhitelist() {
		return srvCfg.isWhitelist();
	}

	// End Temporary calls

	@Override
	public String getUpdateFolder() {
		return this.configuration.getString("settings.update-folder", "update");
	}

	@Override
	public File getUpdateFolderFile() {
		return new File((File) console.getOptions().valueOf("plugins"), this.configuration.getString("settings.update-folder", "update"));
	}

	@Override
	public long getConnectionThrottle() {
		// Spigot Start - Automatically set connection throttle for bungee
		// configurations
		if (spigotCfg.isBungeecord()) {
			return -1;
		} else {
			return this.configuration.getInt("settings.connection-throttle");
		}
		// Spigot End
	}

	@Override
	public int getTicksPerAnimalSpawns() {
		return this.configuration.getInt("ticks-per.animal-spawns");
	}

	@Override
	public int getTicksPerMonsterSpawns() {
		return this.configuration.getInt("ticks-per.monster-spawns");
	}

	@Override
	public PluginManager getPluginManager() {
		return pluginManager;
	}

	@Override
	public CraftScheduler getScheduler() {
		return scheduler;
	}

	@Override
	public ServicesManager getServicesManager() {
		return servicesManager;
	}

	@Override
	public List<World> getWorlds() {
		return new ArrayList<World>(worlds.values());
	}

	public PlayerList getHandle() {
		return playerList;
	}

	// NOTE: Should only be called from DedicatedServer.ah()
	public boolean dispatchServerCommand(CommandSender sender, ServerCommand serverCommand) {
		if (sender instanceof Conversable) {
			Conversable conversable = (Conversable) sender;

			if (conversable.isConversing()) {
				conversable.acceptConversationInput(serverCommand.command);
				return true;
			}
		}
		try {
			this.playerCommandState = true;
			return dispatchCommand(sender, serverCommand.command);
		} catch (Exception ex) {
			getLogger().log(Level.WARNING, "Unexpected exception while parsing console command \"" + serverCommand.command + '"', ex);
			return false;
		} finally {
			this.playerCommandState = false;
		}
	}

	@Override
	public boolean dispatchCommand(CommandSender sender, String commandLine) {
		Validate.notNull(sender, "Sender cannot be null");
		Validate.notNull(commandLine, "CommandLine cannot be null");

		if (commandMap.dispatch(sender, commandLine)) {
			return true;
		}

		// Spigot start
		if (StringUtils.isNotEmpty(spigotCfg.getMessages().getUnknownCommand())) {
			sender.sendMessage(spigotCfg.getMessages().getUnknownCommand());
		}
		// Spigot end

		return false;
	}

	@Override
	/**
	 * We don't want this to happen at least in the way it was done previously.
	 * This feature will eventually be re-implemented
	 */
	public void reload() {
	}

	@Override
	public void reloadData() {
		console.reload();
	}

	@SuppressWarnings({"unchecked", "finally"})
	private void loadCustomPermissions() {
		File file = new File(configuration.getString("settings.permissions-file"));
		FileInputStream stream;

		try {
			stream = new FileInputStream(file);
		} catch (FileNotFoundException ex) {
			try {
				file.createNewFile();
			} finally {
				return;
			}
		}

		Map<String, Map<String, Object>> perms;

		try {
			perms = (Map<String, Map<String, Object>>) yaml.load(stream);
		} catch (MarkedYAMLException ex) {
			getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML: " + ex.toString());
			return;
		} catch (Throwable ex) {
			getLogger().log(Level.WARNING, "Server permissions file " + file + " is not valid YAML.", ex);
			return;
		} finally {
			try {
				stream.close();
			} catch (IOException ex) {
			}
		}

		if (perms == null) {
			getLogger().log(Level.INFO, "Server permissions file " + file + " is empty, ignoring it");
			return;
		}

		List<Permission> permsList = Permission.loadPermissions(perms, "Permission node '%s' in " + file + " is invalid",
				Permission.DEFAULT_PERMISSION);

		for (Permission perm : permsList) {
			try {
				pluginManager.addPermission(perm);
			} catch (IllegalArgumentException ex) {
				getLogger().log(Level.SEVERE, "Permission in " + file + " was already defined", ex);
			}
		}
	}

	@Override
	public String toString() {
		return "CraftServer{" + "serverName=" + serverName + ",serverVersion=" + serverVersion + ",minecraftVersion=" + console.getVersion() + '}';
	}

	public World createWorld(String name, World.Environment environment) {
		return WorldCreator.name(name).environment(environment).createWorld();
	}

	public World createWorld(String name, World.Environment environment, long seed) {
		return WorldCreator.name(name).environment(environment).seed(seed).createWorld();
	}

	public World createWorld(String name, Environment environment, ChunkGenerator generator) {
		return WorldCreator.name(name).environment(environment).generator(generator).createWorld();
	}

	public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {
		return WorldCreator.name(name).environment(environment).seed(seed).generator(generator).createWorld();
	}

	@Override
	public World createWorld(WorldCreator creator) {
		Validate.notNull(creator, "Creator may not be null");

		String name = creator.name();
		ChunkGenerator generator = creator.generator();
		File folder = new File(getWorldContainer(), name);
		World world = getWorld(name);
		WorldType type = WorldType.getType(creator.type().getName());
		boolean generateStructures = creator.generateStructures();

		if (world != null) {
			return world;
		}

		if ((folder.exists()) && (!folder.isDirectory())) {
			throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
		}

		if (generator == null) {
			generator = getGenerator(name);
		}

		if (console.convertable.isConvertable(name)) {
			getLogger().info("Converting world '" + name + "'");
			console.convertable.convert(name, new IProgressUpdate() {
				private long b = System.currentTimeMillis();

				public void a(String s) {
				}

				public void a(int i) {
					if (System.currentTimeMillis() - this.b >= 1000L) {
						this.b = System.currentTimeMillis();
						MinecraftServer.LOGGER.info("Converting... " + i + "%");
					}

				}

				public void c(String s) {
				}
			});
		}

		int dimension = CraftWorld.CUSTOM_DIMENSION_OFFSET + console.worlds.size();
		boolean used = false;
		do {
			for (WorldServer server : console.worlds) {
				used = server.dimension == dimension;
				if (used) {
					dimension++;
					break;
				}
			}
		} while (used);
		boolean hardcore = false;

		IDataManager sdm = new ServerNBTManager(getWorldContainer(), name, true, getHandle().getServer().dataConverterManager);
		WorldData worlddata = sdm.getWorldData();
		WorldSettings worldSettings = null;
		if (worlddata == null) {
			worldSettings = new WorldSettings(creator.seed(), EnumGamemode.getById(getDefaultGameMode().getValue()), generateStructures, hardcore,
					type);
			worldSettings.setGeneratorSettings(creator.generatorSettings());
			worlddata = new WorldData(worldSettings, name);
		}
		worlddata.checkName(name); // CraftBukkit - Migration did not rewrite
									// the level.dat; This forces 1.8 to take
									// the last loaded world as respawn (in this
									// case the end)
		WorldServer internal = (WorldServer) new WorldServer(console, sdm, worlddata, dimension, console.methodProfiler, creator.environment(),
				generator).b();

		if (!(worlds.containsKey(name.toLowerCase(java.util.Locale.ENGLISH)))) {
			return null;
		}

		if (worldSettings != null) {
			internal.a(worldSettings);
		}
		internal.scoreboard = getScoreboardManager().getMainScoreboard().getHandle();
		internal.tracker = new EntityTracker(internal);
		internal.addIWorldAccess(new RedWorldManager(console, internal));
		internal.worldData.setDifficulty(EnumDifficulty.EASY);
		internal.setSpawnFlags(true, true);
		console.worlds.add(internal);

		pluginManager.callEvent(new WorldInitEvent(internal.getWorld()));
		System.out.println("Preparing start region for level " + (console.worlds.size() - 1) + " (Seed: " + internal.getSeed() + ")");

		if (internal.getWorld().getKeepSpawnInMemory()) {
			short short1 = 196;
			long i = System.currentTimeMillis();
			for (int j = -short1; j <= short1; j += 16) {
				for (int k = -short1; k <= short1; k += 16) {
					long l = System.currentTimeMillis();

					if (l < i) {
						i = l;
					}

					if (l > i + 1000L) {
						int i1 = (short1 * 2 + 1) * (short1 * 2 + 1);
						int j1 = (j + short1) * (short1 * 2 + 1) + k + 1;

						System.out.println("Preparing spawn area for " + name + ", " + (j1 * 100 / i1) + "%");
						i = l;
					}

					BlockPosition chunkcoordinates = internal.getSpawn();
					internal.getChunkProviderServer().getChunkAt(chunkcoordinates.getX() + j >> 4, chunkcoordinates.getZ() + k >> 4);
				}
			}
		}
		pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));
		return internal.getWorld();
	}

	@Override
	public boolean unloadWorld(String name, boolean save) {
		return unloadWorld(getWorld(name), save);
	}

	@Override
	public boolean unloadWorld(World world, boolean save) {
		if (world == null) {
			return false;
		}

		WorldServer handle = ((CraftWorld) world).getHandle();

		if (!(console.worlds.contains(handle))) {
			return false;
		}

		if (handle.dimension == 0) {
			return false;
		}

		if (handle.players.size() > 0) {
			return false;
		}

		WorldUnloadEvent e = new WorldUnloadEvent(handle.getWorld());
		pluginManager.callEvent(e);

		if (e.isCancelled()) {
			return false;
		}

		if (save) {
			try {
				handle.save(true, null);
				handle.saveLevel();
			} catch (ExceptionWorldConflict ex) {
				getLogger().log(Level.SEVERE, null, ex);
			}
		}

		worlds.remove(world.getName().toLowerCase(java.util.Locale.ENGLISH));
		console.worlds.remove(console.worlds.indexOf(handle));

		File parentFolder = world.getWorldFolder().getAbsoluteFile();

		// Synchronized because access to RegionFileCache.a is guarded by this
		// lock.
		synchronized (RegionFileCache.class) {
			// RegionFileCache.a should be RegionFileCache.cache
			Iterator<Map.Entry<File, RegionFile>> i = RegionFileCache.a.entrySet().iterator();
			while (i.hasNext()) {
				Map.Entry<File, RegionFile> entry = i.next();
				File child = entry.getKey().getAbsoluteFile();
				while (child != null) {
					if (child.equals(parentFolder)) {
						i.remove();
						try {
							entry.getValue().c(); // Should be
													// RegionFile.close();
						} catch (IOException ex) {
							getLogger().log(Level.SEVERE, null, ex);
						}
						break;
					}
					child = child.getParentFile();
				}
			}
		}

		return true;
	}

	public MinecraftServer getServer() {
		return console;
	}

	@Override
	public World getWorld(String name) {
		Validate.notNull(name, "Name cannot be null");

		return worlds.get(name.toLowerCase(java.util.Locale.ENGLISH));
	}

	@Override
	public World getWorld(UUID uid) {
		for (World world : worlds.values()) {
			if (world.getUID().equals(uid)) {
				return world;
			}
		}
		return null;
	}

	public void addWorld(World world) {
		// Check if a World already exists with the UID.
		if (getWorld(world.getUID()) != null) {
			System.out.println("World " + world.getName()
					+ " is a duplicate of another world and has been prevented from loading. Please delete the uid.dat file from " + world.getName()
					+ "'s world directory if you want to be able to load the duplicate world.");
			return;
		}
		worlds.put(world.getName().toLowerCase(java.util.Locale.ENGLISH), world);
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

	public ConsoleReader getReader() {
		return console.reader;
	}

	@Override
	public PluginCommand getPluginCommand(String name) {
		Command command = commandMap.getCommand(name);

		if (command instanceof PluginCommand) {
			return (PluginCommand) command;
		} else {
			return null;
		}
	}

	@Override
	public void savePlayers() {
		checkSaveState();
		playerList.savePlayers();
	}

	@Override
	public boolean addRecipe(Recipe recipe) {
		CraftRecipe toAdd;
		if (recipe instanceof CraftRecipe) {
			toAdd = (CraftRecipe) recipe;
		} else {
			if (recipe instanceof ShapedRecipe) {
				toAdd = CraftShapedRecipe.fromBukkitRecipe((ShapedRecipe) recipe);
			} else if (recipe instanceof ShapelessRecipe) {
				toAdd = CraftShapelessRecipe.fromBukkitRecipe((ShapelessRecipe) recipe);
			} else if (recipe instanceof FurnaceRecipe) {
				toAdd = CraftFurnaceRecipe.fromBukkitRecipe((FurnaceRecipe) recipe);
			} else {
				return false;
			}
		}
		toAdd.addToCraftingManager();
		return true;
	}

	@Override
	public List<Recipe> getRecipesFor(ItemStack result) {
		Validate.notNull(result, "Result cannot be null");

		List<Recipe> results = new ArrayList<Recipe>();
		Iterator<Recipe> iter = recipeIterator();
		while (iter.hasNext()) {
			Recipe recipe = iter.next();
			ItemStack stack = recipe.getResult();
			if (stack.getType() != result.getType()) {
				continue;
			}
			if (result.getDurability() == -1 || result.getDurability() == stack.getDurability()) {
				results.add(recipe);
			}
		}
		return results;
	}

	@Override
	public Iterator<Recipe> recipeIterator() {
		return new RecipeIterator();
	}

	@Override
	public void clearRecipes() {
		CraftingManager.recipes = new RegistryMaterials();
		RecipesFurnace.getInstance().recipes.clear();
		RecipesFurnace.getInstance().customRecipes.clear();
		RecipesFurnace.getInstance().customExperience.clear();
	}

	@Override
	public void resetRecipes() {
		CraftingManager.recipes = new RegistryMaterials();
		CraftingManager.init();
		RecipesFurnace.getInstance().recipes = new RecipesFurnace().recipes;
		RecipesFurnace.getInstance().customRecipes.clear();
		RecipesFurnace.getInstance().customExperience.clear();
	}

	@Override
	public Map<String, String[]> getCommandAliases() {
		ConfigurationSection section = commandsConfiguration.getConfigurationSection("aliases");
		Map<String, String[]> result = new LinkedHashMap<String, String[]>();

		if (section != null) {
			for (String key : section.getKeys(false)) {
				List<String> commands;

				if (section.isList(key)) {
					commands = section.getStringList(key);
				} else {
					commands = ImmutableList.of(section.getString(key));
				}

				result.put(key, commands.toArray(new String[commands.size()]));
			}
		}

		return result;
	}

	public void removeBukkitSpawnRadius() {
		configuration.set("settings.spawn-radius", null);
		saveConfig();
	}

	public int getBukkitSpawnRadius() {
		return configuration.getInt("settings.spawn-radius", -1);
	}

	@Override
	public String getShutdownMessage() {
		return configuration.getString("settings.shutdown-message");
	}

	@Override
	public int getSpawnRadius() {
		return srvCfg.getGameplayConfig().getSpawnProtection();
	}

	@Override
	public void setSpawnRadius(int value) {
		configuration.set("settings.spawn-radius", value);
		saveConfig();
	}

	@Override
	public boolean getOnlineMode() {
		return online.isValue();
	}

	@Override
	public boolean getAllowFlight() {
		return console.getAllowFlight();
	}

	@Override
	public boolean isHardcore() {
		return console.isHardcore();
	}

	/**
	 * TODO: Put something that managed this method in the configuration, or use
	 * it as a controller.
	 * 
	 * @param world
	 * @return
	 */
	public ChunkGenerator getGenerator(String world) {
		ConfigurationSection section = configuration.getConfigurationSection("worlds");
		ChunkGenerator result = null;

		if (section != null) {
			section = section.getConfigurationSection(world);

			if (section != null) {
				String name = section.getString("generator");

				if ((name != null) && (!name.equals(""))) {
					String[] split = name.split(":", 2);
					String id = (split.length > 1) ? split[1] : null;
					Plugin plugin = pluginManager.getPlugin(split[0]);

					if (plugin == null) {
						getLogger().severe("Could not set generator for default world '" + world + "': Plugin '" + split[0] + "' does not exist");
					} else if (!plugin.isEnabled()) {
						getLogger().severe("Could not set generator for default world '" + world + "': Plugin '"
								+ plugin.getDescription().getFullName() + "' is not enabled yet (is it load:STARTUP?)");
					} else {
						try {
							result = plugin.getDefaultWorldGenerator(world, id);
							if (result == null) {
								getLogger().severe("Could not set generator for default world '" + world + "': Plugin '"
										+ plugin.getDescription().getFullName() + "' lacks a default world generator");
							}
						} catch (Throwable t) {
							plugin.getLogger().log(Level.SEVERE,
									"Could not set generator for default world '" + world + "': Plugin '" + plugin.getDescription().getFullName(), t);
						}
					}
				}
			}
		}

		return result;
	}

	@Override
	@Deprecated
	public CraftMapView getMap(short id) {
		PersistentCollection collection = console.worlds.get(0).worldMaps;
		WorldMap worldmap = (WorldMap) collection.get(WorldMap.class, "map_" + id);
		if (worldmap == null) {
			return null;
		}
		return worldmap.mapView;
	}

	@Override
	public CraftMapView createMap(World world) {
		Validate.notNull(world, "World cannot be null");

		net.minecraft.server.ItemStack stack = new net.minecraft.server.ItemStack(Items.MAP, 1, -1);
		WorldMap worldmap = Items.FILLED_MAP.getSavedMap(stack, ((CraftWorld) world).getHandle());
		return worldmap.mapView;
	}

	@Override
	public void shutdown() {
		console.safeShutdown();
	}

	@Override
	public int broadcast(String message, String permission) {
		Set<CommandSender> recipients = new HashSet<>();
		for (Permissible permissible : getPluginManager().getPermissionSubscriptions(permission)) {
			if (permissible instanceof CommandSender && permissible.hasPermission(permission)) {
				recipients.add((CommandSender) permissible);
			}
		}

		BroadcastMessageEvent broadcastMessageEvent = new BroadcastMessageEvent(message, recipients);
		getPluginManager().callEvent(broadcastMessageEvent);

		if (broadcastMessageEvent.isCancelled()) {
			return 0;
		}

		message = broadcastMessageEvent.getMessage();

		for (CommandSender recipient : recipients) {
			recipient.sendMessage(message);
		}

		return recipients.size();
	}

	@Override
	@Deprecated
	public OfflinePlayer getOfflinePlayer(String name) {
		Validate.notNull(name, "Name cannot be null");
		com.google.common.base.Preconditions.checkArgument(!org.apache.commons.lang.StringUtils.isBlank(name), "Name cannot be blank"); // Spigot

		OfflinePlayer result = getPlayerExact(name);
		if (result == null) {
			// Spigot Start
			GameProfile profile = null;
			// Only fetch an online UUID in online mode
			if (MinecraftServer.getServer().getOnlineMode() || spigotCfg.isBungeecord()) {
				profile = console.getUserCache().getProfile(name);
			}
			// Spigot end
			if (profile == null) {
				// Make an OfflinePlayer using an offline mode UUID since the
				// name has no profile
				result = getOfflinePlayer(new GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(Charsets.UTF_8)), name));
			} else {
				// Use the GameProfile even when we get a UUID so we ensure we
				// still have a name
				result = getOfflinePlayer(profile);
			}
		} else {
			offlinePlayers.remove(result.getUniqueId());
		}

		return result;
	}

	@Override
	public OfflinePlayer getOfflinePlayer(UUID id) {
		Validate.notNull(id, "UUID cannot be null");

		OfflinePlayer result = getPlayer(id);
		if (result == null) {
			result = offlinePlayers.get(id);
			if (result == null) {
				result = new CraftOfflinePlayer(this, new GameProfile(id, null));
				offlinePlayers.put(id, result);
			}
		} else {
			offlinePlayers.remove(id);
		}

		return result;
	}

	public OfflinePlayer getOfflinePlayer(GameProfile profile) {
		OfflinePlayer player = new CraftOfflinePlayer(this, profile);
		offlinePlayers.put(profile.getId(), player);
		return player;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<String> getIPBans() {
		return new HashSet<String>(Arrays.asList(playerList.getIPBans().getEntries()));
	}

	@Override
	public void banIP(String address) {
		Validate.notNull(address, "Address cannot be null.");

		this.getBanList(org.bukkit.BanList.Type.IP).addBan(address, null, null, null);
	}

	@Override
	public void unbanIP(String address) {
		Validate.notNull(address, "Address cannot be null.");

		this.getBanList(org.bukkit.BanList.Type.IP).pardon(address);
	}

	@Override
	public Set<OfflinePlayer> getBannedPlayers() {
		Set<OfflinePlayer> result = new HashSet<OfflinePlayer>();

		for (JsonListEntry entry : playerList.getProfileBans().getValues()) {
			result.add(getOfflinePlayer((GameProfile) entry.getKey()));
		}

		return result;
	}

	@Override
	public BanList getBanList(BanList.Type type) {
		Validate.notNull(type, "Type cannot be null");

		switch (type) {
			case IP :
				return new CraftIpBanList(playerList.getIPBans());
			case NAME :
			default :
				return new CraftProfileBanList(playerList.getProfileBans());
		}
	}

	@Override
	public void setWhitelist(boolean value) {
		playerList.setHasWhitelist(value);
	}

	@Override
	public Set<OfflinePlayer> getWhitelistedPlayers() {
		Set<OfflinePlayer> result = new LinkedHashSet<OfflinePlayer>();

		for (JsonListEntry entry : playerList.getWhitelist().getValues()) {
			result.add(getOfflinePlayer((GameProfile) entry.getKey()));
		}

		return result;
	}

	@Override
	public Set<OfflinePlayer> getOperators() {
		Set<OfflinePlayer> result = new HashSet<OfflinePlayer>();

		for (JsonListEntry entry : playerList.getOPs().getValues()) {
			result.add(getOfflinePlayer((GameProfile) entry.getKey()));
		}

		return result;
	}

	@Override
	public void reloadWhitelist() {
		playerList.reloadWhitelist();
	}

	@Override
	public GameMode getDefaultGameMode() {
		return GameMode.getByValue(console.worlds.get(0).getWorldData().getGameType().getId());
	}

	@Override
	public void setDefaultGameMode(GameMode mode) {
		Validate.notNull(mode, "Mode cannot be null");

		for (World world : getWorlds()) {
			((CraftWorld) world).getHandle().worldData.setGameType(EnumGamemode.getById(mode.getValue()));
		}
	}

	@Override
	public ConsoleCommandSender getConsoleSender() {
		return console.console;
	}

	public EntityMetadataStore getEntityMetadata() {
		return entityMetadata;
	}

	public PlayerMetadataStore getPlayerMetadata() {
		return playerMetadata;
	}

	public WorldMetadataStore getWorldMetadata() {
		return worldMetadata;
	}

	@Override
	public File getWorldContainer() {
		return container;
	}

	@Override
	public OfflinePlayer[] getOfflinePlayers() {
		WorldNBTStorage storage = (WorldNBTStorage) console.worlds.get(0).getDataManager();
		String[] files = storage.getPlayerDir().list(new DatFileFilter());
		Set<OfflinePlayer> players = new HashSet<OfflinePlayer>();

		for (String file : files) {
			try {
				players.add(getOfflinePlayer(UUID.fromString(file.substring(0, file.length() - 4))));
			} catch (IllegalArgumentException ex) {
				// Who knows what is in this directory, just ignore invalid
				// files
			}
		}

		players.addAll(getOnlinePlayers());

		return players.toArray(new OfflinePlayer[players.size()]);
	}

	@Override
	public Messenger getMessenger() {
		return messenger;
	}

	@Override
	public void sendPluginMessage(Plugin source, String channel, byte[] message) {
		StandardMessenger.validatePluginMessage(getMessenger(), source, channel, message);

		for (Player player : getOnlinePlayers()) {
			player.sendPluginMessage(source, channel, message);
		}
	}

	@Override
	public Set<String> getListeningPluginChannels() {
		Set<String> result = new HashSet<String>();

		for (Player player : getOnlinePlayers()) {
			result.addAll(player.getListeningPluginChannels());
		}

		return result;
	}

	@Override
	public Inventory createInventory(InventoryHolder owner, InventoryType type) {
		// TODO: Create the appropriate type, rather than Custom?
		return new CraftInventoryCustom(owner, type);
	}

	@Override
	public Inventory createInventory(InventoryHolder owner, InventoryType type, String title) {
		return new CraftInventoryCustom(owner, type, title);
	}

	@Override
	public Inventory createInventory(InventoryHolder owner, int size) throws IllegalArgumentException {
		Validate.isTrue(size % 9 == 0, "Chests must have a size that is a multiple of 9!");
		return new CraftInventoryCustom(owner, size);
	}

	@Override
	public Inventory createInventory(InventoryHolder owner, int size, String title) throws IllegalArgumentException {
		Validate.isTrue(size % 9 == 0, "Chests must have a size that is a multiple of 9!");
		return new CraftInventoryCustom(owner, size, title);
	}

	@Override
	public Merchant createMerchant(String title) {
		return new CraftMerchantCustom(title == null ? InventoryType.MERCHANT.getDefaultTitle() : title);
	}

	@Override
	public HelpMap getHelpMap() {
		return helpMap;
	}

	public SimpleCommandMap getCommandMap() {
		return commandMap;
	}

	@Override
	public int getMonsterSpawnLimit() {
		return monsterSpawn;
	}

	@Override
	public int getAnimalSpawnLimit() {
		return animalSpawn;
	}

	@Override
	public int getWaterAnimalSpawnLimit() {
		return waterAnimalSpawn;
	}

	@Override
	public int getAmbientSpawnLimit() {
		return ambientSpawn;
	}

	@Override
	public boolean isPrimaryThread() {
		return Thread.currentThread().equals(console.primaryThread);
	}

	@Override
	public String getMotd() {
		return console.getMotd();
	}

	@Override
	public WarningState getWarningState() {
		return warningState;
	}

	public List<String> tabComplete(net.minecraft.server.ICommandListener sender, String message, BlockPosition pos, boolean forceCommand) {
		if (!(sender instanceof EntityPlayer)) {
			return ImmutableList.of();
		}

		List<String> offers;
		Player player = ((EntityPlayer) sender).getBukkitEntity();
		if (message.startsWith("/") || forceCommand) {
			offers = tabCompleteCommand(player, message, pos);
		} else {
			offers = tabCompleteChat(player, message);
		}

		TabCompleteEvent tabEvent = new TabCompleteEvent(player, message, offers);
		getPluginManager().callEvent(tabEvent);

		return tabEvent.isCancelled() ? Collections.EMPTY_LIST : tabEvent.getCompletions();
	}

	public List<String> tabCompleteCommand(Player player, String message, BlockPosition pos) {
		// Spigot Start
		if ((spigotCfg.getCommands().getTabComplete() < 0 || message.length() <= spigotCfg.getCommands().getTabComplete())
				&& !message.contains(" ")) {
			return ImmutableList.of();
		}
		// Spigot End

		List<String> completions = null;
		try {
			if (message.startsWith("/")) {
				// Trim leading '/' if present (won't always be present in
				// command blocks)
				message = message.substring(1);
			}
			if (pos == null) {
				completions = getCommandMap().tabComplete(player, message);
			} else {
				completions = getCommandMap().tabComplete(player, message, new Location(player.getWorld(), pos.getX(), pos.getY(), pos.getZ()));
			}
		} catch (CommandException ex) {
			player.sendMessage(ChatColor.RED + "An internal error occurred while attempting to tab-complete this command");
			getLogger().log(Level.SEVERE, "Exception when " + player.getName() + " attempted to tab complete " + message, ex);
		}

		return completions == null ? ImmutableList.<String>of() : completions;
	}

	public List<String> tabCompleteChat(Player player, String message) {
		List<String> completions = new ArrayList<String>();
		PlayerChatTabCompleteEvent event = new PlayerChatTabCompleteEvent(player, message, completions);
		String token = event.getLastToken();
		for (Player p : getOnlinePlayers()) {
			if (player.canSee(p) && StringUtil.startsWithIgnoreCase(p.getName(), token)) {
				completions.add(p.getName());
			}
		}
		pluginManager.callEvent(event);

		Iterator<?> it = completions.iterator();
		while (it.hasNext()) {
			Object current = it.next();
			if (!(current instanceof String)) {
				// Sanity
				it.remove();
			}
		}
		Collections.sort(completions, String.CASE_INSENSITIVE_ORDER);
		return completions;
	}

	@Override
	public CraftItemFactory getItemFactory() {
		return CraftItemFactory.instance();
	}

	@Override
	public CraftScoreboardManager getScoreboardManager() {
		return scoreboardManager;
	}

	public void checkSaveState() {
		if (this.playerCommandState || this.printSaveWarning || this.console.autosavePeriod <= 0) {
			return;
		}
		this.printSaveWarning = true;
		getLogger().log(Level.WARNING,
				"A manual (plugin-induced) save has been detected while server is configured to auto-save. This may affect performance.",
				warningState == WarningState.ON ? new Throwable() : null);
	}

	@Override
	public CraftIconCache getServerIcon() {
		return icon;
	}

	@Override
	public CraftIconCache loadServerIcon(File file) throws Exception {
		return icon;
	}

	@Override
	public void setIdleTimeout(int threshold) {
		console.setIdleTimeout(threshold);
	}

	@Override
	public int getIdleTimeout() {
		return console.getIdleTimeout();
	}

	@Override
	public ChunkGenerator.ChunkData createChunkData(World world) {
		return new CraftChunkData(world);
	}

	@Override
	public BossBar createBossBar(String title, BarColor color, BarStyle style, BarFlag... flags) {
		return new CraftBossBar(title, color, style, flags);
	}

	@Override
	public Entity getEntity(UUID uuid) {
		Validate.notNull(uuid, "UUID cannot be null");
		net.minecraft.server.Entity entity = console.a(uuid); // PAIL: getEntity
		return entity == null ? null : entity.getBukkitEntity();
	}

	@Override
	public org.bukkit.advancement.Advancement getAdvancement(NamespacedKey key) {
		Preconditions.checkArgument(key != null, "key");

		Advancement advancement = console.getAdvancementData().a(CraftNamespacedKey.toMinecraft(key));
		return (advancement == null) ? null : advancement.bukkit;
	}

	@Override
	public Iterator<org.bukkit.advancement.Advancement> advancementIterator() {
		return Iterators.unmodifiableIterator(
				Iterators.transform(console.getAdvancementData().c().iterator(), new Function<Advancement, org.bukkit.advancement.Advancement>() { // PAIL:
																																					// rename
					@Override
					public org.bukkit.advancement.Advancement apply(Advancement advancement) {
						return advancement.bukkit;
					}
				}));
	}

	@Deprecated
	@Override
	public UnsafeValues getUnsafe() {
		return CraftMagicNumbers.INSTANCE;
	}

	public Spigot spigot() {
		return spigot;
	}

	@Override
	public CachedServerIcon loadServerIcon(BufferedImage image) throws IllegalArgumentException, Exception {
		return icon;
	}
}
