package net.mcavenue.redspigot.controllers;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.bukkit.craftbukkit.CraftServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;

import net.minecraft.server.DedicatedServer;
import net.minecraft.server.PlayerList;

@Controller
public class ServerController {
	@Autowired
	private InitializationController initializer;
	@Autowired
	private DedicatedServer nmsServer;
	@Autowired
	private AutowireCapableBeanFactory factory;
	@Autowired
	private PlayerList playerList;
	@Autowired
	private CraftServer server;
	@PostConstruct
	public void start() {
		factory.autowireBean(nmsServer);
		System.out.println("INIT CS");
		server.initialize();
		System.out.println("INIT NMS");
		nmsServer.server = server;
		nmsServer.a(playerList);
		try {
			nmsServer.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
		initializer.readyStartup();
		playerList.initPlayerList(nmsServer);
		System.out.println("Converter: " + (nmsServer.dataConverterManager == null));
		Logger.getLogger(this.getClass().getName()).info("Starting primary thread of server!");
		nmsServer.primaryThread.start();
	}
}
