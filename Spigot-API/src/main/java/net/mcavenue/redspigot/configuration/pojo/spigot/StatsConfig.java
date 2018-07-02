package net.mcavenue.redspigot.configuration.pojo.spigot;

import java.util.Map;

import lombok.Data;

@Data
public class StatsConfig {
	private boolean disableSaving = false;
	private Map<String, Integer> forcedStats;
	public boolean isDisableSaving() {
		return disableSaving;
	}
	public void setDisableSaving(boolean disableSaving) {
		this.disableSaving = disableSaving;

	}
	public Map<String, Integer> getForcedStats() {
		return forcedStats;
	}
	public void setForcedStats(Map<String, Integer> forcedStats) {
		this.forcedStats = forcedStats;
	}

}
