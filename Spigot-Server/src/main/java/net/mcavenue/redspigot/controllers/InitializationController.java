package net.mcavenue.redspigot.controllers;

import java.io.File;
import java.util.logging.Logger;
import org.fusesource.jansi.AnsiConsole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import joptsimple.OptionSet;
import net.minecraft.server.DedicatedServer;
import net.minecraft.server.MinecraftServer;

@Controller
public class InitializationController {
	public static boolean useJline = true;
	public static boolean useConsole = true;
	/**
	 * May need @Qualifier here later.
	 */
	@Autowired
	private OptionSet options;
	@Autowired
	private DedicatedServer nmsServer;
	private void processConfig() {
		Logger.getLogger(this.getClass().getName()).info("Starting from bukkit startup controller");
		// Do you love Java using + and ! as string based identifiers? I
		// sure do!
		String path = new File(".").getAbsolutePath();
		if (path.contains("!") || path.contains("+")) {
			System.err.println("Cannot run server in a directory with ! or + in the pathname. Please rename the affected folders and try again.");
			return;
		}
		try {
			setupJline();
			System.out.println("Context setup complete...Redspigot is now starting...");
			MinecraftServer.main(options);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	/**
	 * This is the method which will prepare the NMS server for startup.
	 */
	public void readyStartup() {
		processConfig();
		Logger.getLogger(this.getClass().getName()).info("Starting up NMS main stand by.");
		try {
			if (options.has("port")) {
				int port = (Integer) options.valueOf("port");
				if (port > 0) {
					nmsServer.setPort(port);
				}
			}
			if (options.has("universe")) {
				nmsServer.universe = (File) options.valueOf("universe");
			}
			if (options.has("world")) {
				nmsServer.setWorld((String) options.valueOf("world"));
			}
		} catch (Exception exception) {
			MinecraftServer.LOGGER.fatal("Failed to start the minecraft server", exception);
		}
		
	}

	private void setupJline() {
		// This trick bypasses Maven Shade's clever rewriting of our
		// getProperty call when using String literals
		String jline_UnsupportedTerminal = new String(new char[]{'j', 'l', 'i', 'n', 'e', '.', 'U', 'n', 's', 'u', 'p', 'p', 'o', 'r', 't', 'e', 'd',
				'T', 'e', 'r', 'm', 'i', 'n', 'a', 'l'});
		String jline_terminal = new String(new char[]{'j', 'l', 'i', 'n', 'e', '.', 't', 'e', 'r', 'm', 'i', 'n', 'a', 'l'});

		useJline = !(jline_UnsupportedTerminal).equals(System.getProperty(jline_terminal));

		if (options.has("nojline")) {
			System.setProperty("user.language", "en");
			useJline = false;
		}
		if (useJline) {
			Logger.getLogger(this.getClass().getName()).info("So we're using jline.");
			AnsiConsole.systemInstall();
		} else {
			// This ensures the terminal literal will always match the
			// jline implementation
			System.setProperty(jline.TerminalFactory.JLINE_TERMINAL, jline.UnsupportedTerminal.class.getName());
		}
	}

}
