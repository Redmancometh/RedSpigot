package net.mcavenue.redspigot.configuration.context;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.util.CraftIconCache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import com.google.common.base.Charsets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import joptsimple.OptionSet;
import net.minecraft.server.MinecraftServer;

/**
 * This is the context which provides all the configuration values to the
 * containers needing them.
 * 
 * This is to be considered a legacy artifact, and will be replaced with a JSON
 * POJO based configuration system.
 * 
 * @author Redmancometh
 *
 */
@Configuration
public class CraftConfigContext {

	@Bean(name = "server-version")
	public String version() {
		return "1.12-RED";
	}

	@Bean(name = "yml")
	public Yaml yml() {
		return new Yaml(new SafeConstructor());
	}

	@Bean
	public CraftIconCache icon(@Qualifier("mc-logger") Logger logger) {
		CraftIconCache icon = new CraftIconCache(null);
		try {
			File file = new File(new File("."), "server-icon.png");
			if (file.isFile()) {
				icon = loadServerIcon0(file);
			}
		} catch (Exception ex) {
			logger.log(Level.WARNING, "Couldn't load server icon", ex);
		}
		return icon;
	}

	static CraftIconCache loadServerIcon0(File file) throws Exception {
		return loadServerIcon0(ImageIO.read(file));
	}

	public CraftIconCache loadServerIcon(BufferedImage image) throws Exception {
		Validate.notNull(image, "Image cannot be null");
		return loadServerIcon0(image);
	}

	static CraftIconCache loadServerIcon0(BufferedImage image) throws Exception {
		ByteBuf bytebuf = Unpooled.buffer();

		Validate.isTrue(image.getWidth() == 64, "Must be 64 pixels wide");
		Validate.isTrue(image.getHeight() == 64, "Must be 64 pixels high");
		ImageIO.write(image, "PNG", new ByteBufOutputStream(bytebuf));
		ByteBuf bytebuf1 = Base64.encode(bytebuf);

		return new CraftIconCache("data:image/png;base64," + bytebuf1.toString(Charsets.UTF_8));
	}

	/**
	 * 
	 * @param commandsConfiguration
	 * @return
	 */
	@Bean(name = "override-all-command-block-commands")
	public boolean overrideAllCommandBlockCommands(@Qualifier("command-cfg") YamlConfiguration commandsConfiguration) {
		return commandsConfiguration.getStringList("command-block-overrides").contains("*");
	}

	/**
	 * 
	 * @param commandsConfiguration
	 * @return
	 */
	@Bean(name = "unrestricted-advancements")
	public boolean unrestrictedAdvancements(@Qualifier("command-cfg") YamlConfiguration commandsConfiguration) {
		return commandsConfiguration.getStringList("unrestricted-advancements").contains("*");
	}

	// TODO: Clean this up like...a lot.
	@Bean(name = "command-cfg")
	public YamlConfiguration getCmdYml(@Qualifier("bukkit-cfg") YamlConfiguration configuration, MinecraftServer server) {
		ConfigurationSection legacyAlias = null;
		File commandFile = (File) server.getOptions().valueOf("commands-settings");
		if (!configuration.isString("aliases")) {
			legacyAlias = configuration.getConfigurationSection("aliases");
			configuration.set("aliases", "now-in-commands.yml");
		}
		if (commandFile.isFile()) {
			legacyAlias = null;
		}
		YamlConfiguration commandsConfiguration = YamlConfiguration.loadConfiguration(commandFile);
		commandsConfiguration.options().copyDefaults(true);
		commandsConfiguration.setDefaults(YamlConfiguration.loadConfiguration(
				new InputStreamReader(getClass().getClassLoader().getResourceAsStream("configurations/commands.yml"), Charsets.UTF_8)));
		saveCommandsConfig(commandsConfiguration, commandFile);
		if (legacyAlias != null) {
			ConfigurationSection aliases = commandsConfiguration.createSection("aliases");
			for (String key : legacyAlias.getKeys(false)) {
				ArrayList<String> commands = new ArrayList<String>();

				if (legacyAlias.isList(key)) {
					for (String command : legacyAlias.getStringList(key)) {
						commands.add(command + " $1-");
					}
				} else {
					commands.add(legacyAlias.getString(key) + " $1-");
				}

				aliases.set(key, commands);
			}
		}
		saveCommandsConfig(commandsConfiguration, commandFile);
		return commandsConfiguration;
	}

	private void saveCommandsConfig(YamlConfiguration configuration, File cmdFile) {
		try {
			configuration.save(cmdFile);
		} catch (IOException ex) {
			Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + cmdFile, ex);
		}
	}

	@Bean(name = "bukkit-conf-file")
	@DependsOn("option-set")
	public File bukkitConfFile(MinecraftServer server, @Qualifier("option-set") OptionSet options) {
		return (File) options.valueOf("bukkit-settings");
	}

	@Bean(name = "bukkit-cfg")
	@DependsOn("bukkit-conf-file")
	public YamlConfiguration getYml(MinecraftServer server, @Qualifier("bukkit-conf-file") File bukkitConfFile) {
		YamlConfiguration configuration = YamlConfiguration.loadConfiguration(bukkitConfFile);
		configuration.options().copyDefaults(true);
		configuration.setDefaults(YamlConfiguration.loadConfiguration(
				new InputStreamReader(getClass().getClassLoader().getResourceAsStream("configurations/bukkit.yml"), Charsets.UTF_8)));
		saveConfig(configuration, bukkitConfFile);
		return configuration;
	}

	private void saveConfig(YamlConfiguration configuration, File bukkitConfFile) {
		try {
			configuration.save(bukkitConfFile);
		} catch (IOException ex) {
			Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, "Could not save " + bukkitConfFile, ex);
		}
	}
}
