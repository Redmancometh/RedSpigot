package net.mcavenue.redspigot.configuration.pojo;

import lombok.Data;

@Data
public class WorldConfiguration {
	private int maxWorldSize = 5000, maxBuildHeight = 256;
	private boolean generateStructures, allowNether, allowEnd;
	private String levelType = "DEFAULT", levelSeed, levelName, generatorSettings;
}
