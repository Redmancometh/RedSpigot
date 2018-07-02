package org.spigotmc;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import net.mcavenue.redspigot.configuration.pojo.spigot.SpigotConfig;
import net.mcavenue.redspigot.configuration.pojo.spigot.world.HungerConfig;
import net.mcavenue.redspigot.configuration.pojo.spigot.world.TickTimeConfig;
import net.mcavenue.redspigot.configuration.pojo.spigot.world.TrackingRangeConfig;

/**
 * Storing all of this config as fields is pretty shit. Probably want to go
 * ahead and not do that anymore soon.
 * 
 * @author Redmancometh
 *
 */
public class SpigotWorldConfig {

	private final String worldName;
	@Autowired
	private SpigotConfig cfg;
	private boolean verbose;

	public SpigotWorldConfig(String worldName) {
		Logger.getLogger("world-config").info("Loading world: " + worldName);
		this.worldName = worldName;
		Logger.getLogger("world-config").info("Config: " + cfg);
		init();
	}

	public net.mcavenue.redspigot.configuration.pojo.spigot.world.SpigotWorldConfig cfg() {
		return cfg.getWorldSettings().getWorldConfigs().get(worldName);
	}

	public void init() {
		this.verbose = cfg().isVerbose();
		log("-------- World Settings For [" + worldName + "] --------");
		initHunger();
	}

	private void log(String s) {
		if (verbose) {
			Bukkit.getLogger().info(s);
		}
	}

	// Crop growth rates
	public int cactusModifier;
	public int caneModifier;
	public int melonModifier;
	public int mushroomModifier;
	public int pumpkinModifier;
	public int saplingModifier;
	public int wheatModifier;
	public int wartModifier;
	public int vineModifier;
	public int cocoaModifier;

	private void growthModifiers() {
		cactusModifier = cfg().getGrowth().getCactus();
		caneModifier = cfg().getGrowth().getCane();
		melonModifier = cfg().getGrowth().getMelon();
		mushroomModifier = cfg().getGrowth().getMushroom();
		pumpkinModifier = cfg().getGrowth().getPumpkin();
		saplingModifier = cfg().getGrowth().getSapling();
		wheatModifier = cfg().getGrowth().getWheat();
		wartModifier = cfg().getGrowth().getNetherwart();
		vineModifier = cfg().getGrowth().getVine();
		cocoaModifier = cfg().getGrowth().getCocoa();
	}

	public double itemMerge;
	private void itemMerge() {
		itemMerge = cfg().getMerging().getItem();
		log("Item Merge Radius: " + itemMerge);
	}

	public double expMerge;
	private void expMerge() {
		expMerge = cfg().getMerging().getExp();
		log("Experience Merge Radius: " + expMerge);
	}

	public int viewDistance;
	private void viewDistance() {
		viewDistance = cfg().getViewDistance();
		log("View Distance: " + viewDistance);
	}

	public byte mobSpawnRange;
	private void mobSpawnRange() {
		mobSpawnRange = (byte) cfg().getMobSpawnRange();
		log("Mob Spawn Range: " + mobSpawnRange);
	}

	public int itemDespawnRate;
	private void itemDespawnRate() {
		itemDespawnRate = cfg().getItemDesspawnRate();
		log("Item Despawn Rate: " + itemDespawnRate);
	}

	public int animalActivationRange = 32;
	public int monsterActivationRange = 32;
	public int miscActivationRange = 16;
	public boolean tickInactiveVillagers = true;
	private void activationRange() {
		net.mcavenue.redspigot.configuration.pojo.spigot.world.ActivationRange act = cfg().getActivationRanges();
		animalActivationRange = act.getAnimals();
		monsterActivationRange = act.getMonsters();
		miscActivationRange = act.getMisc();
		tickInactiveVillagers = act.isTickInactiveVillagers();
		log("Entity Activation Range: An " + animalActivationRange + " / Mo " + monsterActivationRange + " / Mi " + miscActivationRange + " / Tiv "
				+ tickInactiveVillagers);
	}

	public int playerTrackingRange = 48;
	public int animalTrackingRange = 48;
	public int monsterTrackingRange = 48;
	public int miscTrackingRange = 32;
	public int otherTrackingRange = 64;
	private void trackingRange() {
		TrackingRangeConfig tracking = cfg().getTrackingRanges();
		playerTrackingRange = tracking.getPlayers();
		animalTrackingRange = tracking.getAnimals();
		monsterTrackingRange = tracking.getMonsters();
		miscTrackingRange = tracking.getMisc();
		otherTrackingRange = tracking.getOther();
		log("Entity Tracking Range: Pl " + playerTrackingRange + " / An " + animalTrackingRange + " / Mo " + monsterTrackingRange + " / Mi "
				+ miscTrackingRange + " / Other " + otherTrackingRange);
	}

