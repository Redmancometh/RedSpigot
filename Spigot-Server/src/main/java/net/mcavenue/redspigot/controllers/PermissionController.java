package net.mcavenue.redspigot.controllers;

import java.util.UUID;

import javax.annotation.PostConstruct;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.util.SpecialFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import net.mcavenue.redspigot.db.ObjectManager;
import net.mcavenue.redspigot.db.SubDatabase;
import net.mcavenue.redspigot.event.EventManager;
import net.mcavenue.redspigot.model.RedPlayer;
import net.minecraft.server.MinecraftServer;

@Controller
public class PermissionController {
	@Autowired
	private EventManager events;
	@Autowired
	private ObjectManager<RedPlayer> players;
	@PostConstruct
	public void post() {
		System.out.println("GETTING PLAYER");
		players.getSubDB().getDirectlyFromDB(UUID.randomUUID());
		players.getRecord(UUID.randomUUID()).thenAccept((rp) -> {
			System.out.println("RP RECORD");
		});
	}
}
