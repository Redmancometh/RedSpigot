package net.mcavenue.redspigot.configuration.pojo.spigot;

import lombok.Data;
import net.mcavenue.redspigot.configuration.pojo.spigot.world.SpigotWorldsConfig;

@Data
public class SpigotConfig {
	private String restartScript;
	private boolean debug, lateBind, saveUserCacheOnStopOnly, bungeecord, restartOnCrash, filterCreativeItems;
	private int userCacheSize = 1000, sampleCount = 12, playerShuffle = 0, intCacheLimit = 1024, itemDirtyTicks = 20, timeoutTime = 20,
			nettyThreads = 4;
	private double movedWronglyThreshhold = .0625, movedTooQuicklyMultiplier = 10.0;
	private SpigotWorldsConfig worldSettings;
	private AttributeConfig attributes;
	private MessagesConfig messages;
	private StatsConfig stats;
	private AdvancementConfig advancement;
	private CommandsConfig commands;
	public String getRestartScript() {
		return restartScript;
	}
	public void setRestartScript(String restartScript) {
		this.restartScript = restartScript;
	}
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public boolean isLateBind() {
		return lateBind;
	}
	public void setLateBind(boolean lateBind) {
		this.lateBind = lateBind;
	}
	public boolean isSaveUserCacheOnStopOnly() {
		return saveUserCacheOnStopOnly;
	}
	public void setSaveUserCacheOnStopOnly(boolean saveUserCacheOnStopOnly) {
		this.saveUserCacheOnStopOnly = saveUserCacheOnStopOnly;
	}
	public boolean isBungeecord() {
		return bungeecord;
	}
	public void setBungeecord(boolean bungeecord) {
		this.bungeecord = bungeecord;
	}
	public boolean isRestartOnCrash() {
		return restartOnCrash;
	}
	public void setRestartOnCrash(boolean restartOnCrash) {
		this.restartOnCrash = restartOnCrash;
	}
	public boolean isFilterCreativeItems() {
		return filterCreativeItems;
	}
	public void setFilterCreativeItems(boolean filterCreativeItems) {
		this.filterCreativeItems = filterCreativeItems;
	}
	public int getUserCacheSize() {
		return userCacheSize;
	}
	public void setUserCacheSize(int userCacheSize) {
		this.userCacheSize = userCacheSize;
	}
	public int getSampleCount() {
		return sampleCount;
	}
	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}
	public int getPlayerShuffle() {
		return playerShuffle;
	}
	public void setPlayerShuffle(int playerShuffle) {
		this.playerShuffle = playerShuffle;
	}
	public int getIntCacheLimit() {
		return intCacheLimit;
	}
	public void setIntCacheLimit(int intCacheLimit) {
		this.intCacheLimit = intCacheLimit;
	}
	public int getItemDirtyTicks() {
		return itemDirtyTicks;
	}
	public void setItemDirtyTicks(int itemDirtyTicks) {
		this.itemDirtyTicks = itemDirtyTicks;
	}
	public int getTimeoutTime() {
		return timeoutTime;
	}
	public void setTimeoutTime(int timeoutTime) {
		this.timeoutTime = timeoutTime;
	}
	public int getNettyThreads() {
		return nettyThreads;
	}
	public void setNettyThreads(int nettyThreads) {
		this.nettyThreads = nettyThreads;
	}
	public double getMovedWronglyThreshhold() {
		return movedWronglyThreshhold;
	}
	public void setMovedWronglyThreshhold(double movedWronglyThreshhold) {
		this.movedWronglyThreshhold = movedWronglyThreshhold;
	}
	public double getMovedTooQuicklyMultiplier() {
		return movedTooQuicklyMultiplier;
	}
	public void setMovedTooQuicklyMultiplier(double movedTooQuicklyMultiplier) {
		this.movedTooQuicklyMultiplier = movedTooQuicklyMultiplier;
	}
	public SpigotWorldsConfig getWorldSettings() {
		return worldSettings;
	}
	public void setWorldSettings(SpigotWorldsConfig worldSettings) {
		this.worldSettings = worldSettings;
	}
	public AttributeConfig getAttributes() {
		return attributes;
	}
	public void setAttributes(AttributeConfig attributes) {
		this.attributes = attributes;
	}
	public MessagesConfig getMessages() {
		return messages;
	}
	public void setMessages(MessagesConfig messages) {
		this.messages = messages;
	}
	public StatsConfig getStats() {
		return stats;
	}
	public void setStats(StatsConfig stats) {
		this.stats = stats;
	}
	public AdvancementConfig getAdvancement() {
		return advancement;
	}
	public void setAdvancement(AdvancementConfig advancement) {
		this.advancement = advancement;
	}
	public CommandsConfig getCommands() {
		return commands;
	}
	public void setCommands(CommandsConfig commands) {
		this.commands = commands;
	}

}