	public int hopperTransfer;
	public int hopperCheck;
	public int hopperAmount;
	private void hoppers() {
		TickTimeConfig transfers = cfg().getTickTimes();
		hopperTransfer = transfers.getHopperTransfer();
		hopperCheck = transfers.getHopperCheck();
		hopperAmount = cfg().getHopperAmount();
		log("Hopper Transfer: " + hopperTransfer + " Hopper Check: " + hopperCheck + " Hopper Amount: " + hopperAmount);
	}

	public boolean randomLightUpdates;
	private void lightUpdates() {
		randomLightUpdates = cfg().isRandomLightUpdates();
		log("Random Lighting Updates: " + randomLightUpdates);
	}

	public boolean saveStructureInfo;
	private void structureInfo() {
		saveStructureInfo = cfg().isSaveStructureInfo();
		log("Structure Info Saving: " + saveStructureInfo);
		if (!saveStructureInfo) {
			log("*** WARNING *** You have selected to NOT save structure info. This may cause structures such as fortresses to not spawn mobs!");
			log("*** WARNING *** Please use this option with caution, SpigotMC is not responsible for any issues this option may cause in the future!");
		}
	}

	public int arrowDespawnRate;
	private void arrowDespawnRate() {
		arrowDespawnRate = cfg().getArrowDespawnRate();
		log("Arrow Despawn Rate: " + arrowDespawnRate);
	}

	public boolean zombieAggressiveTowardsVillager;
	private void zombieAggressiveTowardsVillager() {
		zombieAggressiveTowardsVillager = cfg().isZombieAggressiveTowardsVillager();
		log("Zombie Aggressive Towards Villager: " + zombieAggressiveTowardsVillager);
	}

	public boolean nerfSpawnerMobs;
	private void nerfSpawnerMobs() {
		nerfSpawnerMobs = cfg().isNerfSpawnerMobs();
		log("Nerfing mobs spawned from spawners: " + nerfSpawnerMobs);
	}

	public boolean enableZombiePigmenPortalSpawns;
	private void enableZombiePigmenPortalSpawns() {
		enableZombiePigmenPortalSpawns = cfg().isEnableZombiePigmentPortalSpawns();
		log("Allow Zombie Pigmen to spawn from portal blocks: " + enableZombiePigmenPortalSpawns);
	}

	public int dragonDeathSoundRadius;
	private void keepDragonDeathPerWorld() {
		dragonDeathSoundRadius = cfg().getDragonDeathSoundRadius();
	}

	public int witherSpawnSoundRadius;
	private void witherSpawnSoundRadius() {
		witherSpawnSoundRadius = cfg().getWitherSpawnSoundRadius();
	}

	public int villageSeed;
	public int largeFeatureSeed;
	public int monumentSeed;
	public int slimeSeed;
	private void initWorldGenSeeds() {
		villageSeed = cfg().getSeedVillage();
		largeFeatureSeed = cfg().getSeedFeature();
		monumentSeed = cfg().getSeedMonumen();
		slimeSeed = cfg().getSeedSlime();
		log("Custom Map Seeds:  Village: " + villageSeed + " Feature: " + largeFeatureSeed + " Monument: " + monumentSeed + " Slime: " + slimeSeed);
	}

	public float jumpWalkExhaustion;
	public float jumpSprintExhaustion;
	public float combatExhaustion;
	public float regenExhaustion;
	public float swimMultiplier;
	public float sprintMultiplier;
	public float otherMultiplier;
	private void initHunger() {
		HungerConfig hunger = cfg().getHunger();
		jumpWalkExhaustion = (float) hunger.getJumpWalkExhaustion();
		jumpSprintExhaustion = (float) hunger.getJumpSprintExhaustion();
		combatExhaustion = (float) hunger.getCombatExhaustion();
		regenExhaustion = (float) hunger.getRegenExhaustion();
		swimMultiplier = (float) hunger.getSwimMultiplier();
		sprintMultiplier = (float) hunger.getSprintMultiplier();
		otherMultiplier = (float) hunger.getOtherMultiplier();
	}

	public int currentPrimedTnt = 0;
	public int maxTntTicksPerTick;
	private void maxTntPerTick() {
		maxTntTicksPerTick = cfg().getMaxTntPerTick();
		log("Max TNT Explosions: " + maxTntTicksPerTick);
	}

	public int hangingTickFrequency;
	private void hangingTickFrequency() {
		hangingTickFrequency = cfg().getHangingTickFrequency();
	}

	public int tileMaxTickTime;
	public int entityMaxTickTime;
	private void maxTickTimes() {
		tileMaxTickTime = cfg().getMaxTicks().getTile();
		entityMaxTickTime = cfg().getMaxTicks().getEntity();
		log("Tile Max Tick Time: " + tileMaxTickTime + "ms Entity max Tick Time: " + entityMaxTickTime + "ms");
	}

	public double squidSpawnRangeMin;
	private void squidSpawnRange() {
		squidSpawnRangeMin = cfg().getSquidSpawning().getMin();
	}
}
