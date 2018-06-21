package net.mcavenue.redspigot.configuration.pojo;

import lombok.Data;

@Data
public class GameplayConfiguration {
	private boolean hardcore, forceGamemode, allowFlight, pvp, spawnNpcs, spawnAnimals, spawnMonsters;
	private int spawnProtection, gamemode, difficulty;
}
