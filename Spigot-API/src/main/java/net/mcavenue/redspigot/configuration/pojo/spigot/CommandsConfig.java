package net.mcavenue.redspigot.configuration.pojo.spigot;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author Redmancometh
 *
 */
@Data
public class CommandsConfig {
	private int tabComplete = 0;
	private List<String> replaceCommands, spamExclusions;
	private boolean log = true, silentCommandblockConsole = false;
	public int getTabComplete() {
		return tabComplete;
	}
	public void setTabComplete(int tabComplete) {
		this.tabComplete = tabComplete;
	}
	public List<String> getReplaceCommands() {
		return replaceCommands;
	}
	public void setReplaceCommands(List<String> replaceCommands) {
		this.replaceCommands = replaceCommands;
	}
	public List<String> getSpamExclusions() {
		return spamExclusions;
	}
	public void setSpamExclusions(List<String> spamExclusions) {
		this.spamExclusions = spamExclusions;
	}
	public boolean isLog() {
		return log;
	}
	public void setLog(boolean log) {
		this.log = log;
	}
	public boolean isSilentCommandblockConsole() {
		return silentCommandblockConsole;
	}
	public void setSilentCommandblockConsole(boolean silentCommandblockConsole) {
		this.silentCommandblockConsole = silentCommandblockConsole;
	}

}
