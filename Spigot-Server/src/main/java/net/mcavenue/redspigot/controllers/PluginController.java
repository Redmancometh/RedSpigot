package net.mcavenue.redspigot.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.SimpleCommandMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.command.VanillaCommandWrapper;
import org.bukkit.craftbukkit.help.SimpleHelpMap;
import org.bukkit.craftbukkit.util.permissions.CraftDefaultPermissions;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.util.permissions.DefaultPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.MarkedYAMLException;

import net.minecraft.server.CommandAbstract;
import net.minecraft.server.CommandDispatcher;
import net.minecraft.server.ICommand;
import net.minecraft.server.LocaleI18n;
import net.minecraft.server.MinecraftServer;

@Controller
public class PluginController {
	@Autowired
	private PluginManager pluginManager;
	@Autowired
	private MinecraftServer console;
	@Autowired
	@Qualifier("mc-logger")
	private Logger logger;
	@Autowired
	private AutowireCapableBeanFactory beans;
	@Autowired
	private SimpleCommandMap commandMap;
	@Autowired
	private SimpleHelpMap helpMap;
	@Autowired
	@Qualifier("bukkit-cfg")
	private YamlConfiguration ymlBukkit;
	@Autowired
	private Yaml yml;

	public void loadPlugins() {
		pluginManager.registerInterface(JavaPluginLoader.class);
		File pluginFolder = (File) console.getOptions().valueOf("plugins");
		if (pluginFolder.exists()) {
			Plugin[] plugins = pluginManager.loadPlugins(pluginFolder);
			for (Plugin plugin : plugins) {
				try {
					String message = String.format("Loading %s", plugin.getDescription().getFullName());
					plugin.getLogger().info(message);
					plugin.onLoad();
				} catch (Throwable ex) {
					Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE,
							ex.getMessage() + " initializing " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
				}
			}
		} else {
			pluginFolder.mkdir();
		}
	}

	public void enablePlugins(PluginLoadOrder type) {
		if (type == PluginLoadOrder.STARTUP) {
			helpMap.clear();
			helpMap.initializeGeneralTopics();
		}
		Plugin[] plugins = pluginManager.getPlugins();

		for (Plugin plugin : plugins) {
			if ((!plugin.isEnabled()) && (plugin.getDescription().getLoad() == type)) {
				enablePlugin(plugin);
			}
		}

		if (type == PluginLoadOrder.POSTWORLD) {
			// Spigot start - Allow vanilla commands to be forced to be the main
			// command
			setVanillaCommands(true);
			commandMap.setFallbackCommands();
			setVanillaCommands(false);
			// Spigot end
			commandMap.registerServerAliases();
			loadCustomPermissions();
			DefaultPermissions.registerCorePermissions();
			CraftDefaultPermissions.registerCorePermissions();
			helpMap.initializeCommands();
		}
	}

	private void setVanillaCommands(boolean first) { // Spigot
		Map<String, ICommand> commands = new CommandDispatcher(console).getCommands();
		for (ICommand cmd : commands.values()) {
			// Spigot start
			VanillaCommandWrapper wrapper = new VanillaCommandWrapper((CommandAbstract) cmd, LocaleI18n.get(cmd.getUsage(null)));
			if (org.spigotmc.SpigotConfig.replaceCommands.contains(wrapper.getName())) {
				if (first) {
					commandMap.register("minecraft", wrapper);
				}
			} else if (!first) {
				commandMap.register("minecraft", wrapper);
			}
			// Spigot end
		}
	}

	@SuppressWarnings({"unchecked", "finally"})
	private void loadCustomPermissions() {
		File file = new File(ymlBukkit.getString("settings.permissions-file"));
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
			perms = (Map<String, Map<String, Object>>) yml.load(stream);
		} catch (MarkedYAMLException ex) {
			logger.log(Level.WARNING, "Server permissions file " + file + " is not valid YAML: " + ex.toString());
			return;
		} catch (Throwable ex) {
			logger.log(Level.WARNING, "Server permissions file " + file + " is not valid YAML.", ex);
			return;
		} finally {
			try {
				stream.close();
			} catch (IOException ex) {
			}
		}

		if (perms == null) {
			logger.log(Level.INFO, "Server permissions file " + file + " is empty, ignoring it");
			return;
		}

		List<Permission> permsList = Permission.loadPermissions(perms, "Permission node '%s' in " + file + " is invalid",
				Permission.DEFAULT_PERMISSION);

		for (Permission perm : permsList) {
			try {
				pluginManager.addPermission(perm);
			} catch (IllegalArgumentException ex) {
				logger.log(Level.SEVERE, "Permission in " + file + " was already defined", ex);
			}
		}
	}

	@SuppressWarnings("unused")
	private void enablePlugin(Plugin plugin) {
		try {
			SimplePluginManager pluginManager = (SimplePluginManager) this.pluginManager;
			List<Permission> perms = plugin.getDescription().getPermissions();
			for (Permission perm : perms) {
				try {
					pluginManager.addPermission(perm, false);
				} catch (IllegalArgumentException ex) {
					logger.log(Level.WARNING, "Plugin " + plugin.getDescription().getFullName() + " tried to register permission '" + perm.getName()
							+ "' but it's already registered", ex);
				}
			}
			pluginManager.dirtyPermissibles();
			// Can't see any way this can go wrong off the top of my head.
			JavaPlugin jPlugin = (JavaPlugin) plugin;
			beans.autowireBean(plugin);
			pluginManager.enablePlugin(plugin);
			/**
			 * May store a reference to the ApplicationContext later, but for
			 * now we'll let the plugins handle their own lifecycle.
			 */
		} catch (Throwable ex) {
			Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE,
					ex.getMessage() + " loading " + plugin.getDescription().getFullName() + " (Is it up to date?)", ex);
		}
	}
}
